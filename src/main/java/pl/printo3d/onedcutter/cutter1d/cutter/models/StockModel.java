package pl.printo3d.onedcutter.cutter1d.cutter.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Model zawierajacy zadeklarowana przez usera dlugosc surowca oraz ilosc takich samych odcinkow
 */
@Entity
public class StockModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String idFront;
    private String stockLength;
    private String stockPcs;
    private String stockPrice;
    private String name;

    @ManyToOne
    private OrderModel orderModel;

    public StockModel() {
    }


    public StockModel(String idFront, String stockLength, String stockPcs, String stockPrice) {
        this.idFront = idFront;
        this.stockLength = stockLength;
        this.stockPcs = stockPcs;
        this.stockPrice = stockPrice;
    }

    public StockModel(String idFront, String stockLength, String stockPcs, String stockPrice, String name) {
        this.idFront = idFront;
        this.stockLength = stockLength;
        this.stockPcs = stockPcs;
        this.stockPrice = stockPrice;
        this.name = name;
    }

    public String getStockLength() {
        return stockLength;
    }

    public void setStockLength(String stockLength) {
        this.stockLength = stockLength;
    }

    public String getStockPcs() {
        return stockPcs;
    }

    public void setStockPcs(String stockPcs) {
        this.stockPcs = stockPcs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderModel getOrderModel() {
        return orderModel;
    }

    public void setOrderModel(OrderModel orderModel) {
        this.orderModel = orderModel;
    }

    public String getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(String stockPrice) {
        this.stockPrice = stockPrice;
    }

    public String getIdFront() {
        return idFront;
    }

    public void setIdFront(String idFront) {
        this.idFront = idFront;
    }


}
