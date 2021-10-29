package pl.printo3d.onedcutter.cutter1d.models.project;

/**
 * Model zawierajacy pojedynczy wynikowy odcinek na danym precie <p>
 * {@code String barWidthProc} - Zajmowany procent na progressBarze <p>
 * {@code String barText} - Tekst do wyswietlenia na progressBarze <p>
 */
public class ResultBarPieceModel {

    private String barWidthProc;
    private String barText;

    public ResultBarPieceModel(String barWidthProc, String barText) {
        this.barWidthProc = barWidthProc;
        this.barText = barText;
    }

    public String getBarWidthProc() {
        return barWidthProc;
    }

    public void setBarWidthProc(String barWidthProc) {
        this.barWidthProc = barWidthProc;
    }

    public String getBarText() {
        return barText;
    }

    public void setBarText(String barText) {
        this.barText = barText;
    }
}