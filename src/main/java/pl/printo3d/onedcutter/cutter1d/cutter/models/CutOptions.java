package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Model zawierajacy ustawienia uzytkownika - dla kazdego rozkroju osobne <p>
 * {@code boolean optionStackResult} - Czy stackujemy wyniki <p>
 * {@code Double optionSzrank} - Szerokosc ciecia / pily <p>
 * {@code boolean optionPrice} - Czy liczymy koszty <p>
 * {@code boolean optionAlgo} - Drugi algorytm <p>
 */
@Entity
public class CutOptions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean optionStackResult;
    private Double optionSzrank;
    private boolean optionPrice;
    private boolean optionAlgo;


    public CutOptions() {
    }

    public CutOptions(boolean optionStackResult, Double optionSzrank, boolean optionPrice, boolean optionAlgo) {
        this.optionStackResult = optionStackResult;
        this.optionSzrank = optionSzrank;
        this.optionPrice = optionPrice;
        this.optionAlgo = optionAlgo;
    }


    public boolean isOptionStackResult() {
        return optionStackResult;
    }

    public void setOptionStackResult(boolean optionStackResult) {
        this.optionStackResult = optionStackResult;
    }

    public Double getOptionSzrank() {
        return optionSzrank;
    }

    public void setOptionSzrank(Double optionSzrank) {
        this.optionSzrank = optionSzrank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(boolean optionPrice) {
        this.optionPrice = optionPrice;
    }

    public boolean isOptionAlgo() {
        return optionAlgo;
    }

    public void setOptionAlgo(boolean optionAlgo) {
        this.optionAlgo = optionAlgo;
    }

}
