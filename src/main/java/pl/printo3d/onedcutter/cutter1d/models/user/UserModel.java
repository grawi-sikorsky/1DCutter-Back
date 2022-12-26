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

import pl.printo3d.onedcutter.cutter1d.dto.UserRegisterDTO;
import pl.printo3d.onedcutter.cutter1d.models.project.CutUnit;
import pl.printo3d.onedcutter.cutter1d.models.project.CutOptions;
import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.project.StockUnit;

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
    private Integer activeProjectId;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumn(name = "active_project_model", referencedColumnName = "id")
    private ProjectModel activeProjectModel;

    @OneToMany(cascade = { CascadeType.ALL })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private List<ProjectModel> savedProjectModels = new ArrayList<ProjectModel>();

    @PrePersist
    public void prepersist(){
        this.uuid = UUID.randomUUID().toString();

        // 1 domyslne formatki
        ProjectModel ord = new ProjectModel();
        ord.setCutList(new ArrayList<>(Arrays.asList(new CutUnit("220", "5"), new CutUnit("260", "5"))));
        ord.setStockList(new ArrayList<>(Arrays.asList(new StockUnit("0", "1000", "6", "0"), new StockUnit("1", "1000", "5", "0"))));
        ord.setCutOptions(new CutOptions(false, 0d, false, false, 1000));
        ord.setProjectName("Default project");
        ord.setProjectCreated(LocalDateTime.now());
        ord.setProjectModified(LocalDateTime.now());

        this.setactiveProjectModel(ord);
        this.setsavedProjectModels(new ArrayList<>(Arrays.asList(ord)));
        this.setactiveProjectId(0); // default
        this.setNumberOfSavedItems(this.getsavedProjectModels().size());

        this.setRole("VIP"); // role dynamicznie pasuje ustawiac.        
    }

    public UserModel() {
    }

    public UserModel(UserRegisterDTO userRegisterDTO){
        this.username = userRegisterDTO.getUsername();
        this.password = userRegisterDTO.getPassword();
        this.email = userRegisterDTO.getPassword();
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
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role));
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

    public Integer getactiveProjectId() {
        return activeProjectId;
    }

    public void setactiveProjectId(Integer activeProjectId) {
        this.activeProjectId = activeProjectId;
    }

    public ProjectModel getactiveProjectModel() {
        return activeProjectModel;
    }

    public void setactiveProjectModel(ProjectModel activeProjectModel) {
        this.activeProjectModel = activeProjectModel;
    }

    public List<ProjectModel> getsavedProjectModels() {
        return savedProjectModels;
    }

    public void setsavedProjectModels(List<ProjectModel> savedProjectModels) {
        this.savedProjectModels = savedProjectModels;
    }

}
