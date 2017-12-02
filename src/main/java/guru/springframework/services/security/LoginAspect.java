package guru.springframework.services.security;

import guru.springframework.domain.Customer;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import guru.springframework.services.CustomerService;
import javax.servlet.ServletContext;
import org.aspectj.lang.annotation.After;

/**
 * Created by jt on 1/6/16.
 */
@Aspect
@Component
public class LoginAspect {

    @Autowired
    private LoginFailureEventPublisher publisher;

    @Autowired
    private CustomerService userService;

    @Pointcut("execution(* guru.springframework.WebAppInit.onStartup(..))")
    public void onStartupCustom() {

        System.out.println("LoginAspect.execution(* guru.springframework.WebAppInit.onStartup()");
    }

    @Pointcut("execution(* org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer.onStartup(..))")
    public void onStartupSecurity() {

        System.out.println("LoginAspect.org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer.onStartup()");
    }

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate() {
    }

    @AfterReturning("guru.springframework.services.security.LoginAspect.onStartupCustom() && args(servletContext)")
    public void onStartupCustomBefore(ServletContext servletContext) {

        System.out.println("@Aspect onStartupCustomBefore: " + servletContext);
    }

    @AfterReturning("guru.springframework.services.security.LoginAspect.onStartupSecurity() && args(servletContext)")
    public void onStartupSecurityBefore(ServletContext servletContext) {

        System.out.println("@Aspect onStartupSecurityBefore: " + servletContext);
    }

    @Before("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logBefore(Authentication authentication) {

//        System.out.println("@Aspect This is before the Authenticate Method: authentication: " + authentication.isAuthenticated());
    }

    @AfterReturning(value = "guru.springframework.services.security.LoginAspect.doAuthenticate()",
            returning = "authentication")
    public void logAfterAuthenticate(Authentication authentication) {
//        System.out.println("@Aspect This is after the Authenticate Method authentication: " + authentication.isAuthenticated());

        if (authentication.isAuthenticated()) {

            Customer user = userService.findByUserName(((UserDetailsImpl) authentication.getPrincipal()).getUsername());

            user.setFailedLoginAttempts(0);

            System.out.println("User AUTHENTICATED  " + user);
            userService.update(user);
        }
    }

    @AfterThrowing("guru.springframework.services.security.LoginAspect.doAuthenticate() && args(authentication)")
    public void logAuthenicationException(Authentication authentication) {
//        String userDetails = (String) authentication.getPrincipal();
//        System.out.println("@@@@@@@@Aspect Login failed for user: " + userDetails);

        publisher.publish(new LoginFailureEvent(authentication));
    }
}
