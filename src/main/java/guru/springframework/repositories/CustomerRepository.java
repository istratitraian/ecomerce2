package guru.springframework.repositories;

import guru.springframework.domain.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 */
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    public Customer findByUsername(String userName);

    public Customer findByEmail(String email);

}
