package lk.pos.controller;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.SuplierTM;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SupplierReportController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colsupid.setStyle("-fx-alignment:center");
        colfname.setStyle("-fx-alignment:center");
        collname.setStyle("-fx-alignment:center");
        colcontact.setStyle("-fx-alignment:center");
        coladdress.setStyle("-fx-alignment:center");

        colsupid.setCellValueFactory(new PropertyValueFactory<>("suplierid"));
        colfname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        collname.setCellValueFactory(new PropertyValueFactory<>("latname"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));


    }

    @FXML
    private TextField suplytxt;

    @FXML
    private TextField city;

    @FXML
    private TableView<SuplierTM> tbl;

    @FXML
    private TableColumn<?, ?> colsupid;

    @FXML
    private TableColumn<?, ?> colfname;

    @FXML
    private TableColumn<?, ?> collname;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private JFXTextField txtsuply;

    @FXML
    private JFXTextField txtfname;

    @FXML
    private JFXTextField txtlname;

    @FXML
    private JFXTextField t;

    @FXML
    private JFXTextField txtcity;

    @FXML
    private JFXTextArea txtaddress;

    @FXML
    void getuniq(MouseEvent event) {


        String id = tbl.getSelectionModel().getSelectedItem().getSuplierid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from suplier where suplierid=?", id);

            if (set.next()) {
                txtsuply.setText(set.getString(1));
                txtfname.setText(set.getString(2));
                txtlname.setText(set.getString(3));
                t.setText(set.getString(5));
                txtcity.setText(set.getString(4));
                txtaddress.setText(set.getString(6));
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
    void pribt(MouseEvent event) {
        clearAll();
    }

    @FXML
    void printuniq(MouseEvent event) {
        clearAll();
    }

    private void clearAll() {
        txtsuply.setText("");
        txtfname.setText("");
        txtlname.setText("");
        t.setText("");
        txtcity.setText("");
        txtaddress.setText("");
    }


    @FXML
    void get(KeyEvent event) {
        tbl.getItems().clear();

        String searchtxt = suplytxt.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select suplierid,firstname,lastname,address,city,contact,company from suplier where suplierid like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {
                tbl.getItems().add(new SuplierTM(
                        set.getString("suplierid"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("city"),
                        set.getString("contact"),
                        set.getString("address"),
                        set.getString("company")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void getscity(KeyEvent event) {
        tbl.getItems().clear();

        String searchtxt = city.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select suplierid,firstname,lastname,address,city,contact,company from suplier where city like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {
                tbl.getItems().add(new SuplierTM(
                        set.getString("suplierid"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("city"),
                        set.getString("contact"),
                        set.getString("address"),
                        set.getString("company")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
