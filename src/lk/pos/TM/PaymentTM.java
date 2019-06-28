package lk.pos.TM;

public class PaymentTM {
    private int paymentid;
    private String paytype;
    private int orderid;
    private String date;
    private String time;
    private double amount;
    private String desc;
    private double f;

    public PaymentTM(int paymentid, String paytype, int orderid, String date, String time, double amount, String desc, double f) {
        this.paymentid = paymentid;
        this.paytype = paytype;
        this.orderid = orderid;
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.desc = desc;
        this.f = f;
    }

    public int getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

    public double getF() {
        return f;
    }

    public void setF(int f) {
        this.f = f;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}