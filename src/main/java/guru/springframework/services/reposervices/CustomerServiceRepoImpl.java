package guru.springframework.services.reposervices;

import guru.springframework.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import guru.springframework.services.CustomerService;
import guru.springframework.repositories.CustomerRepository;

/**
 * Created by jt on 12/21/15.
 */
@Service
@Profile("springdatajpa")
@Transactional
public class CustomerServiceRepoImpl implements CustomerService {

    @Autowired
    private CustomerRepository userRepository;

    @Resource(name = "passwordEncoder")
    PasswordEncoder encryptionService;

    @Override
    public List<Customer> listAll() {
        List<Customer> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add); //fun with Java 8
        return users;
    }

    @Override
    public Customer getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public Customer saveOrUpdate(Customer domainObject) {

        if (domainObject.getPassword() != null) {
            domainObject.setEncryptedPassword(encryptionService.encode(domainObject.getPassword()));
        }
        return userRepository.save(domainObject);
    }

    @Transactional
    @Override
    public Customer update(Customer domainObject) {
        return userRepository.save(domainObject);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Customer user = userRepository.findOne(id);
//        customerRepository.delete(user.getCustomer());
        userRepository.delete(user);
    }

    @Override
    public Customer findByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Customer findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
