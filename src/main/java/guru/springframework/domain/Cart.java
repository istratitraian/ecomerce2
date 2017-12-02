package guru.springframework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *  * @author I.T.W764
 */
@Entity
public class Cart extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 3940548625296145582L;
    private String totalCost;

    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    @OneToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER, orphanRemoval = true)//, cascade = CascadeType.REMOVE whill delete Cart AND Customer also !!!!
    private Set<CartItem> items = new HashSet<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Cart() {

    }

    public Cart(Customer customer) {
        this.customer = customer;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public void removeItemById(Integer id) {
        CartItem item = new CartItem(id);
        this.items.remove(item);
    }

    @Override
    public int hashCode() {
        return this.getId();
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
        return Objects.equals(this.getId(), ((Cart) obj).getId());
    }

    public String getTotalCost() {
        double total = 0;
        total = items.stream().map((item) -> item.getProduct().getPrice() * item.getQuantity()).reduce(total, (accumulator, _item) -> accumulator + _item);
        totalCost = String.format("%.2f", total);
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "Cart{" + getId() + ", items=" + items + ", grandTotal=" + getTotalCost() + '}';
    }

}
