package guru.springframework.controllers;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductToProductForm;
import guru.springframework.domain.Cart;
import guru.springframework.domain.CartItem;
import guru.springframework.domain.Product;
import guru.springframework.repositories.CartItemRepository;
import guru.springframework.repositories.CustomerRepository;
import guru.springframework.services.ProductService;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author I.T.W764
 */
@Controller
public class ProductController {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductToProductForm productToProductForm;

    @Autowired
    private MessageSource messageSource;
    private Path path;

    private String getProperty(String prop) {
        return messageSource.getMessage(prop, new Object[]{}, LocaleContextHolder.getLocale());
    }

    @GetMapping("/product/list")
    public String listProducts(Model model) {
        model.addAttribute("products", productService.listAll());
        return "/product/list";
    }

    private void addItemToCart(Integer id, Principal principal) {
        Product product = productService.getById(id);
        Cart cart = customerRepository.findByUsername(principal.getName()).getCart();
        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.incrementQuantity();
                cartItemRepository.save(item);
                return;
            }
        }
        cartItemRepository.save(new CartItem(cart, product));
    }

    @GetMapping("/product/addToCart/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addToCart(@PathVariable(name = "id") Integer id, Principal principal) {
        addItemToCart(id, principal);
    }

    @GetMapping("/product/addToCartAndView/{id}")
    public String addToCartAndView(@PathVariable Integer id, Principal principal) {
        addItemToCart(id, principal);
        return "redirect:/cart/";
    }

    @GetMapping("/product/show/{id}")
    public String getProduct(@PathVariable Integer id, Model model) {
        model.addAttribute("product", productService.getById(id));
        return "/product/show";
    }

    @GetMapping("/product/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Product product = productService.getById(id);
        ProductForm productForm = productToProductForm.convert(product);

        model.addAttribute("productForm", productForm);
        return "/product/productform";
    }

    @GetMapping("/product/new")
    public String newProduct(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "/product/productform";
    }

    @PostMapping(value = "/product")//, consumes = "multipart/form-data")
    public String saveOrUpdateProduct(
//            @ModelAttribute("productForm")
            @Validated //            @Valid 
            ProductForm productForm,
            BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            return "/product/productform";
        }
        Product savedProduct = productService.saveOrUpdateProductForm(productForm);

        String rootDir = 
//                "E:\\Spring\\NetBeans\\NetBeansProjects\\musicshop2\\src\\main\\resources\\images\\products";
                getProperty("external.path");
        System.out.println(" *** saveOrUpdateProduct() " + productForm
        +"\n, root:"+rootDir
        );

        Integer savedId = savedProduct.getId();

        path = Paths.get(rootDir + savedId);

        MultipartFile image = productForm.getImage();

        if (image != null && !image.isEmpty()) {
            try {
                image.transferTo(path.toFile());
            } catch (IOException | IllegalStateException e) {
                throw new RuntimeException("Product image saving failed :(" + image.getName());
            }
        }

        return "redirect:/product/show/" + savedProduct.getId();
    }

    @GetMapping("/product/delete/{id}")
    public String delete(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/product/list";
    }
}
