package guru.springframework;

import guru.springframework.domain.security.Authority;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/**
 * @author I.T.W764
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    public static final Authority AUTHORITY_CUSTOMER = new Authority(passwordEncoder().encode("CUSTOMER"));
//    public static final Authority AUTHORITY_ADMIN = new Authority(passwordEncoder().encode("ADMIN"));
//    public static final Authority AUTHORITY_GUEST = new Authority(passwordEncoder().encode("GUEST"));
    public static final Authority AUTHORITY_CUSTOMER = new Authority("CUSTOMER");
    public static final Authority AUTHORITY_ADMIN = new Authority("ADMIN");
    public static final Authority AUTHORITY_GUEST = new Authority("GUEST");
    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Bean(name = "passwordEncoder")
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(userDetailsService);
        daoAuth.setPasswordEncoder(passwordEncoder());
        return daoAuth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/h2-console/**").disable().headers().frameOptions().disable()
                .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().authorizeRequests().antMatchers("/**/favicon.ico").permitAll()
                .and().authorizeRequests().antMatchers("/product/**").permitAll()
                .and().authorizeRequests().antMatchers("/cart/**").permitAll()
                .and().authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/**").permitAll()
                .and().authorizeRequests().antMatchers("/images/**").permitAll()
                .and().authorizeRequests().antMatchers("/js").permitAll()
                .and().formLogin().loginPage("/login").permitAll()
                .and().authorizeRequests().antMatchers("/customer/**").authenticated()
                .and().authorizeRequests().antMatchers("/user/**").hasAuthority(AUTHORITY_ADMIN.getAuthority())//hasRole(ROLE_ADMIN.getRole()).anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/access_denied");

//        http.addFilterAfter(new CsrfAttributeToCookieFilter(), CsrfFilter.class)
//                .csrf().csrfTokenRepository(csrfTokenRepository());

//        http.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");//XSRF-TOKEN
        return repository;
    }

//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//    }
}
