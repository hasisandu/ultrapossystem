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

        String txtqtys = qty.getText();

        Pattern pqty = Pattern.compile("[0-9]{1,4}");

        Matcher mqty = pqty.matcher(txtqtys);

        boolean bqty = mqty.matches();

        if (bqty) {
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
    private JFXButton c;
    private int te;

    @FXML
    void getItem(KeyEvent event) {

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

}
