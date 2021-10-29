package pl.printo3d.onedcutter.cutter1d.models.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pl.printo3d.onedcutter.cutter1d.models.project.CutModel;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.OrderModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockModel;

/**
 * Model USERA implementacja UserDetails <p>
 */
@Entity
public class UserModel implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String uuid;
    private String username;
    private String password;
    private String role;
    private String email;
    private String phone;
    private String website;
    private Integer numberOfSavedItems;
    private Integer activeOrderId;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "activeOrderModel", referencedColumnName = "id")
    private OrderModel activeOrderModel;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<OrderModel> savedOrderModels = new ArrayList<OrderModel>();

    @PrePersist
    public void prepersist(){
        this.uuid = UUID.randomUUID().toString();

        // 1 domyslne formatki
        OrderModel ord = new OrderModel();
        ord.setCutList(Arrays.asList(new CutModel("220", "5"), new CutModel("260", "5")));
        ord.setStockList(Arrays.asList(new StockModel("0", "1000", "6", "0"), new StockModel("1", "1000", "5", "0")));
        ord.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
        ord.setProjectName("Default project");
        ord.setProjectCreated(LocalDateTime.now());
        ord.setProjectModified(LocalDateTime.now());

        this.setActiveOrderModel(ord);
        this.setSavedOrderModels(Arrays.asList(ord));
        this.setActiveOrderId(0); // default
        this.setNumberOfSavedItems(this.getSavedOrderModels().size());

        this.setRole("VIP"); // role dynamicznie pasuje ustawiac.        
    }

    public UserModel() {
    }

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(String username, String password, String email, String website) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.website = website;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return Collections.singleton(new SimpleGrantedAuthority(role));
        // return null;
    }
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return password;
    }
    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Integer getNumberOfSavedItems() {
        return numberOfSavedItems;
    }

    public void setNumberOfSavedItems(Integer numberOfSavedItems) {
        this.numberOfSavedItems = numberOfSavedItems;
    }

    public Integer getActiveOrderId() {
        return activeOrderId;
    }

    public void setActiveOrderId(Integer activeOrderId) {
        this.activeOrderId = activeOrderId;
    }

    public OrderModel getActiveOrderModel() {
        return activeOrderModel;
    }

    public void setActiveOrderModel(OrderModel activeOrderModel) {
        this.activeOrderModel = activeOrderModel;
    }

    public List<OrderModel> getSavedOrderModels() {
        return savedOrderModels;
    }

    public void setSavedOrderModels(List<OrderModel> savedOrderModels) {
        this.savedOrderModels = savedOrderModels;
    }

}
