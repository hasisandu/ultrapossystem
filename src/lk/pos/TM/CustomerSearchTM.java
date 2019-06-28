package lk.pos.TM;

public class CustomerSearchTM {
    private String itemid;
    private String name;
    private String conatct;
    private String adtdress;

    public CustomerSearchTM(String itemid, String name, String conatct, String adtdress) {
        this.itemid = itemid;
        this.name = name;
        this.conatct = conatct;
        this.adtdress = adtdress;
    }

    public CustomerSearchTM() {
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

    public String getConatct() {
        return conatct;
    }

    public void setConatct(String conatct) {
        this.conatct = conatct;
    }

    public String getAdtdress() {
        return adtdress;
    }

    public void setAdtdress(String adtdress) {
        this.adtdress = adtdress;
    }

    @Override
    public String toString() {
        return "CustomerSearchTM{" +
                "itemid='" + itemid + '\'' +
                ", name='" + name + '\'' +
                ", conatct='" + conatct + '\'' +
                ", adtdress='" + adtdress + '\'' +
                '}';
    }
}
