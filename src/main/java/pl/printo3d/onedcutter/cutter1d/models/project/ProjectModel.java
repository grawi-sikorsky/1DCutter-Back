package pl.printo3d.onedcutter.cutter1d.models.project;

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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Model zawierajacy caly projekt <p>
 * {@code String projectName} - Nazwa <p>
 * {@code LocalDateTime projectCreated} - Data stworzenia <p>
 * {@code LocalDateTime projectModified} - Data modyfikacji <p>
 * {@code List<CutModel> cutList} - Lista odcinkow <p>
 * {@code List<StockModel> stockList} - Lista surowcow <p>
 * {@code CutOptions cutOptions} - Opcje projektu <p>
 */
@Entity
public class ProjectModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String projectName;
    private LocalDateTime projectCreated;
    private LocalDateTime projectModified;

    @Column(columnDefinition = "TEXT")
    private String projectResults;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = true, updatable = true)
    private List<CutModel> cutList = new ArrayList<CutModel>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = true, updatable = true)
    private List<StockModel> stockList = new ArrayList<StockModel>();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cut_options_id", referencedColumnName = "id", unique = true, insertable = true, updatable = true)
    private CutOptions cutOptions;

    @Column(name = "user_id")
    private Long userId;

    @PrePersist
    public void prepersist(){
        this.projectCreated = LocalDateTime.now();
    }

    public ProjectModel() {
    }

    public List<CutModel> getCutList() {
        return cutList;
    }

    public void setCutList(List<CutModel> cutList) {
        this.cutList = cutList;
    }

    public List<StockModel> getStockList() {
        return stockList;
    }

    public void setStockList(List<StockModel> stockList) {
        this.stockList = stockList;
    }

    public CutOptions getCutOptions() {
        return cutOptions;
    }

    public void setCutOptions(CutOptions cutOptions) {
        this.cutOptions = cutOptions;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDateTime getProjectCreated() {
        return projectCreated;
    }

    public void setProjectCreated(LocalDateTime projectCreated) {
        this.projectCreated = projectCreated;
    }

    public LocalDateTime getProjectModified() {
        return projectModified;
    }

    public void setProjectModified(LocalDateTime projectModified) {
        this.projectModified = projectModified;
    }

    public String getProjectResults() {
        return projectResults;
    }

    public void setProjectResults(String projectResults) {
        this.projectResults = projectResults;
    }

    
}
