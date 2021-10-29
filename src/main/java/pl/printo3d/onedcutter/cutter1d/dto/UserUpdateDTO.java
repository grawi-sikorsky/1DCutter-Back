package pl.printo3d.onedcutter.cutter1d.dto;

import pl.printo3d.onedcutter.cutter1d.models.user.UserModel;

public class UserUpdateDTO {

    private String phone;
    private String website;
    private Integer activeOrderId;

    public UserUpdateDTO() {}
    public UserUpdateDTO(UserModel userModel) {
        this.phone = userModel.getPhone();
        this.website = userModel.getWebsite();
        this.activeOrderId = userModel.getActiveOrderId();
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
    public Integer getActiveOrderId() {
        return activeOrderId;
    }
    public void setActiveOrderId(Integer activeOrderId) {
        this.activeOrderId = activeOrderId;
    }

    
}
