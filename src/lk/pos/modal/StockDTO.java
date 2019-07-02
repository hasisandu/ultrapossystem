package lk.pos.modal;

public class StockDTO {

    private String itemid;
    private int badgeid;
    private String name;
    private String brand;
    private int qty;
    private double price;
    private String describe;
    private int stockqty;

    public StockDTO(String itemid, int badgeid, String name, String brand, int qty, double price, String describe, int stockqty) {
        this.itemid = itemid;
        this.badgeid = badgeid;
        this.name = name;
        this.brand = brand;
        this.qty = qty;
        this.price = price;
        this.describe = describe;
        this.stockqty = stockqty;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getStockqty() {
        return stockqty;
    }

    public void setStockqty(int stockqty) {
        this.stockqty = stockqty;
    }

    public int getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(int badgeid) {
        this.badgeid = badgeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
