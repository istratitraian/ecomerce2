package guru.springframework.controllers;

import guru.springframework.SecurityConfig;
import guru.springframework.domain.Customer;
import guru.springframework.domain.security.Authority;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.services.security.UserDetailsImpl;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author I.T.W764
 */
@Controller
public class IndexController {

    public static final String INDEX_PAGE = "index";

    @Autowired
    private CustomerRepository customerRepository;

//    @Autowired
//    PasswordEncoder passwordEncoder;
//    @Autowired
//    @Qualifier("sessionRegistry")
//    private SessionRegistry sessionRegistry;

    private boolean isGuest = false;

    @RequestMapping({"/", ""})
    public String index(HttpServletResponse response,
            HttpServletRequest request,
            @CookieValue(value = "cookie_eMagGUEST_Key", defaultValue = "eMagGUEST_0") String cookieVal,//, defaultValue = "eMagGUEST_0"
            @AuthenticationPrincipal User user,
            Principal principal) {

//        List<Object> principals = sessionRegistry.getAllPrincipals();
//        System.out.println(" +++ " + principals);

        Customer oldCustomer = null;

        if (user != null) {
            oldCustomer = customerRepository.findByUsername(user.getUsername());
        } else {

            if (cookieVal != null && !isGuest) {
                oldCustomer = customerRepository.findByUsername(cookieVal);
            }

            if (oldCustomer == null) {
                if (!isGuest) {
                    Customer guestCustomer = new Customer();
                    guestCustomer.addAuthority(SecurityConfig.AUTHORITY_GUEST);
                    customerRepository.save(guestCustomer);
                    guestCustomer.setUsername("eMagGUEST_" + guestCustomer.getId());
//                guestCustomer.setPassword(passwordEncoder.encode(guestCustomer.getUsername()));
                    customerRepository.save(guestCustomer);
                    oldCustomer = guestCustomer;
                    System.out.println(" +++ IndexController.index() saved GUEST = " + guestCustomer);

                    Cookie cookie1 = new Cookie("cookie_eMagGUEST_Key", guestCustomer.getUsername());
                    cookie1.setMaxAge(10000);
                    response.addCookie(cookie1);
                    isGuest = true;
                }
            }
        }

        if (oldCustomer != null) {

            List<SimpleGrantedAuthority> auths = new ArrayList<>();
            String[] authss = new String[oldCustomer.getAuthorities().size()];
            int i = 0;
            for (Authority authority : oldCustomer.getAuthorities()) {
                auths.add(new SimpleGrantedAuthority(authority.getAuthority()));
                authss[i++] = authority.getAuthority();
            }
//            User newUser = new User(oldCustomer.getUsername(), "", auths);

            UserDetailsImpl newUser = new UserDetailsImpl(oldCustomer.getUsername(), auths);

            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, "",
                    AuthorityUtils.createAuthorityList(authss));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        System.out.println(" +++ IndexController.index()  cookie = " + cookieVal);
        System.out.println(" +++ IndexController.index()  user = " + user);
        System.out.println(" +++ IndexController.index()  principal = " + principal);
        System.out.println(" +++ IndexController.index() oldCustomer = " + oldCustomer);

        return INDEX_PAGE;
    }

}
