package pl.printo3d.onedcutter.cutter1d.models.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc surowca oraz ilosc takich
 * samych odcinkow
 * <p>
 * {@code String idFront} - ID na froncie
 * <p>
 * {@code String stockLength} - Dlugosc surowca
 * <p>
 * {@code String stockPcs} - Ilosc surowca danej dlugosci
 * <p>
 * {@code String stockPrice} - Cena surowca
 * <p>
 * {@code String name} - NOT YET....
 * <p>
 */

@Entity
@Getter
@Setter
public class StockUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idFront;
    private String stockLength;
    private String stockPcs;
    private String stockPrice;
    private String name;

    public StockUnit() {
    }

    public StockUnit(String idFront, String stockLength, String stockPcs, String stockPrice) {
        this.idFront = idFront;
        this.stockLength = stockLength;
        this.stockPcs = stockPcs;
        this.stockPrice = stockPrice;
    }

    public StockUnit(String idFront, String stockLength, String stockPcs, String stockPrice, String name) {
        this.idFront = idFront;
        this.stockLength = stockLength;
        this.stockPcs = stockPcs;
        this.stockPrice = stockPrice;
        this.name = name;
    }
}
