package lk.pos.TM;

public class OrderTM {
    private String itemid;
    private String name;
    private String describe;
    private int qty;
    private double amount;

    public OrderTM(String itemid, String name, String describe, int qty, double amount) {
        this.itemid = itemid;
        this.name = name;
        this.describe = describe;
        this.qty = qty;
        this.amount = amount;
    }

    public OrderTM() {

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "OrderTM{" +
                "itemid='" + itemid + '\'' +
                ", name='" + name + '\'' +
                ", describe='" + describe + '\'' +
                ", qty=" + qty +
                ", amount=" + amount +
                '}';
    }
}
