package guru.springframework.commands.validators;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import guru.springframework.commands.CustomerForm;
import guru.springframework.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import guru.springframework.services.CustomerService;

/**
* @author I.T.W764
 */
@Component
public class CustomerFormValidator implements Validator {

    @Autowired
//    @Resource(name="userServiceRepoImpl")
    private CustomerService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return CustomerForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        CustomerForm customerForm = (CustomerForm) target;

        Customer user = userService.findByUserName(customerForm.getUserName());
        if (user != null) {
            errors.rejectValue("userName", "error.customer.username.taken");
            return;
        }

        if (!customerForm.getPasswordText().equals(customerForm.getPasswordTextConf())) {
            errors.rejectValue("passwordText", "PasswordsDontMatch.customerForm.passwordText", "Passwords Don't Match");
            errors.rejectValue("passwordTextConf", "PasswordsDontMatch.customerForm.passwordTextConf", "Passwords Don't Match");
            return;
        }

        Customer existendCustomer = userService.findByEmail(customerForm.getEmail());
        if (existendCustomer != null) {
            errors.rejectValue("email", "error.customer.email.exists");
        }

    }
}
