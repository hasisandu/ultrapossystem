package lk.pos.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.modal.SellPayment;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BuyPaymentController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setMonth();

        colorderid.setStyle("-fx-alignment:center");
        colpayid.setStyle("-fx-alignment:center");
        colpaytype.setStyle("-fx-alignment:center");
        coldate.setStyle("-fx-alignment:center");
        coltime.setStyle("-fx-alignment:center");
        colamount.setStyle("-fx-alignment:center");

        colorderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colpayid.setCellValueFactory(new PropertyValueFactory<>("paymentid"));
        colpaytype.setCellValueFactory(new PropertyValueFactory<>("paymenttype"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colamount.setCellValueFactory(new PropertyValueFactory<>("payment"));

    }

    private void setMonth() {
        txtmonth.getItems().addAll(
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        );
    }

    @FXML
    private TextField txtorderid;

    @FXML
    private ComboBox<String> txtmonth;

    @FXML
    private JFXDatePicker txtdate;

    @FXML
    private TableView<SellPayment> tbl;

    @FXML
    private TableColumn<?, ?> colorderid;

    @FXML
    private TableColumn<?, ?> colpayid;

    @FXML
    private TableColumn<?, ?> colpaytype;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> coltime;

    @FXML
    private TableColumn<?, ?> colamount;

    @FXML
    void clearset(MouseEvent event) {
        tbl.getItems().clear();
    }

    @FXML
    void clerall(MouseEvent event) {
        tbl.getItems().clear();
    }

    @FXML
    void getbyid(KeyEvent event) {

        tbl.getItems().clear();

        String searchtxt = txtorderid.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select * from buypayment where suplyid like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {

                tbl.getItems().add(new SellPayment(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(7),
                        set.getString(3),
                        set.getString(4),
                        set.getDouble(5),
                        set.getString(6)

                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void search(MouseEvent event) {


        if (txtmonth.getSelectionModel().getSelectedItem() != null) {

            tbl.getItems().clear();

            String searchtxt = txtmonth.getSelectionModel().getSelectedItem();
            String sql = "select * from buypayment where MONTHNAME(date)=?";
            try {
                ResultSet set = CrudUtill.executeQuery(sql, searchtxt);

                while (set.next()) {
                    tbl.getItems().add(new SellPayment(
                            set.getInt(1),
                            set.getString(2),
                            set.getInt(7),
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5),
                            set.getString(6)

                    ));
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        if (txtdate.getValue() != null) {

            tbl.getItems().clear();
            String sql = "select * from buypayment where date=?";
            try {
                ResultSet set = CrudUtill.executeQuery(sql, txtdate.getValue());

                while (set.next()) {
                    tbl.getItems().add(new SellPayment(
                            set.getInt(1),
                            set.getString(2),
                            set.getInt(7),
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5),
                            set.getString(6)

                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }


    }
}
