package guru.springframework.repositories;

import guru.springframework.domain.security.Authority;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 12/21/15.
 */
public interface RoleRepository extends CrudRepository<Authority, Integer> {
}
