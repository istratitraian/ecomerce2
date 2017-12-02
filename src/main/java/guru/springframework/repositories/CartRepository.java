package guru.springframework.repositories;

import guru.springframework.domain.Cart;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author I.T.W764
 */
public interface CartRepository extends PagingAndSortingRepository<Cart, Integer> {
}
