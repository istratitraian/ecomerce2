package guru.springframework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
* @author I.T.W764
 */
@Controller
public class LoginController {

    @RequestMapping("/login")
    public String showLoginForm() {
        System.out.println("LoginController.showLoginForm()");
        return "login";
    }

    @RequestMapping("secured")
    public String secured() {
        return "secured";
    }

    @RequestMapping("/access_denied")
    public String notAuth() {
        return "secured";
//        return "access_denied";
    }

    @RequestMapping("logout-success")
    public String yourLoggedOut() {

        return "logout-success";
    }

}
