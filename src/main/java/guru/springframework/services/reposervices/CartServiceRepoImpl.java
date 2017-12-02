package guru.springframework.services.reposervices;

import guru.springframework.domain.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import guru.springframework.repositories.CartRepository;
import guru.springframework.services.CartService;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jt on 12/18/15.
 */
@Transactional
@Service
@Profile({"springdatajpa"})
public class CartServiceRepoImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> listAll() {
        List<Cart> carts = new ArrayList<>();
        cartRepository.findAll().forEach(carts::add); //fun with Java 8
        return carts;
    }

    @Override
    public Cart getById(Integer id) {
        return cartRepository.findOne(id);
    }

    @Override
    public Cart saveOrUpdate(Cart domainObject) {
        return cartRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        cartRepository.delete(id);
    }
}
