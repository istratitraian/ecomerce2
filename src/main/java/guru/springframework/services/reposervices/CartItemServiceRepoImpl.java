/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework.services.reposervices;

import guru.springframework.domain.CartItem;
import guru.springframework.repositories.CartItemRepository;
import guru.springframework.services.CartItemService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author I.T.W764
 */
@Transactional
@Service
@Profile({"springdatajpa"})
public class CartItemServiceRepoImpl implements CartItemService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public List<CartItem> listAll() {
        List<CartItem> list = new ArrayList<>();
        cartItemRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public CartItem getById(Integer id) {
        return cartItemRepository.findOne(id);
    }

    @Override
    public CartItem saveOrUpdate(CartItem domainObject) {
        return cartItemRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        cartItemRepository.delete(id);
    }

}
