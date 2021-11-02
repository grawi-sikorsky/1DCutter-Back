package pl.printo3d.onedcutter.cutter1d.dto;

import java.util.ArrayList;
import java.util.List;

import pl.printo3d.onedcutter.cutter1d.models.project.ProjectModel;
import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;

public class UserDTO {

    private String uuid;
    private String username;
    private String role;
    private String email;
    private String phone;
    private String website;
    private Integer numberOfSavedItems;
    private Integer activeProjectId;

    private ProjectModel activeProjectModel;
    private List<ProjectModel> savedProjectModels = new ArrayList<ProjectModel>();

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
        this.activeProjectId = userModel.getactiveProjectId();
        this.activeProjectModel = userModel.getactiveProjectModel();
        this.savedProjectModels = userModel.getsavedProjectModels(); // todo: out!
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
        userModel.setactiveProjectId(this.activeProjectId);
        userModel.setactiveProjectModel(this.activeProjectModel);
        userModel.setsavedProjectModels(this.savedProjectModels);

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
