package guru.springframework.domain;

import guru.springframework.domain.security.Authority;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 *  * @author I.T.W764
 */
@Entity
public class Customer extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer failedLoginAttempts = 0;

    private String username = "";

    @Transient
    private String password;

    private String encryptedPassword;
    private Boolean enabled = true;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "addressLine1", column = @Column(name = "ADDRESS_LINE_1_BILLING"))
        ,@AttributeOverride(name = "addressLine2", column = @Column(name = "ADDRESS_LINE_2_BILLING"))
        ,@AttributeOverride(name = "city", column = @Column(name = "CITY_BILLING"))
        ,@AttributeOverride(name = "state", column = @Column(name = "STATE_BILLING"))
        ,@AttributeOverride(name = "zipCode", column = @Column(name = "ZIP_CODE_BILLING"))
    })
    //We need to replace names in database, other way column with the SAME NAME will throw error
    private Address billingAddress = new Address();

    @Embedded
    private Address shippingAddress = new Address();

    @JoinTable(name = "CUSTOMER_AUTHORITY",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities = new ArrayList<>();// new HashSet<>();

    public Customer() {
    }

//    @JsonIgnore
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "customer")
    private Cart cart = new Cart(this);

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {

        this.cart = cart;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> auth) {
        this.authorities = auth;
    }

    public void addAuthority(Authority auth) {

        if (!authorities.contains(auth)) {
            this.authorities.add(auth);
        }
        if (!auth.getCustomers().contains(this)) {
            auth.getCustomers().add(this);
        }
    }

    public void removeAuthority(Authority auth) {
        this.authorities.remove(auth);
        auth.getCustomers().remove(this);
    }

    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
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
        final Customer other = (Customer) obj;
        return Objects.equals(hashCode(), other.hashCode());
    }

    @Override
    public String toString() {
        return "Customer{" + getId() + ", failedLoginAttempts=" + failedLoginAttempts + ", username=" + username + ", password=" + password + ", encryptedPassword=" + encryptedPassword + ", enabled=" + enabled + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", billingAddress=" + billingAddress + ", shippingAddress=" + shippingAddress + ", roles=" + authorities + "}";
    }

}
