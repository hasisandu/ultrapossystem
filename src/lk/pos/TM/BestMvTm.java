package lk.pos.TM;

public class BestMvTm {
    private String itemid;
    private String itemname;
    private int qty;

    public BestMvTm(String itemid, String itemname, int qty) {
        this.itemid = itemid;
        this.itemname = itemname;
        this.qty = qty;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
