package guru.springframework;

import javax.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import org.h2.server.web.WebServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author I.T.W764
 */
public class WebAppInit implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        System.out.println("---- WebAppInit()");
        //set active profiles located in guru.springframework.services.reposervices
        servletContext.setInitParameter("spring.profiles.active", "springdatajpa");

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.register(MvcConfig.class);

//        servletContext.addListener(new ContextLoaderListener(context));

        //Spring Dispatcher Servlet
       
        
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("guruAppServlet", new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

//         System.out.println("---- WebAppInit() servletRegistrationsName() = "+ servletContext.getAttributeNames());
        //H2 console
        String path = "/h2-console";
        String urlMapping = (path.endsWith("/") ? path + "*" : path + "/*");
        ServletRegistration.Dynamic h2Console = servletContext.addServlet("h2", new WebServlet());
        h2Console.setLoadOnStartup(1);
        h2Console.addMapping(urlMapping);

        System.out.println("---- WebAppInit() H2");

    }
}
