package tech.ioco.discovery.bank.client;

import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AuthenticatedClient implements UserDetails {
    @NotNull
    @Valid
    private final Client client;
    //Pleasing User Details Gods
    public AuthenticatedClient() {
        client = null;
    }

    public AuthenticatedClient(Client user) {
        this.client = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        final Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet();
        grantedAuthorities.add(new SimpleGrantedAuthority("client"));


        return Collections.unmodifiableSet(grantedAuthorities);
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return client.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return client.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Client getClient() {
        return client;
    }

    @Override
    public String toString() {
        return String.format("%s:%s,%s,%s", getUsername(), getPassword(), getAuthorities(), client);
    }
}
