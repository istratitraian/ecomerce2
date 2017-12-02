package guru.springframework.services.security;

import guru.springframework.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import guru.springframework.services.CustomerService;

/**
 * Created by jt on 1/6/16.
 */
@Component
public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent> {

    @Autowired
    private CustomerService userService;

    @Override
    public void onApplicationEvent(LoginFailureEvent event) {

        Authentication authentication = (Authentication) event.getSource();
        System.out.println("LoginEvent Failure for: " + authentication.getPrincipal() + ", " + event.getTimestamp());
        updateUserAccount(authentication);
    }

    public void updateUserAccount(Authentication authentication) {
        Customer user = userService.findByUserName((String) authentication.getPrincipal());

        if (user != null) { //user found
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() > 5) {
                user.setEnabled(false);
            }

            System.out.println("Valid User name, updating failed attempts " + user);
            userService.update(user);
        }
    }
}
