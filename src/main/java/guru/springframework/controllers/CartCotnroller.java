package guru.springframework.controllers;

import guru.springframework.domain.Cart;
import guru.springframework.domain.CartItem;
import guru.springframework.domain.Customer;
import guru.springframework.repositories.CartItemRepository;
import guru.springframework.repositories.CustomerRepository;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author I.T.W764
 */
@Controller
public class CartCotnroller {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/cart/")
    public String getCart(@AuthenticationPrincipal User user,
            Model model, Principal principal) {

        Customer customer = customerRepository.findByUsername(principal.getName());
        Cart cart = customer.getCart();
        model.addAttribute("cart", cart);
        return "cart/list";
    }

    @GetMapping("/cart/delete/{itemId}")
    public String dleteItem(@PathVariable(value = "itemId") Integer itemId) {
        CartItem item = cartItemRepository.findOne(itemId);
        item.getCart().getItems().clear();
        item.getProduct().getCartItems().clear();
        cartItemRepository.delete(item);
        return "redirect:/cart/";
    }

    @GetMapping("/cart/removeAllCartItems/")
    public String removeAllCartItems(Principal principal) {

        Customer customer = customerRepository.findByUsername(principal.getName());
        Cart cart = customer.getCart();
        cartItemRepository.delete(cart.getItems());
        return "redirect:/cart/";
    }
}
