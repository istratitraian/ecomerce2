package guru.springframework.commands;

import java.io.Serializable;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * * @author I.T.W764
 */
public class ProductForm implements Serializable {

    private static final long serialVersionUID = -5302262861085254667L;

    private Integer id;
    private Integer version;

    @NotEmpty
    @Size(min = 5, max = 200)
    private String description;

    @NotNull
    @Min(0)
    @Max(5000)
    private Double price;

    public ProductForm() {
        
//        this.image = new CommonsMultipartFile(fileItem);
    }
//    @NotEmpty
//    @URL
//    private String imageUrl;
    private MultipartFile image;

    public MultipartFile getImage() {
        System.out.println("ProductForm.getImage() "+image);
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
    @Override
    public String toString() {
        return "ProductForm{" + "id=" + id + ", version=" + version + ", description=" + description + ", price=" + price + ", image=" + image + '}';
    }

}
