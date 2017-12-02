package guru.springframework;

import guru.springframework.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import guru.springframework.repositories.RoleRepository;
import guru.springframework.repositories.ProductRepository;
import guru.springframework.repositories.CustomerRepository;
import javax.annotation.PostConstruct;

/**
 * @author I.T.W764
 */
@Component
public class PopulateTablesJPA {//implements ApplicationListener<ContextRefreshedEvent> {

    @Resource(name = "passwordEncoder")
    PasswordEncoder passwordEncoder;

    @Autowired
    private ProductRepository productService;
    @Autowired
    private CustomerRepository userService;
    @Autowired
    private RoleRepository roleService;
//    @Autowired
//    private CartRepository cartService;

//    @Override
    @PostConstruct
    public void onApplicationEvent() {//(ContextRefreshedEvent contextRefreshedEvent) {
        loadProducts();
        loadRoles();
        loadUsersAndCustomers();

        System.out.println(" *** Authorities " + roleService.findAll());
    }

    private void loadRoles() {
        roleService.save(SecurityConfig.AUTHORITY_ADMIN);
        roleService.save(SecurityConfig.AUTHORITY_CUSTOMER);
        roleService.save(SecurityConfig.AUTHORITY_GUEST);
    }

    public void loadUsersAndCustomers() {

        Customer user0 = new Customer();
        user0.addAuthority(SecurityConfig.AUTHORITY_ADMIN);
        user0.addAuthority(SecurityConfig.AUTHORITY_CUSTOMER);
        user0.setUsername("admin");
        user0.setEncryptedPassword(passwordEncoder.encode("admin"));
        user0.setFirstName("Istrati");
        user0.setLastName("Taian");
        user0.getBillingAddress().setAddressLine1("Aleea Caminului Nr. 1");
        user0.getBillingAddress().setCity("Focsani");
        user0.getBillingAddress().setState("Vrancea");
        user0.getBillingAddress().setZipCode("620150");
        user0.setEmail("istrati.traian@yahoo.com");
        user0.setPhoneNumber("+40748661049");
        
        
        userService.save(user0);
        
//        cartService.save(userService.findOne(user0.getId()).getCart());
        
        

        Customer user1 = new Customer();
        user1.addAuthority(SecurityConfig.AUTHORITY_CUSTOMER);
        user1.setUsername("mweston");
        user1.setEncryptedPassword(passwordEncoder.encode("password"));
        user1.setFirstName("Micheal");
        user1.setLastName("Weston");
        user1.getBillingAddress().setAddressLine1("1 Main St");
        user1.getBillingAddress().setCity("Miami");
        user1.getBillingAddress().setState("Florida");
        user1.getBillingAddress().setZipCode("33101");
        user1.setEmail("micheal@burnnotice.com");
        user1.setPhoneNumber("305.333.0101");
        userService.save(user1);

        Customer user2 = new Customer();
        user2.addAuthority(SecurityConfig.AUTHORITY_CUSTOMER);
        user2.setUsername("fglenanne");
        user2.setEncryptedPassword(passwordEncoder.encode("password"));
        user2.setFirstName("Fiona");
        user2.setLastName("Glenanne");
        user2.getBillingAddress().setAddressLine1("1 Key Biscane Ave");
        user2.getBillingAddress().setCity("Miami");
        user2.getBillingAddress().setState("Florida");
        user2.getBillingAddress().setZipCode("33101");
        user2.setEmail("fiona@burnnotice.com");
        user2.setPhoneNumber("305.323.0233");
        userService.save(user2);

        Customer user3 = new Customer();
        user3.addAuthority(SecurityConfig.AUTHORITY_CUSTOMER);
        user3.setUsername("saxe");
        user3.setEncryptedPassword(passwordEncoder.encode("password"));
        user3.setFirstName("Sam");
        user3.setLastName("Axe");
        user3.getBillingAddress().setAddressLine1("1 Little Cuba Road");
        user3.getBillingAddress().setCity("Miami");
        user3.getBillingAddress().setState("Florida");
        user3.getBillingAddress().setZipCode("33101");
        user3.setEmail("sam@burnnotice.com");
        user3.setPhoneNumber("305.426.9832");
        userService.save(user3);

    }

    public void loadProducts() {

        for (int i = 1; i < 20; i++) {
            Product product1 = new Product();
            product1.setDescription("Product " + i);
            product1.setPrice(12.99 * i);
//            product1.setImageUrl("http://example.com/product" + i);
            productService.save(product1);
        }

    }
}
