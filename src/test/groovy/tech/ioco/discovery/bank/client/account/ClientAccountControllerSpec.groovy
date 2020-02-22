package tech.ioco.discovery.bank.client.account


import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithUserDetails
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Issue
import spock.lang.Specification
import tech.ioco.discovery.bank.Book
import tech.ioco.discovery.bank.SpecFactory
import tech.ioco.discovery.bank.client.Client
import tech.ioco.discovery.bank.client.ClientRepository

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
class ClientAccountControllerSpec extends Specification {
    @Autowired
    private ClientAccountController controller
    @Autowired
    private WebApplicationContext context
    @Autowired
    private ClientRepository clientRepository
    private Client testClient
    private ClientAccountRepository repository = Mock()
    private ClientAccountService service = Mock()
    private MockMvc mockMvc

    def setup() {
        controller.repository = repository
        controller.service = service
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).alwaysDo(print()).build()
        testClient = clientRepository.findByUsername('test').get()
    }

    @Issue('4.2.1. Display transactional accounts with balances')
    @WithUserDetails('test')
    def 'getting transactional client accounts by a logged in user returns their client account'() {
        when: 'getting client account'
        mockMvc.perform(get('/clientAccounts/0/50')).andExpect(status().isOk()).
                andExpect(jsonPath('$.content[0].displayBalance').value(SpecFactory.clientAccount.displayBalance)).
                andExpect(jsonPath('$.content[0].accountType.description').value(SpecFactory.clientAccount.accountType.description)).
                andExpect(jsonPath('$.content[0].clientAccountNumber').value(SpecFactory.clientAccount.clientAccountNumber)).
                andExpect(jsonPath('$.content[0].client.name').value(SpecFactory.clientAccount.client.name)).
                andExpect(jsonPath('$.content[0].client.surname').value(SpecFactory.clientAccount.client.surname)).
                andExpect(jsonPath('$.content[0].client.title').value(SpecFactory.clientAccount.client.title.name())).
                andExpect(jsonPath('$.content[0].client.dateOfBirth').value(SpecFactory.clientAccount.client.dateOfBirth.toString())).
                andExpect(jsonPath('$.content[0].currency.currencyCode').value(SpecFactory.clientAccount.currency.currencyCode)).
                andExpect(jsonPath('$.content[0].currency.decimalPlaces').value(SpecFactory.clientAccount.currency.decimalPlaces)).
                andExpect(jsonPath('$.content[0].currency.description').value(SpecFactory.clientAccount.currency.description))
        then: 'the repository is called to get client accounts ordering them by displayBalance descending'
        1 * repository.findByClient({ Client client -> client.clientId == testClient.clientId }, true,
                { Pageable pageable ->
                    pageable.sort.stream().allMatch(
                            { Sort.Order order -> order.property == 'displayBalance' && order.descending }) &&
                            pageable.pageNumber == 0 && pageable.pageSize == 50
                }) >> new PageImpl([SpecFactory.clientAccount])
    }

    @WithAnonymousUser
    def 'Only an authenticated user has access to the clientsAccounts pages'() {
        when: 'getting client account'
        mockMvc.perform(get('/clientAccounts/0/50')).andExpect(status().is4xxClientError())
        then: 'the repository is skipped totally'
        0 * repository.findByClient(_, _, _)
    }

    @Issue('4.2.2. Display currency accounts with converted Rand values')
    @WithUserDetails('test')
    def 'getting currency account by a logged in user returns the same'() {
        given: 'that a currency account with a currency display balance'
        Gson gson = new Gson()
        ClientAccount clientAccount = gson.fromJson(gson.toJson(SpecFactory.clientAccount), ClientAccount)
        clientAccount.currencyBalance(SpecFactory.currencyDisplayBalance)
        when: 'getting currency accounts'
        mockMvc.perform(get('/currencyClientAccounts/0/67')).andExpect(status().isOk()).
                andExpect(jsonPath('$.content[0].currencyDisplayBalance.rate').value(clientAccount.currencyDisplayBalance.get().rate)).
                andExpect(jsonPath('$.content[0].currencyDisplayBalance.currency.currencyCode').value(clientAccount.currencyDisplayBalance.get().currency.currencyCode)).
                andExpect(jsonPath('$.content[0].currencyDisplayBalance.displayBalance').value(clientAccount.currencyDisplayBalance.get().displayBalance))
        then: 'delegate to the service'
        1 * service.currencyAccounts({ Client client -> client.clientId == testClient.clientId },
                { Book book -> book.itemsPerPage == 67 && book.pageNumber == 0 }) >> new PageImpl<>([clientAccount])
    }

    def 'only a logged in user can access currency accounts'() {
        when: 'getting currency accounts'
        mockMvc.perform(get('/currencyClientAccounts/0/67')).andExpect(status().is4xxClientError())
        then: 'the service calling is skipped'
        0 * service.currencyAccounts(_, _)
    }

}
