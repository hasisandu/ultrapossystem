package lk.pos.TM;

public class CustomerTM {
    private String customerid;
    private String firstname;
    private String lastname;
    private String contact;

    public CustomerTM(String customerid, String firstname, String lastname, String contact) {
        this.customerid = customerid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contact = contact;
    }

    public CustomerTM() {
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "CustomerTM{" +
                "customerid='" + customerid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
