package guru.springframework.domain.security;

import guru.springframework.domain.AbstractDomainClass;
import guru.springframework.domain.Customer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

/**
 * @author I.T.W764
 */
@Entity
@Table(name = "Authority")
public class Authority extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 1L;
    private String authority = "";

    @JoinTable(name = "CUSTOMER_AUTHORITY",
            joinColumns = @JoinColumn(name = "authority_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Customer> customers = new ArrayList<>();//new HashSet<>();

    public Authority() {
    }

    public Authority(String role) {
        this.authority = role;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String role) {
        this.authority = role;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> users) {
        this.customers = users;
    }

    public void addCustomer(Customer user) {
        if (!this.customers.contains(user)) {
            this.customers.add(user);
        }
        if (!user.getAuthorities().contains(this)) {
            user.getAuthorities().add(this);
        }
    }

    public void removeCustomer(Customer user) {
        this.customers.remove(user);
        user.getAuthorities().remove(this);
    }

    @Override
    public String toString() {
        return "Authority{" + authority + '}';
    }

}
