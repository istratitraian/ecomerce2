package guru.springframework;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
/**
 * <h1 style="color:red;"> This class is mandatory to initialize :</h1>
 * <h2>Spring Security and Thymeleaf Security!</h2>
 *
 * @author Istrati Traian
 */
public class SpringSecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SpringSecurityInitializer() {
        
        super(MvcConfig.class);
        
        System.out.println(" --- SecurityWebApplicationInitializer()");
//        onStartup(servletContext);
    }
    
    
 
    

//    @Override
//    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
//        System.out.println("SecurityWebApplicationInitializer.beforeSpringSecurityFilterChain()");
//        insertFilters(servletContext, new MultipartFilter());
//    }
}
