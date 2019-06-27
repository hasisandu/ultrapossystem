package lk.pos.TM;

public class ItemTM {
    private String itemid;
    private String name;
    private String brand;
    private int badge;
    private int qty;
    private double price;

    public ItemTM(String itemid, String name, String brand, int badge, int qty, double price) {
        this.itemid = itemid;
        this.name = name;
        this.brand = brand;
        this.badge = badge;
        this.qty = qty;
        this.price = price;
    }

    public ItemTM() {

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
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

    @Override
    public String toString() {
        return "ItemTM{" +
                "itemid='" + itemid + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", badge=" + badge +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}
