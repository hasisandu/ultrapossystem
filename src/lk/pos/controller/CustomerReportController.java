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
import lk.pos.modal.CustomerDTO;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerReportController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colcusid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colfname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        collname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));

    }

    @FXML
    private TextField txtsearchname;

    @FXML
    private TextField asdadsadsdsd;

    @FXML
    private TableView<CustomerDTO> resulttbl;

    @FXML
    private TableColumn<?, ?> colcusid;

    @FXML
    private TableColumn<?, ?> colfname;

    @FXML
    private TableColumn<?, ?> collname;

    @FXML
    private TableColumn<?, ?> colcontact;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private JFXTextField txtcusid;

    @FXML
    private JFXTextField txtfname;

    @FXML
    private JFXTextField txtcontact;

    @FXML
    private JFXTextField txtlname;

    @FXML
    private JFXTextField txtcity;

    @FXML
    private JFXTextArea txtaddress;

    @FXML
    void Prinddetailwice(MouseEvent event) {

        clearAll();

    }

    private void clearAll() {
        txtcusid.setText("");
        txtfname.setText("");
        txtlname.setText("");
        txtcontact.setText("");
        txtcity.setText("");
        txtaddress.setText("");
    }

    @FXML
    void getuniq(MouseEvent event) {

        String id = resulttbl.getSelectionModel().getSelectedItem().getCustomerid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from customer where customerid=?", id);

            if (set.next()) {
                txtcusid.setText(set.getString(1));
                txtfname.setText(set.getString(2));
                txtlname.setText(set.getString(3));
                txtcontact.setText(set.getString(4));
                txtcity.setText(set.getString(6));
                txtaddress.setText(set.getString(5));
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
    void loadAlldebtors(MouseEvent event) {

    }

    @FXML
    void loadcity(KeyEvent event) {
        resulttbl.getItems().clear();

        String searchtxt = asdadsadsdsd.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select customerid,firstname,lastname,address,city,contact from customer where city like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {
                resulttbl.getItems().add(new CustomerDTO(
                        set.getString("customerid"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("contact"),
                        set.getString("address"),
                        set.getString("city")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadset(KeyEvent event) {
        resulttbl.getItems().clear();

        String searchtxt = txtsearchname.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select customerid,firstname,lastname,address,city,contact from customer where customerid like" + searchtxt + " || city like" + searchtxt + " || firstname like" + searchtxt + " || lastname like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {
                resulttbl.getItems().add(new CustomerDTO(
                        set.getString("customerid"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("contact"),
                        set.getString("address"),
                        set.getString("city")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void printbytable(MouseEvent event) {

    }

}
