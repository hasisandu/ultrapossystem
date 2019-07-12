package lk.pos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.FuckinClass;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadCustomerCount();
        loadItemCount();
        loadLessthan10();
        loadSupplyCount();
        loadDateAndTime();
        loadDailyIncome();

        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Default.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<FuckinClass> todayids = new ArrayList<>();
    private ArrayList<FuckinClass> monthayids = new ArrayList<>();
    private Double ttl = 0.00;
    private Double monthttl = 0.00;

    private void loadDailyIncome() {

        ttl = 0.00;
        Date d1 = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("YYYY-MM-dd");
        String x1 = sd1.format(d1);

        todayids.clear();

        String sql = "select itemid,qty from orderdetail where date=?";
        try {
            ResultSet resultSet = CrudUtill.executeQuery(sql, x1);

            while (resultSet.next()) {
                todayids.add(new FuckinClass(resultSet.getString(1), Integer.parseInt(resultSet.getString(2))));
            }

            for (FuckinClass xyz : todayids
                    ) {
                String sql2 = "select price,buyprice from stock where itemid=?";
                ResultSet se = CrudUtill.executeQuery(sql2, xyz.getId());
                if (se.next()) {
                    ttl += ((se.getDouble(1) - se.getDouble(2)) * xyz.getQty());
                }
            }

            todayincome.setText(ttl + "");


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private Text txtdate;

    @FXML
    private Text txttime;


    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-YYYY");
        String datetxt = simpleDateFormat.format(date);
        txtdate.setText(datetxt);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                Calendar time = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                                txttime.setText(simpleDateFormat.format(time.getTime()));
                            }
                        }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
        loadDailyIncome();
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
