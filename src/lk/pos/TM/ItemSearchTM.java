package lk.pos.TM;

public class ItemSearchTM {
    private String itemid;
    private String name;
    private String description;
    private int qty;
    private double amount;

    public ItemSearchTM(String itemid, String name, String description, int qty, double amount) {
        this.itemid = itemid;
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.amount = amount;
    }

    public ItemSearchTM() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "ItemSearchTM{" +
                "itemid='" + itemid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", amount=" + amount +
                '}';
    }
}
