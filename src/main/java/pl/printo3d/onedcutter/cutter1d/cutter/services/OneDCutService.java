package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.printo3d.onedcutter.cutter1d.cutter.models.CutModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.CutterProduct;
import pl.printo3d.onedcutter.cutter1d.cutter.models.OrderModel;
import pl.printo3d.onedcutter.cutter1d.cutter.models.WorkPiece;

@Service
public class OneDCutService {

    public OneDCutService() {
    }

    @Autowired
    ResultService resultService;


    // Tworzy liste elementow do ciecia na podstawie wpisanych danych
    public List<Double> makePartList(List<CutModel> cutList) {
        List<Double> partsList = new ArrayList<Double>();
 
        for (CutModel c : cutList) {
            for (int i = 0; i < Integer.parseInt(c.getCutPcs()); ++ i) {
                partsList.add(Double.parseDouble(c.getCutLength()));
            }
        }
        return partsList;
    }

    // Sortowanie odwrotne
    public List<Double> sortReverse(List<CutModel> cutList) {
        List<Double> partsList = new ArrayList<Double>();
        partsList = makePartList(cutList);
        Collections.sort(partsList);
        Collections.reverse(partsList);

        return partsList;
    }

    // 1. Pierwsza metoda rozwiazania problemu
    public CutterProduct firstFit(OrderModel incomingOrder) {

        CutterProduct cutterProduct = new CutterProduct();
        // lista roboczych kawalkow - kazdy zawiera info o cieciach oraz o ilosci
        // wolnego miejsca na nim
        List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();

        List<Double> partsList = new ArrayList<Double>();
       
        // rewers..
        partsList = sortReverse(incomingOrder.getCutList());

        Integer tempStockCounter = 0, tempStockIterator = 0;
        List<Double> partsDone = new ArrayList<Double>();
        List<Double> partsRemaining = new ArrayList<Double>(partsList);

        for (Double part : partsList) {
            // 1. CHWYC NOWA CZESC
            System.out.println("Next part is: " + part);

            // 2. JESLI NA OBECNYM SUROWCU NIE MA WOLNEGO MIEJSCA NA TE CZESC?
            if (! workPiecesList.stream().anyMatch(work -> work.freeSpace(incomingOrder.getCutOptions().getOptionSzrank()) >= part)) {
                // 3. JESLI DOSTEPNA JEST JESZCZE JEDNA SZTUKA SUROWCA DANEGO TYPU/DLUGOSCI
                if (tempStockCounter < Integer.parseInt(incomingOrder.getStockList().get(tempStockIterator).getStockPcs())) {
                    // 4. DODAJ SUROWIEC DANEGO TYPU
                    workPiecesList.add(new WorkPiece(incomingOrder.getStockList().get(tempStockIterator).getIdFront(), Double.valueOf(incomingOrder.getStockList().get(tempStockIterator).getStockLength())));
                    System.out.println("No free space left, adding new stock piece: " + incomingOrder.getStockList().get(tempStockIterator).getStockLength());
                    tempStockCounter++;
                } else // 5. BRAKUJE JUZ SUROWCA DANEGO TYPU:
                {
                    // 6. JESTLI SA DOSTEPNE INNE TYPY/DLUGOSCI SUROWCA:
                    if (tempStockIterator < incomingOrder.getStockList().size() - 1) {
                        tempStockIterator++;
                        tempStockCounter = 0;
                        // 7. DODAJ SUROWIEC NOWEGO TYPU / ZERUJ LICZNIKI
                        workPiecesList.add(new WorkPiece(incomingOrder.getStockList().get(tempStockIterator).getIdFront(), Double.valueOf(incomingOrder.getStockList().get(tempStockIterator).getStockLength())));
                        System.out.println("No free space left, adding new stock piece: " + incomingOrder.getStockList().get(tempStockIterator).getStockLength());
                        tempStockCounter++;
                    } else {
                        // BRAK SUROWCA
                        System.out.println("NOT ENOF STOCK AT ALL!");
                    }
                }
            }

            // 8. PRZESZUKAJ LISTE UZYWANYCH SUROWCOW W POSZUKIWANIU MIEJSCA NA NOWA CZESC
            for (WorkPiece work : workPiecesList) {
                if (work.freeSpace(incomingOrder.getCutOptions().getOptionSzrank()) >= part) {
                    work.cut(part);
                    partsDone.add(part);
                    break; // koniecznie wyskoczyc z loopa!
                }
            }
        }
        // 9. STWORZ LISTE CZESCI KTORE NIE ZMIESCILY SIE NA ZADNYM SUROWCU:
        partsDone.forEach(e -> partsRemaining.remove(e));

        // 10. PRZEKAZUJEMY LISTE<DOUBLE> do uslugi ktora tworzy, zapisuje i zwraca bary
        //resultService.getRemainBars(partsRemaining);


        cutterProduct.setWorkPiecesList(workPiecesList);
        cutterProduct.setNotFittedPieces(partsRemaining);

        return cutterProduct;
    }

}
