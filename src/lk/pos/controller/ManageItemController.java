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

public class ManageItemController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initcusid();
        loadBrandset();
        colid1.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colproduct1.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbrand1.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colbadge1.setCellValueFactory(new PropertyValueFactory<>("badge"));
        colqty1.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colprice1.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    @FXML
    private TextField txtsearchbar;

    @FXML
    private TextField txtitemid;

    @FXML
    private TextField txtproductname;

    @FXML
    private TextField txtdrefreshc;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtprice;

    @FXML
    private TextArea drefreshcription;

    @FXML
    private TableView<?> table2;

    @FXML
    private TableColumn<?, ?> colitemid2;

    @FXML
    private TableColumn<?, ?> colquantitysop;

    @FXML
    private TableColumn<?, ?> colqtystock;

    @FXML
    private TableColumn<?, ?> colbrand2;

    @FXML
    private TableColumn<?, ?> colprice2;


    @FXML
    private TextField txtbadgeid;

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
    private ComboBox<String> txtbrand;

    @FXML
    void getSerachedData(KeyEvent event) {

        srchtble1.getItems().clear();

        String searchtxt = txtsearchbar.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select itemid,brand,name,qtyonshop,price,badgeid,description from item where itemid like" + searchtxt + " || brand like" + searchtxt + " || name like" + searchtxt + " || description like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            while (set.next()) {
                srchtble1.getItems().add(new ItemTM(
                        set.getString("itemid"),
                        set.getString("name"),
                        set.getString("brand"),
                        set.getInt("badgeid"),
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
    void refreshAll(MouseEvent event) {
        clerll();
    }

    @FXML
    void saveItems(MouseEvent event) {
        String sql = "INSERT INTO item VALUES(?,?,?,?,?,?,?)";
        try {
            boolean saved = CrudUtill.executeUpdate(sql, txtitemid.getText(), txtbrand.getSelectionModel().getSelectedItem(), txtproductname.getText(), Integer.parseInt(txtqty.getText()), Double.parseDouble(txtprice.getText()), Integer.parseInt(txtbadgeid.getText()), drefreshcription.getText());
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
    void updateitems(MouseEvent event) {

        String sql = "update item set brand=?, name=?, qtyonshop=?, price=?, badgeid=?, description=? where itemid=?";
        try {
            boolean updated = CrudUtill.executeUpdate(sql, txtbrand.getSelectionModel().getSelectedItem(), txtproductname.getText(), Integer.parseInt(txtqty.getText()), Double.parseDouble(txtprice.getText()), Integer.parseInt(txtbadgeid.getText()), drefreshcription.getText(), txtitemid.getText());
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

    private void initcusid() {

        String itemid = "";

        String sql = "SELECT itemid FROM item ORDER BY itemid DESC LIMIT 1";
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


        this.txtitemid.setText(itemid);
        this.txtbadgeid.setText(badgeid);
    }

    private void clerll() {
        initcusid();
        txtbrand.getItems().clear();
        txtproductname.setText("");
        txtqty.setText("");
        txtprice.setText("");
        drefreshcription.setText("");
        txtdrefreshc.setText("");
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
    void getItemsbytable(MouseEvent event) {
        String id = srchtble1.getSelectionModel().getSelectedItem().getItemid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from item where itemid=?", id);

            if (set.next()) {
                txtitemid.setText(set.getString(1));
                txtbrand.getItems().add(set.getString(2));
                txtproductname.setText(set.getString(3));
                txtqty.setText(set.getInt(4) + "");
                txtprice.setText(set.getDouble(5) + "");
                txtbadgeid.setText(set.getInt(6) + "");
                drefreshcription.setText(set.getString(7));
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
    void okset(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (drefreshcription.getText().equalsIgnoreCase("")) {
                String x;
                x = txtdrefreshc.getText();
                drefreshcription.setText(x);
                txtdrefreshc.setText("");
            } else {
                String x = drefreshcription.getText();
                x = x + " ,\n" + txtdrefreshc.getText();
                drefreshcription.setText(x);
                txtdrefreshc.setText("");
            }

        }
    }

}
