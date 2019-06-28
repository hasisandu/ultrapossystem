package lk.pos.controller;

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
import java.util.ResourceBundle;

public class PlaceOrderController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    }

    private double totalcost = 0.00;

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
    private Text total;

    private ArrayList<OrderTM> orderlist = new ArrayList<>();

    @FXML
    void addtocart(MouseEvent event) {

        tblorder.getItems().clear();

        OrderTM tm = new OrderTM(
                txtitemid.getText(),
                txtname.getText(),
                txtdescribe.getText(),
                Integer.parseInt(qty.getText()),
                Double.parseDouble(amount.getText()));

        if (orderlist.size() > 0) {
            for (OrderTM om : orderlist) {
                if (om.getItemid().equalsIgnoreCase(tm.getItemid())) {
                    OrderTM temp = new OrderTM();
                    int tempqty = om.getQty();
                    tempqty += Integer.parseInt(qty.getText());
                    double tempamount = om.getAmount();
                    tempamount += Double.parseDouble(amount.getText());

                    temp = new OrderTM(om.getItemid(), om.getName(), om.getDescribe(), tempqty, tempamount);
                    orderlist.remove(om);
                    orderlist.add(tm);
                } else {
                    orderlist.add(tm);

                }
                totalcost += om.getAmount();

                System.out.println(om);
            }

            orderlist.add(tm);

        } else {
            orderlist.add(tm);
        }

        System.out.println(orderlist.size());

        for (OrderTM t : orderlist) {
            tblorder.getItems().add(t);
        }


        total.setText(totalcost + " LKR");
        qty.setText("");
        amount.setText("");

    }

    @FXML
    void count(KeyEvent event) {

        if (qty.getText().equalsIgnoreCase("")) {
            amount.setText("");
        } else {
            double tempitemprice = Double.parseDouble(unitprice.getText());
            double tempttl = tempitemprice * Integer.parseInt(qty.getText());
            amount.setText(tempttl + "");
        }

    }

    @FXML
    void submitOrder(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/SellPaymentPage.fxml"));
            Stage stage = new Stage();
            Scene s = new Scene(root);
            stage.setScene(s);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    void getItem(KeyEvent event) {

        tblitem.getItems().clear();

        String searchtxt = txtorderitemid.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select itemid,brand,name,qtyonshop,price,badgeid,description from item where itemid like" + searchtxt + " || brand like" + searchtxt + " || name like" + searchtxt + " || description like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            while (set.next()) {
                tblitem.getItems().add(new ItemSearchTM(
                        set.getString("itemid"),
                        set.getString("name"),
                        set.getString("description"),
                        set.getInt("qtyonshop"),
                        set.getDouble("price")
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            ResultSet set = CrudUtill.executeQuery("select * from item where itemid=?", id);

            if (set.next()) {
                txtitemid.setText(set.getString(1));
                txtname.setText(set.getString(3));
                txtqty.setText(set.getInt(4) + "");
                txtdescribe.setText(set.getString(7));
                unitprice.setText(set.getDouble(5) + "");
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Product Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
