package lk.pos.TM;

public class ExternalTM {
    private int id;
    private String reason;
    private String date;
    private String time;
    private double price;

    public ExternalTM(int id, String reason, String date, String time, double price) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.time = time;
        this.price = price;
    }

    public ExternalTM() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ExternalTM{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", price=" + price +
                '}';
    }
}
