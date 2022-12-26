package pl.printo3d.onedcutter.cutter1d.models.project;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

/**
 * Model zawierajacy ustawienia uzytkownika - dla kazdego rozkroju osobne <p>
 * {@code boolean optionStackResult} - Czy stackujemy wyniki <p>
 * {@code Double optionSzrank} - Szerokosc ciecia / pily <p>
 * {@code boolean optionPrice} - Czy liczymy koszty <p>
 * {@code boolean optionAlgo} - Drugi algorytm <p>
 */
@Entity
@Getter
@Setter
public class CutOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean optionStackResult;
    private Double optionSzrank;
    private boolean optionPrice;
    private boolean optionAlgo;
    private Integer optionIterations;


    public CutOptions() {
    }

    public CutOptions(boolean optionStackResult, Double optionSzrank, boolean optionPrice, boolean optionAlgo, Integer optionIterations) {
        this.optionStackResult = optionStackResult;
        this.optionSzrank = optionSzrank;
        this.optionPrice = optionPrice;
        this.optionAlgo = optionAlgo;
        this.optionIterations = optionIterations;
    }
}
