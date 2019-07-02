package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.ItemTM;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StockController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        initcusid();
        loadBrandset();

        colid1.setStyle("-fx-alignment:center");
        colproduct1.setStyle("-fx-alignment:center");
        colbrand1.setStyle("-fx-alignment:center");
        colbadge1.setStyle("-fx-alignment:center");
        colqty1.setStyle("-fx-alignment:center");
        colprice1.setStyle("-fx-alignment:center");

        colid1.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colproduct1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbrand1.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colbadge1.setCellValueFactory(new PropertyValueFactory<>("badge"));
        colqty1.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colprice1.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    private void loadBrandset() {

        try {
            ResultSet resultSet = CrudUtill.executeQuery("select * from brand");
            while (resultSet.next()) {
                txtbrand.getItems().add(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private TextField txtsearch;

    @FXML
    private TextField itemidtxt;

    @FXML
    private TextField txtproductname;

    @FXML
    private TextField txtdescribe;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtprice;

    @FXML
    private TextArea txtalldescribe;

    @FXML
    private TextField txtbadgeid;

    @FXML
    private ComboBox<String> txtbrand;

    @FXML
    private TableView<ItemTM> srchtble1;

    @FXML
    private TableColumn<?, ?> colid1;

    @FXML
    private TableColumn<?, ?> colproduct1;

    @FXML
    private TableColumn<?, ?> colbrand1;

    @FXML
    private TableColumn<?, ?> colbadge1;

    @FXML
    private TableColumn<?, ?> colqty1;

    @FXML
    private TableColumn<?, ?> colprice1;

    @FXML
    void deleteItem(MouseEvent event) {

        String sql = "DELETE FROM stock WHERE itemid=?";
        try {
            boolean deleted = CrudUtill.executeUpdate(sql, itemidtxt.getText());
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Product Has been Deleted !", ButtonType.CLOSE).show();
                clerll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
        }

    }

    @FXML
    void getItemsbytable(MouseEvent event) {

        String id = srchtble1.getSelectionModel().getSelectedItem().getItemid();
        System.out.println(id);
        try {
            ResultSet set = CrudUtill.executeQuery("select * from stock where itemid=?", id);

            if (set.next()) {
                itemidtxt.setText(set.getString(1));
                txtbrand.setValue(set.getString(4));
                txtproductname.setText(set.getString(3));
                txtqty.setText(set.getInt(5) + "");
                txtprice.setText(set.getDouble(6) + "");
                txtbadgeid.setText(set.getInt(2) + "");
                txtalldescribe.setText(set.getString(7));
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Product Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void refreshAll(MouseEvent event) {
        clerll();
    }

    @FXML
    void saveItem(MouseEvent event) {
        String sql = "INSERT INTO stock VALUES(?,?,?,?,?,?,?)";
        try {
            boolean saved = CrudUtill.executeUpdate(sql, itemidtxt.getText(), txtbadgeid.getText(), txtproductname.getText(), txtbrand.getSelectionModel().getSelectedItem(), Integer.parseInt(txtqty.getText()), Double.parseDouble(txtprice.getText()), txtalldescribe.getText());
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Product Has been Saved !", ButtonType.CLOSE).show();
                clerll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    @FXML
    void updateItem(MouseEvent event) {

        String sql = "update stock set brand=?, name=?, qtyonstock=?, price=?, badgeid=?, describedetail=? where itemid=?";
        try {
            boolean updated = CrudUtill.executeUpdate(sql, txtbrand.getSelectionModel().getSelectedItem(), txtproductname.getText(), Integer.parseInt(txtqty.getText()), Double.parseDouble(txtprice.getText()), Integer.parseInt(txtbadgeid.getText()), txtalldescribe.getText(), itemidtxt.getText());
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Product Has been Updated !", ButtonType.CLOSE).show();
                clerll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        }

    }

    @FXML
    void released(KeyEvent event) {

        srchtble1.getItems().clear();

        String searchtxt = txtsearch.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select itemid,brand,name,qtyonstock,price,badgeid,describedetail from stock where itemid like" + searchtxt + " || brand like" + searchtxt + " || name like" + searchtxt + " || describedetail like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            while (set.next()) {
                srchtble1.getItems().add(new ItemTM(
                        set.getString("itemid"),
                        set.getString("name"),
                        set.getString("brand"),
                        set.getInt("badgeid"),
                        set.getInt("qtyonstock"),
                        set.getDouble("price")

                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void initcusid() {

        String itemid = "";

        String sql = "SELECT itemid FROM stock ORDER BY itemid DESC LIMIT 1";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            if (set.next()) {
                String tempcusid = set.getString(1);
                int id = Integer.parseInt(tempcusid.substring(1));
                id++;
                itemid = "I" + id;
            } else {
                itemid = "I100";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        String badgeid = "";

        String sql2 = "SELECT badgeid FROM badge ORDER BY badgeid DESC LIMIT 1";
        try {
            ResultSet set = CrudUtill.executeQuery(sql2);
            if (set.next()) {
                int tempbadgeid = set.getInt(1);
                badgeid = "" + tempbadgeid;
            } else {
                badgeid = "1";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        this.itemidtxt.setText(itemid);
        this.txtbadgeid.setText(badgeid);
    }

    private void clerll() {
        initcusid();
        txtbrand.getItems().clear();
        txtproductname.setText("");
        txtqty.setText("");
        txtprice.setText("");
        txtdescribe.setText("");
        txtalldescribe.setText("");
        loadBrandset();
    }

    @FXML
    void oks(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {

            if (txtalldescribe.getText().equalsIgnoreCase("")) {
                String x;
                x = txtdescribe.getText();
                txtalldescribe.setText(x);
                txtdescribe.setText("");
            } else {
                String x = txtalldescribe.getText();
                x = x + " ,\n" + txtdescribe.getText();
                txtalldescribe.setText(x);
                txtdescribe.setText("");
            }

        }

    }

}
