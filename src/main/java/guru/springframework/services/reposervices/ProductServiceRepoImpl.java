package guru.springframework.services.reposervices;

import guru.springframework.commands.ProductForm;
import guru.springframework.converters.ProductFormToProduct;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import guru.springframework.repositories.ProductRepository;
import guru.springframework.services.SendMessageService;

/**
 * Created by jt on 12/18/15.
 */
@Service
@Profile({"springdatajpa"})
public class ProductServiceRepoImpl implements ProductService {

    @Autowired
    private SendMessageService sendTextMessageService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductFormToProduct productFormToProduct;

    @Override
    public List<Product> listAll() {
        
        try {
            sendTextMessageService.sendMessage("sendTextMessageService Listing Products");
            sendTextMessageService.sendTextMessage("sendTextMessageService Listing Products");
        } catch (Exception e) {
            System.out.println("  ERROR JMS sendMessages : "+e);
        }
        
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add); //fun with Java 8
        return products;
    }

    @Override
    public Product getById(Integer id) {
        return productRepository.findOne(id);
    }

    @Override
    public Product saveOrUpdateProductForm(ProductForm productForm) {
        return saveOrUpdate(productFormToProduct.convert(productForm));
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        return productRepository.save(domainObject);
    }

    @Override
    public void delete(Integer id) {
        productRepository.delete(id);
    }
}
