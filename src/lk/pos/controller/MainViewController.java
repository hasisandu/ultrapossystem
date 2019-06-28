package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.pos.DBUtility.CrudUtill;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadCustomerCount();
        loadItemCount();
        loadLessthan10();
        loadSupplyCount();

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Default.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSupplyCount() {

        try {
            ResultSet set = CrudUtill.executeQuery("select count(suplierid) from suplier");
            if (set.next()) {
                int tempcount = set.getInt(1);
                suppliercount.setText(tempcount + "");
            } else {
                suppliercount.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private Text totalitems;

    @FXML
    private Text todayincome;

    @FXML
    private Text monthincome;

    @FXML
    private Text suppliercount;


    @FXML
    private Text fuckinqty;

    @FXML
    private Text customercount;

    @FXML
    private Text debdesCount;


    @FXML
    private AnchorPane mainwindow;


    public void PageLoader(String name) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/" + name + ".fxml"));
            mainwindow.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadCustomerPage(MouseEvent event) {
        PageLoader("ManageCustomerView");
    }

    @FXML
    void loadItemPage(MouseEvent event) {
        PageLoader("ManageItemView");
    }

    @FXML
    void loadPlaceOrderPage(MouseEvent event) {
        PageLoader("PlaceOrderAria");
    }

    @FXML
    void loadExternalImportPage(MouseEvent event) {
        PageLoader("ExternalImportPage");
    }

    @FXML
    void loadSellPaymentPage(MouseEvent event) {
        PageLoader("SellPaymentPage");
    }

    @FXML
    void loadSuplierPage(MouseEvent event) {
        PageLoader("ManageSupliersPage");
    }

    @FXML
    void loadStockPage(MouseEvent event) {
        PageLoader("StockRefillPage");
    }

    @FXML
    void loadBuyPaymentPage(MouseEvent event) {
        PageLoader("BuyPayment");
    }

    @FXML
    void loadReportPage(MouseEvent event) {
        PageLoader("ReportsPage");
    }

    private void loadCustomerCount() {
        try {
            ResultSet set = CrudUtill.executeQuery("select count(customerid) from customer");
            if (set.next()) {
                int tempcount = set.getInt(1);
                customercount.setText(tempcount + "");
            } else {
                customercount.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadItemCount() {
        try {
            ResultSet set = CrudUtill.executeQuery("select count(itemid) from stock");
            if (set.next()) {
                int tempcount = set.getInt(1);
                totalitems.setText(tempcount + "");
            } else {
                totalitems.setText("0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadLessthan10() {
        try {
            ResultSet set = CrudUtill.executeQuery("select count(itemid) from stock where qtyonstock<10");
            if (set.next()) {
                fuckinqty.setText(set.getInt(1) + "");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
