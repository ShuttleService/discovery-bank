package tech.ioco.discovery.bank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.ioco.discovery.bank.atm.AtmService;
import tech.ioco.discovery.bank.atm.DenominationService;
import tech.ioco.discovery.bank.atm.DenominationTypeService;
import tech.ioco.discovery.bank.client.ClientService;
import tech.ioco.discovery.bank.client.ClientSubTypeService;
import tech.ioco.discovery.bank.client.ClientTypeService;
import tech.ioco.discovery.bank.client.ClientUserService;
import tech.ioco.discovery.bank.client.account.AccountTypeService;
import tech.ioco.discovery.bank.client.account.ClientAccountService;
import tech.ioco.discovery.bank.currency.CurrencyService;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableWebSecurity
public class App extends SpringBootServletInitializer {
    private Logger logger = LoggerFactory.getLogger(App.class);
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientUserService clientUserService;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ClientTypeService clientTypeService;
    @Autowired
    private ClientSubTypeService clientSubTypeService;
    @Autowired
    private AccountTypeService accountTypeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientAccountService clientAccountService;
    @Autowired
    private DenominationTypeService denominationTypeService;
    @Autowired
    private AtmService atmService;
    @Autowired
    private DenominationService denominationService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void init() {
        logger.info("Adding defaults details. Please Wait...");
        currencyService.addDefaults();
        accountTypeService.addDefaults();
        clientTypeService.addDefaults();
        clientSubTypeService.addDefaults();
        clientService.addDefaults();
        clientAccountService.addDefaults();
        denominationTypeService.addDefaults();
        atmService.addDefaults();
        denominationService.addDefaults();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(App.class);
    }

    @Bean
    public WebSecurityConfigurerAdapter webSecurityConfigurerAdapter() {
        return new WebSecurityConfigurerAdapter() {
            @Override
            protected void configure(HttpSecurity http) throws Exception {
                http.authorizeRequests().antMatchers("/login/**").permitAll().
                        antMatchers("/clientAccounts/{pageNumber}/{elementPerPage}").authenticated().
                        antMatchers("/currencyClientAccounts/{pageNumber}/{elementPerPage}").authenticated().
                        antMatchers("/withdraw").authenticated().
                        and().csrf().disable();
            }

            @Override
            protected void configure(AuthenticationManagerBuilder builder) throws Exception {
                builder.userDetailsService(clientUserService).
                        passwordEncoder(passwordEncoder);
            }

        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").
                        allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE").
                        exposedHeaders("x-auth-token", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Access-Control-Allow-Methods");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(clientUserService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
