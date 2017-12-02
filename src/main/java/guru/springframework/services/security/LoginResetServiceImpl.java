/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework.services.security;

import guru.springframework.domain.Customer;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import guru.springframework.services.CustomerService;

/**
 *
 * @author I.T.W764
 */
@Service
public class LoginResetServiceImpl implements LoginResetService {

    @Autowired
    private CustomerService userService;

    @Scheduled(fixedRate = 60000)
    @Override
    public void resetFailedLogins() {

        System.out.println("LoginResetServiceImpl.resetFailedLogins()");

        List<Customer> users = userService.listAll();

        users.forEach(user -> {
            if (!user.getEnabled() || user.getFailedLoginAttempts() > 0) {
                user.setEnabled(true);
                user.setFailedLoginAttempts(0);
                System.out.println("LoginResetServiceImpl reset : " + user);
                userService.update(user);
            }

        });
    }

}
