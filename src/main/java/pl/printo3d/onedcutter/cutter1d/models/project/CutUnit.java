package pl.printo3d.onedcutter.cutter1d.models.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc ciecia oraz ilosc takich
 * samych odcinkow
 * <p>
 * {@code String cutLength} - dlugosc odcinka
 * <p>
 * {@code String cutPcs} - ilosc odcinkow danej dlugosci
 * <p>
 * {@code String name} - not yet.. nazwy
 * <p>
 */
@Entity
@Getter
@Setter
public class CutUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cutLength;
    private String cutPcs;
    private String name; // byc moze nazwy kawalkow beda

    public CutUnit() {
    }

    public CutUnit(String cutLength, String cutPcs) {
        this.cutLength = cutLength;
        this.cutPcs = cutPcs;
    }

    public CutUnit(String cutLength, String cutPcs, String name) {
        this.cutLength = cutLength;
        this.cutPcs = cutPcs;
        this.name = name;
    }
}
