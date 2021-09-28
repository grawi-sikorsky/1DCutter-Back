package pl.printo3d.onedcutter.cutter1d.cutter.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Incubating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
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

    /**
     * Pierwsza metoda rozwiazania problemu
     * TODO: zrobic jeszcze algorytm bestFit, worstFit
     * @param incomingOrder
     * @return
     */
    public CutterProduct firstFit(OrderModel incomingOrder) {

        CutterProduct cutterProduct = new CutterProduct();

        // lista roboczych kawalkow - kazdy zawiera info o cieciach oraz o ilosci wolnego miejsca na nim
        List<WorkPiece> workPiecesList = new ArrayList<WorkPiece>();

        // lista czesci do optymalizacji
        List<Double> partsList = new ArrayList<Double>();
       
        // rewers..
        partsList = sortReverse(incomingOrder.getCutList());

        Integer tempStockCounter = 0, tempStockIterator = 0;
        List<Double> partsDone = new ArrayList<Double>();
        List<Double> partsRemaining = new ArrayList<Double>(partsList);

        for (Double part : partsList) {
            // 1. CHWYC NOWA CZESC
            //System.out.println("Next part is: " + part);

            // 2. JESLI NA OBECNYM SUROWCU NIE MA WOLNEGO MIEJSCA NA TE CZESC?
            if (! workPiecesList.stream().anyMatch(work -> work.freeSpace(incomingOrder.getCutOptions().getOptionSzrank()) >= part)) {
                // 3. JESLI DOSTEPNA JEST JESZCZE JEDNA SZTUKA SUROWCA DANEGO TYPU/DLUGOSCI
                if (tempStockCounter < Integer.parseInt(incomingOrder.getStockList().get(tempStockIterator).getStockPcs())) {
                    // 4. DODAJ SUROWIEC DANEGO TYPU
                    workPiecesList.add(new WorkPiece(incomingOrder.getStockList().get(tempStockIterator).getIdFront(), Double.valueOf(incomingOrder.getStockList().get(tempStockIterator).getStockLength())));
                    //System.out.println("No free space left, adding new stock piece: " + incomingOrder.getStockList().get(tempStockIterator).getStockLength());
                    tempStockCounter++;
                } else // 5. BRAKUJE JUZ SUROWCA DANEGO TYPU:
                {
                    // 6. JESTLI SA DOSTEPNE INNE TYPY/DLUGOSCI SUROWCA:
                    if (tempStockIterator < incomingOrder.getStockList().size() - 1) {
                        tempStockIterator++;
                        tempStockCounter = 0;
                        // 7. DODAJ SUROWIEC NOWEGO TYPU / ZERUJ LICZNIKI
                        workPiecesList.add(new WorkPiece(incomingOrder.getStockList().get(tempStockIterator).getIdFront(), Double.valueOf(incomingOrder.getStockList().get(tempStockIterator).getStockLength())));
                        //System.out.println("No free space left, adding new stock piece: " + incomingOrder.getStockList().get(tempStockIterator).getStockLength());
                        tempStockCounter++;
                    } else {
                        // BRAK SUROWCA
                        //System.out.println("NOT ENOF STOCK AT ALL!");
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

        cutterProduct.setWorkPiecesList(workPiecesList);    // 10. zapisujemy do CutterProduct liste do dalszych dzialan
        cutterProduct.setNotFittedPieces(partsRemaining);   // 11. zapisujemy do CutterProduct liste pozostalych kawalkow (niezoptymalizowanych)

        return cutterProduct;
    }

    public CutterProduct newAlgo(CutterProduct incomingSolution, OrderModel incomingOrder){
        CutterProduct cutterProduct = new CutterProduct();
        List<Double> differentCuts = new ArrayList<Double>();
        List<WorkPiece> patternList = incomingSolution.getWorkPiecesList();
        Integer currentSolutionQuality = incomingSolution.getWorkPiecesList().size();
        Integer newSolutionQuality = currentSolutionQuality;
        Double obecnieFree;
        Integer loops = 5;

        for( int i=0; i < incomingOrder.getCutList().size()/2; ++i)
        {
            differentCuts.add( Double.parseDouble( incomingOrder.getCutList().get(i).getCutLength() ) );
        }
        differentCuts.stream().forEach(System.out::println);

        while(loops > 0)
        {
            loops--;
        }



        rekuTest(differentCuts);
        

        // 3. zwrocic calosc w postaci cutterProduct, dalej to już bajka..

        return cutterProduct;
    }


    private void permutacja(List<Double> incCuts, int l, int r)
    {
        String str;
        if (l == r)
            System.out.println(incCuts);
        else {
            for (int i = l; i <= r; i++) {
                Collections.swap(incCuts, l, i);
                permutacja(incCuts, l + 1, r);
                Collections.swap(incCuts, l, i);
            }
        }
    }

    /**
     * Swap Characters at position
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    public String swap(String a, int i, int j)
    {
        char temp;
        char[] charArray = a.toCharArray();
        temp = charArray[i];
        charArray[i] = charArray[j];
        charArray[j] = temp;
        return String.valueOf(charArray);
    }

    public int rekuTest(List<Double> incCuts)
    {
        int n = incCuts.size();
        permutacja(incCuts, 0, n - 1);
        System.out.println("DONE");
        return 0;
    }
}
