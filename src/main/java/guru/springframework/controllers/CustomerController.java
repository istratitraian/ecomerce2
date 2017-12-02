package guru.springframework.controllers;

import guru.springframework.SecurityConfig;
import guru.springframework.domain.Customer;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import guru.springframework.services.CustomerService;

/**
* @author I.T.W764
 */
@Controller
@RequestMapping("/user")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping({"/list", "/"})
    public String listUsers(Model model) {
//        System.out.println("UserConroller.index() userService = " + userService.listAll());

        model.addAttribute("users", customerService.listAll());
        return "user/list";
    }

    @RequestMapping("/show/{id}")
    public String getUser(@PathVariable Integer id, Model model) {
        model.addAttribute("user", customerService.getById(id));
        return "user/show";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("user", customerService.getById(id));
        return "user/userform";
    }

    @RequestMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new Customer());
        return "user/userform";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid Customer user, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("saveOrUpdate(/) hasErrors()" + bindingResult.hasErrors());
            return "user/userform";
        }
        user.addAuthority(SecurityConfig.AUTHORITY_CUSTOMER);
        
        Customer savedUser = customerService.saveOrUpdate(user);
        return "redirect:/user/show/" + savedUser.getId();
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        
        
        
        customerService.delete(id);
        return "redirect:/user/list";
    }
}
