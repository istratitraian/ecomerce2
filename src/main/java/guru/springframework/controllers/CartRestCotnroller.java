package guru.springframework.controllers;

import guru.springframework.domain.Cart;
import guru.springframework.domain.CartItem;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CartItemRepository;
import guru.springframework.repositories.CartRepository;
import guru.springframework.repositories.CustomerRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author I.T.W764
 */
@Controller
//@RequestMapping("/rest")
public class CartRestCotnroller {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/rest/cart/")
    public String getCart(Model model, Principal principal) {

        Customer customer = customerRepository.findByUsername(principal.getName());
        Cart cart = customer.getCart();
        model.addAttribute("cart", cart);
        return "cart/list_angular";
    }

    @GetMapping("/rest/cart/{cartId}")
    @ResponseBody
    public Cart getCartById(@PathVariable(value = "cartId") Integer cartId) {
        Cart cart = cartRepository.findOne(cartId);
        return cart;
    }

    @DeleteMapping("/rest/cart/delete/{itemId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void dleteItem(@PathVariable(value = "itemId") Integer itemId) {
        CartItem item = cartItemRepository.findOne(itemId);
        item.getCart().getItems().clear();
        item.getProduct().getCartItems().clear();
        cartItemRepository.delete(item);
    }

    @DeleteMapping("/rest/cart/{cartId}/clear")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeAllCartItems(@PathVariable(value = "cartId") Integer cartId) {
        Cart cart = cartRepository.findOne(cartId);
        cartItemRepository.delete(cart.getItems());
    }
}
