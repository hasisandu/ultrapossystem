package lk.pos.modal;

public class OrderDTO {

    private int orderid;
    private String customerid;
    private double debd;
    private String date;
    private String time;
    private double ttl;
    private double discount;
    private String describe;


    public OrderDTO(int orderid, String customerid, double debd, String date, String time, double ttl, double discount, String describe) {
        this.orderid = orderid;
        this.customerid = customerid;
        this.debd = debd;
        this.date = date;
        this.time = time;
        this.ttl = ttl;
        this.discount = discount;
        this.describe = describe;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public double getDebd() {
        return debd;
    }

    public void setDebd(double debd) {
        this.debd = debd;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTtl() {
        return ttl;
    }

    public void setTtl(double ttl) {
        this.ttl = ttl;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
