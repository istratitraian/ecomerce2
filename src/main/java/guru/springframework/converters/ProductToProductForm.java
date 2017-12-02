package guru.springframework.converters;

import guru.springframework.commands.ProductForm;
import guru.springframework.domain.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *  * @author I.T.W764
 */
@Component
public class ProductToProductForm implements Converter<Product, ProductForm> {
    @Override
    public ProductForm convert(Product product) {
        ProductForm productForm = new ProductForm();
        productForm.setId(product.getId());
        productForm.setVersion(product.getVersion());
        productForm.setDescription(product.getDescription());
        productForm.setPrice(product.getPrice());
//        productForm.setImage(new CommonsMultipartFile(
//                
//                new DiskFileItemFactory(
//                DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, new File(""))));
//        productForm.setImageUrl(product.getImageUrl());
        return productForm;
    }
}
