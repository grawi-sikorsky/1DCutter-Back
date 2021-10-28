package pl.printo3d.onedcutter.cutter1d.user.models;

import java.util.ArrayList;
import java.util.List;

import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;

public class UserDTO {

    private String uuid;
    private String username;
    private String role;
    private String email;
    private String phone;
    private String website;
    private Integer numberOfSavedItems;
    private Integer activeOrderId;

    private OrderModel activeOrderModel;
    private List<OrderModel> savedOrderModels = new ArrayList<OrderModel>();

    public UserDTO() {
    }

    public UserDTO(UserModel userModel){
        this.uuid = userModel.getUuid();
        this.username = userModel.getUsername();
        this.role = userModel.getRole();
        this.email = userModel.getEmail();
        this.phone = userModel.getPhone();
        this.website = userModel.getWebsite();
        this.numberOfSavedItems = userModel.getNumberOfSavedItems();
        this.activeOrderId = userModel.getActiveOrderId();
        this.activeOrderModel = userModel.getActiveOrderModel();
        this.savedOrderModels = userModel.getSavedOrderModels();
    }

    public UserModel toEntity(){
        UserModel userModel = new UserModel();

        userModel.setUuid(this.uuid);
        userModel.setUsername(this.username);
        userModel.setRole(this.role);
        userModel.setEmail(this.email);
        userModel.setPhone(this.phone);
        userModel.setWebsite(this.website);
        userModel.setNumberOfSavedItems(this.numberOfSavedItems);
        userModel.setActiveOrderId(this.activeOrderId);
        userModel.setActiveOrderModel(this.activeOrderModel);
        userModel.setSavedOrderModels(this.savedOrderModels);

        return userModel;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
