package guru.springframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 *
 * @author I.T.W764
 */
@Entity
public class Product extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = -3532377236419382983L;

    private String description;
    private Double price;
//    private String imageUrl;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, orphanRemoval = true)//cascade = CascadeType.REMOVE whill delete Product also !!!
    @JsonIgnore
    private Set<CartItem> cartItems = new HashSet<>();

    public Product() {
    }

//    public Product(String description, Double price, String imageUrl) {
//        this.description = description;
//        this.price = price;
//        this.imageUrl = imageUrl;
//    }
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
    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> items) {
        this.cartItems = items;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return Objects.equals(this.getId(), other.getId());
    }

    @Override
    public String toString() {
        return "Product{" + "description=" + description + ", price=" + price + ", cartItems=" + cartItems + '}';
    }

}
