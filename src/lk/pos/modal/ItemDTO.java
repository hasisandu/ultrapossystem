package lk.pos.modal;

public class ItemDTO {
    private String itemid;
    private String name;
    private int badgeid;
    private int qtyinshop;
    private int qtyinstock;
    private String brand;
    private double price;
    private String discribe;

    public ItemDTO(String itemid, String name, int badgeid, int qtyinshop, int qtyinstock, String brand, double price, String discribe) {
        this.itemid = itemid;
        this.name = name;
        this.badgeid = badgeid;
        this.qtyinshop = qtyinshop;
        this.qtyinstock = qtyinstock;
        this.brand = brand;
        this.price = price;
        this.discribe = discribe;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(int badgeid) {
        this.badgeid = badgeid;
    }

    public int getQtyinshop() {
        return qtyinshop;
    }

    public void setQtyinshop(int qtyinshop) {
        this.qtyinshop = qtyinshop;
    }

    public int getQtyinstock() {
        return qtyinstock;
    }

    public void setQtyinstock(int qtyinstock) {
        this.qtyinstock = qtyinstock;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDiscribe() {
        return discribe;
    }

    public void setDiscribe(String discribe) {
        this.discribe = discribe;
    }
}
