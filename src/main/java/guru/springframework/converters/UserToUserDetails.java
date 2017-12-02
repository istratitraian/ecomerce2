package guru.springframework.converters;

import guru.springframework.domain.Customer;
import guru.springframework.services.security.UserDetailsImpl;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author I.T.W764
 */
@Component
public class UserToUserDetails implements Converter<Customer, UserDetails> {

    @Override
    public UserDetails convert(Customer user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        if (user != null) {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getEncryptedPassword());
            userDetails.setEnabled(user.getEnabled());

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            user.getAuthorities().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            });

            userDetails.setAuthorities(authorities);
        }

        return userDetails;
    }
}
