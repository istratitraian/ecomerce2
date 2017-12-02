package guru.springframework.services.security;

import guru.springframework.domain.Customer;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import guru.springframework.services.CustomerService;

/**
 * Created by jt on 12/28/15.
 */
@Service("userDetailsService")
public class SpringSecUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private CustomerService userService;

    @Resource(name = "userToUserDetails")
    private Converter<Customer, UserDetails> userUserDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer user = userService.findByUserName(username);
        return userUserDetailsConverter.convert(user);
    }
}
