/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guru.springframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.Transient;

/**
 *
 * @author I.T.W764
 */
@Entity
public class CartItem extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private int quantity = 1;

    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private Cart cart;

    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product product;

    public CartItem() {
    }

    public CartItem(Cart cart, Product product) {
        this(1, cart, product);
    }

    public CartItem(int quantity, Cart cart, Product product) {
        this.quantity = quantity;
        this.cart = cart;
        this.product = product;
    }

    public CartItem(Integer id) {
        super.setId(id);
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

//    @Transient
    public String getTotal() {
        total = String.format("%.2f", (quantity * product.getPrice()));
        return total;
    }

    private String total;

    public void setTotal(String total) {
        this.total = total;
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
        return Objects.equals(this.hashCode(), ((CartItem) obj).hashCode());
    }

    @Transient
    public void incrementQuantity() {
        this.quantity++;
    }

    @Override
    public String toString() {
        return "Item{" + getId() + ", quantity=" + quantity + ", product=" + product.getId() + ", cart= " + cart.getId() + '}' + '\n';
    }

}
