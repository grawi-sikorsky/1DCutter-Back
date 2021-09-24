package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


/**
 * NOT USET ... <p>
 */
@Entity
public class UserSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer numberOfSavedItems;
    Integer activeOrderId;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "userSlot", referencedColumnName = "id" )
    private List<OrderModel> orderModel1 = new ArrayList<OrderModel>();

    public UserSlots() {
    }

    public UserSlots(Integer numberOfSavedItems, Integer activeOrderId, List<OrderModel> orderModel1) {
        this.numberOfSavedItems = numberOfSavedItems;
        this.activeOrderId = activeOrderId;
        this.orderModel1 = orderModel1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderModel> getOrderModel() {
        return orderModel1;
    }

    public void setOrderModel(List<OrderModel> orderModel1) {
        this.orderModel1 = orderModel1;
    }


    
}
