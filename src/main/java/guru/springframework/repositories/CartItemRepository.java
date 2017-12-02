package guru.springframework.repositories;

import guru.springframework.domain.CartItem;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 12/18/15.
 */
public interface CartItemRepository extends CrudRepository<CartItem, Integer> {
}
