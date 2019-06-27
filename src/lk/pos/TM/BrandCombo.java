package lk.pos.TM;

public class BrandCombo {
    private int id;
    private String name;

    public BrandCombo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public BrandCombo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String
    toString() {
        return "BrandCombo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
