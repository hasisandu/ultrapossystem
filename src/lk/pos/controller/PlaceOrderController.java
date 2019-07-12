package lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.CustomerSearchTM;
import lk.pos.TM.CustomerTM;
import lk.pos.TM.ItemSearchTM;
import lk.pos.TM.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceOrderController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        colcustomerid.setStyle("-fx-alignment:center");
        colname.setStyle("-fx-alignment:center");
        colcontact.setStyle("-fx-alignment:center");
        coladdress.setStyle("-fx-alignment:center");
        coltblid.setStyle("-fx-alignment:center");
        colitemtblename.setStyle("-fx-alignment:center");
        colordertbldescription.setStyle("-fx-alignment:center");
        tblqty.setStyle("-fx-alignment:center");
        colpricetbl.setStyle("-fx-alignment:center");
        colorderitemid.setStyle("-fx-alignment:center");
        colordername.setStyle("-fx-alignment:center");
        colorderdescription.setStyle("-fx-alignment:center");
        colorderqty.setStyle("-fx-alignment:center");
        colorderamount.setStyle("-fx-alignment:center");

        colcustomerid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("conatct"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("adtdress"));

        coltblid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colitemtblename.setCellValueFactory(new PropertyValueFactory<>("name"));
        colordertbldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colpricetbl.setCellValueFactory(new PropertyValueFactory<>("amount"));

        colorderitemid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colordername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colorderdescription.setCellValueFactory(new PropertyValueFactory<>("describe"));
        colorderqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colorderamount.setCellValueFactory(new PropertyValueFactory<>("amount"));

        l.setDisable(true);
        k.setDisable(true);

    }

    public double totalcost = 0.00;

    @FXML
    private TextField searchcustomertxt;

    @FXML
    private TextField txtorderitemid;

    @FXML
    private TableView<ItemSearchTM> tblitem;

    @FXML
    private TableColumn<ItemSearchTM, String> coltblid;

    @FXML
    private TableColumn<ItemSearchTM, String> colitemtblename;

    @FXML
    private TableColumn<ItemSearchTM, String> colordertbldescription;

    @FXML
    private TableColumn<ItemSearchTM, Integer> tblqty;

    @FXML
    private TableColumn<ItemSearchTM, Double> colpricetbl;

    @FXML
    private TableView<CustomerSearchTM> tblcustomer;

    @FXML
    private TableColumn<CustomerSearchTM, String> colcustomerid;

    @FXML
    private TableColumn<CustomerSearchTM, String> colname;

    @FXML
    private TableColumn<CustomerSearchTM, String> colcontact;

    @FXML
    private TableColumn<CustomerSearchTM, String> coladdress;

    private ObservableList<OrderTM> fuck = FXCollections.observableArrayList();

    @FXML
    private JFXTextField txtcusid;

    @FXML
    private JFXTextField txtcusname;

    @FXML
    private TableView<OrderTM> tblorder;

    @FXML
    private TableColumn<OrderTM, String> colorderitemid;

    @FXML
    private TableColumn<OrderTM, String> colordername;

    @FXML
    private TableColumn<OrderTM, String> colorderdescription;

    @FXML
    private TableColumn<OrderTM, Integer> colorderqty;

    @FXML
    private TableColumn<OrderTM, Double> colorderamount;

    @FXML
    private JFXTextField txtitemid;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtqty;

    @FXML
    private JFXTextArea txtdescribe;

    @FXML
    private TextField unitprice;

    @FXML
    private TextField qty;

    @FXML
    private TextField amount;

    @FXML
    public Text total;
    public String cusid = "C001";

    private ArrayList<OrderTM> orderlist = new ArrayList<>();
    private ObservableList li = FXCollections.observableArrayList();


    Map<String, OrderTM> map = new HashMap<>();


    @FXML
    void addtocart(MouseEvent event) {

        tblitem.getItems().clear();

        String txtqtys = qty.getText();

        Pattern pqty = Pattern.compile("[0-9]{1,4}");

        Matcher mqty = pqty.matcher(txtqtys);

        boolean bqty = mqty.matches();

        if (bqty) {

            l.setDisable(false);
            k.setDisable(false);


//            ================================================================================================
            tblorder.getItems().clear();
            OrderTM orderTMs = new OrderTM(txtitemid.getText(), txtname.getText(), txtdescribe.getText(), Integer.parseInt(qty.getText()), Double.parseDouble(amount.getText()));

            map.put(txtitemid.getText(), orderTMs);
            OrderTM orderfuck = null;
            orderfuck = map.get(txtitemid.getText());

            totalcost = 0.00;
            for (Map.Entry<String, OrderTM> entry : map.entrySet()
                    ) {
                tblorder.getItems().add(entry.getValue());
                totalcost += entry.getValue().getAmount();
            }


//            ================================================================================================


            total.setText(totalcost + " LKR");
            qty.setText("");
            amount.setText("");
            txtitemid.setText("");
            txtdescribe.setText("");
            txtname.setText("");
            txtqty.setText("");
            unitprice.setText("");
        } else {
            new Alert(Alert.AlertType.ERROR, "Fields Missing !", ButtonType.CLOSE).show();
        }


    }

    @FXML
    void count(KeyEvent event) {

        if (qty.getText().equalsIgnoreCase("")) {
            amount.setText("");
        } else {
            if (te <= Integer.parseInt(qty.getText())) {
                c.setDisable(true);
            } else {
                c.setDisable(false);
            }
            double tempitemprice = Double.parseDouble(unitprice.getText());
            double tempttl = tempitemprice * Integer.parseInt(qty.getText());
            amount.setText(tempttl + "");
        }

    }

    @FXML
    void submitOrder(MouseEvent event) {


        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../view/SellPaymentPage.fxml"));

            Parent p = loader.load();
            Scene scene = new Scene(p);
            Stage stage = new Stage();
            stage.setScene(scene);

            SellingPriceController sellingPriceController = loader.getController();
            sellingPriceController.setControll(this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private JFXButton l;

    @FXML
    private JFXButton k;

    @FXML
    void getCustomer(KeyEvent event) {

        tblcustomer.getItems().clear();

        String searchtxt = searchcustomertxt.getText();
        searchtxt = "'" + searchtxt + "%'";
        System.out.println(searchtxt);
        String sql = "select customerid,firstname,lastname,address,contact from customer where customerid like" + searchtxt + " || city like" + searchtxt + " || firstname like" + searchtxt + " || lastname like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            ArrayList<CustomerTM> tempCustomer = new ArrayList<>();

            while (set.next()) {
                tblcustomer.getItems().add(new CustomerSearchTM(
                        set.getString("customerid"),
                        set.getString("firstname") + " " + set.getString("lastname"),
                        set.getString("contact"),
                        set.getString("address")
                ));
//                .add(tempCustomer);
            }
//            customerearchlist.add(tempCustomer);
//            System.out.println(customerearchlist);
//            searchtable.getItems().add(customerearchlist);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private JFXButton c;
    private int te;

    @FXML
    void getItem(KeyEvent event) {

        if (cusid.equalsIgnoreCase("C001")) {
            new Alert(Alert.AlertType.WARNING, "Imposable! Select A Customer...", ButtonType.CLOSE).show();

        } else {
            tblitem.getItems().clear();

            String searchtxt = txtorderitemid.getText();
            searchtxt = "'" + searchtxt + "%'";
            String sql = "select a.itemid, b.id ,a.brand,a.name,b.qty,a.price,a.badgeid,a.describedetail from stock a join shop b on a.itemid=b.id && a.itemid like" + searchtxt + " || a.brand like" + searchtxt + " || a.name like" + searchtxt + " || a.describedetail like" + searchtxt + "";
            try {
                ResultSet set = CrudUtill.executeQuery(sql);

                while (set.next()) {
                    te = set.getInt("qty");
                    tblitem.getItems().add(new ItemSearchTM(
                            set.getString("itemid"),
                            set.getString("name"),
                            set.getString("describedetail"),
                            set.getInt("qty"),
                            set.getDouble("price")
                    ));
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }


    @FXML
    void selectcustomer(MouseEvent event) {
        String id = tblcustomer.getSelectionModel().getSelectedItem().getItemid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from customer where customerid=?", id);

            if (set.next()) {
                txtcusid.setText(set.getString(1));
                txtcusname.setText(set.getString(2) + " " + (set.getString(3)));
                cusid = txtcusid.getText();
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Customer Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void selectItem(MouseEvent event) {
        String id = tblitem.getSelectionModel().getSelectedItem().getItemid();
        try {
            String sql = "select a.itemid, b.id ,a.brand,a.name,b.qty,a.price,a.badgeid,a.describedetail from stock a join shop b on a.itemid=b.id && a.itemid=?";
            ResultSet set = CrudUtill.executeQuery(sql, id);
            if (set.next()) {
                txtitemid.setText(set.getString("itemid"));
                txtname.setText(set.getString("name"));
                txtqty.setText(set.getInt("qty") + "");
                te = set.getInt("qty");
                txtdescribe.setText(set.getString("describedetail"));
                unitprice.setText(set.getDouble("price") + "");
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Product Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    String itemid = "no";

    @FXML
    void remove(MouseEvent event) {

        if (!itemid.equalsIgnoreCase("no")) {
            String xx = tblorder.getSelectionModel().getSelectedItem().getItemid();
            tblorder.getItems().clear();
            map.remove(xx);
            totalcost = 0.00;
            for (Map.Entry<String, OrderTM> entry : map.entrySet()
                    ) {
                tblorder.getItems().add(entry.getValue());
                totalcost += entry.getValue().getAmount();
            }

            new Alert(Alert.AlertType.WARNING, " Product Has Removed...", ButtonType.CLOSE).show();
            itemid = "no";

        } else {
            new Alert(Alert.AlertType.WARNING, " Select a Product From Table...", ButtonType.CLOSE).show();

        }

    }


    @FXML
    void getItemId(MouseEvent event) {
        itemid = tblorder.getSelectionModel().getSelectedItem().getItemid();

    }


}
