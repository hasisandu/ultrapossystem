package lk.pos.modal;

public class SellPayment {
    private int paymentid;
    private String paymenttype;
    private int orderid;
    private String date;
    private String time;
    private double payment;
    private String describe;

    public SellPayment(int paymentid, String paymenttype, int orderid, String date, String time, double payment, String describe) {
        this.paymentid = paymentid;
        this.paymenttype = paymenttype;
        this.orderid = orderid;
        this.date = date;
        this.time = time;
        this.payment = payment;
        this.describe = describe;
    }

    public SellPayment() {
    }

    public int getPaymentid() {
        return paymentid;
    }

    public void setPaymentid(int paymentid) {
        this.paymentid = paymentid;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
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

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
