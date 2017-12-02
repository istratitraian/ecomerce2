package guru.springframework.repositories;

import guru.springframework.domain.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {
}
