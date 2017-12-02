package guru.springframework.services;

import guru.springframework.domain.Customer;

/**
 * Created by jt on 12/14/15.
 */
public interface CustomerService extends CRUDService<Customer> {

    public Customer findByUserName(String username);

    public Customer update(Customer domainObject);

    public Customer findByEmail(String email);

}
