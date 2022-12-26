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

import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
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
    private List<CutUnit> cutList = new ArrayList<CutUnit>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_id", referencedColumnName = "id", insertable = true, updatable = true)
    private List<StockUnit> stockList = new ArrayList<StockUnit>();

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
}
