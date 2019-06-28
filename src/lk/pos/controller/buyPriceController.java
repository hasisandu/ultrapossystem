package lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.PaymentTM;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class buyPriceController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colpaymentid.setStyle("-fx-alignment:center");
        coldate.setStyle("-fx-alignment:center");
        coltime.setStyle("-fx-alignment:center");
        colpaymenttype.setStyle("-fx-alignment:center");
        ss.setStyle("-fx-alignment:center");


        colpaymentid.setCellValueFactory(new PropertyValueFactory<>("paymentid"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colpaymenttype.setCellValueFactory(new PropertyValueFactory<>("paytype"));
        ss.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    @FXML
    private TableView<PaymentTM> ordertable;

    @FXML
    private TableColumn<PaymentTM, Integer> colpaymentid;

    @FXML
    private TableColumn<PaymentTM, String> coldate;

    @FXML
    private TableColumn<PaymentTM, String> coltime;

    @FXML
    private TableColumn<PaymentTM, String> colpaymenttype;

    @FXML
    private TableColumn<PaymentTM, String> ss;

    @FXML
    private ToggleGroup a1;

    @FXML
    private TextField txtorderid;

    @FXML
    private TextField searchtxtfff;

    @FXML
    private TextField txtrest;

    @FXML
    private TextField txtamount;

    @FXML
    private TextField txtbalance;

    @FXML
    private TextField search;

    @FXML
    private RadioButton a;

    @FXML
    private RadioButton b;


    @FXML
    void Save(MouseEvent event) {

        Date d1 = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("YYYY-MM-dd");
        String x1 = sd1.format(d1);

        Date d2 = new Date();
        SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
        String x2 = sd2.format(d2);

        String type = "Cash";
        if (b.isSelected()) {
            type = "Check";
        }


        String sql = "INSERT INTO buypayment(paymenttype,date,time,payment,description,suplyid) VALUE(?,?,?,?,?,?)";
        try {
            boolean saved = CrudUtill.executeUpdate(sql, type, x1, x2, Double.parseDouble(txtamount.getText()), "", Integer.parseInt(txtorderid.getText()));
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Payment Has been Saved !", ButtonType.CLOSE).show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        }

        txtorderid.setText("");
        txtrest.setText("");
        txtamount.setText("");
        txtbalance.setText("");
        search.setText("");

    }


    @FXML
    private JFXButton btn;

    @FXML
    void getorders(KeyEvent event) {


        ordertable.getItems().clear();

        String searchtxt = searchtxtfff.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select * from buypayment where suplyid like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            while (set.next()) {
                ordertable.getItems().add(new PaymentTM(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(7),
                        set.getString(3),
                        set.getString(4),
                        set.getDouble(5),
                        set.getDouble(5) + "",
                        set.getDouble(5)
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void loadorderforpay(MouseEvent event) {

        txtamount.setDisable(false);
        txtbalance.setDisable(false);
        btn.setDisable(false);

        double temptotal = 0.00;
        double payidtemptotal = 0.00;
        double amount = 0.00;

        int searchtxt = Integer.parseInt(search.getText());
        String sql = "select * from buypayment where suplyid=?";
        try {
            ResultSet set = CrudUtill.executeQuery(sql, searchtxt);

            while (set.next()) {
                payidtemptotal += set.getDouble(6);
                txtorderid.setText(set.getString(3));
            }

            String sql2 = "select * from suply where orderid=?";
            ResultSet set2 = CrudUtill.executeQuery(sql2, searchtxt);

            if (set2.next()) {
                temptotal = set2.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        amount = temptotal - payidtemptotal;
        this.txtrest.setText(amount + "");
        if (amount == 0) {
            txtamount.setDisable(true);
            txtbalance.setDisable(true);
            btn.setDisable(true);
        }

    }


    @FXML
    void getbalance(KeyEvent event) {
        double insertamount = 0.00;
        double balance = 0.00;

        if (txtamount.getText() == null) {

        } else {
            balance = Double.parseDouble(txtamount.getText()) - Double.parseDouble(txtamount.getText());
            if (balance > 0) {
                txtbalance.setText(balance + "");
            } else {
                txtbalance.setText("0.00");
            }
        }
    }


}
