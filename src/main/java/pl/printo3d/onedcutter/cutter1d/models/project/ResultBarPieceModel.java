package pl.printo3d.onedcutter.cutter1d.models.project;

import lombok.Getter;
import lombok.Setter;

/**
 * Model zawierajacy pojedynczy wynikowy odcinek na danym precie <p>
 * {@code String barWidthProc} - Zajmowany procent na progressBarze <p>
 * {@code String barText} - Tekst do wyswietlenia na progressBarze <p>
 */
@Getter
@Setter
public class ResultBarPieceModel {

    private String barWidthProc;
    private String barText;

    public ResultBarPieceModel(String barWidthProc, String barText) {
        this.barWidthProc = barWidthProc;
        this.barText = barText;
    }
}