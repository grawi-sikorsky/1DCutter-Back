package pl.printo3d.onedcutter.cutter1d.cutter.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class UserSlots {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Integer numberOfSavedItems;
    Integer activeOrderId;

    @ElementCollection
    List<Integer> savedOrdersIds;

    public UserSlots() {
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

    public List<Integer> getSavedItemsIds() {
        return savedOrdersIds;
    }

    public void setSavedItemsIds(List<Integer> savedOrdersIds) {
        this.savedOrdersIds = savedOrdersIds;
    }

    
}
