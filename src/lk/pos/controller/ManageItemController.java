package lk.pos.controller;

import com.jfoenix.controls.JFXButton;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageItemController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        initcusid();
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
    private TextField txtbrand;

    @FXML
    void getSerachedData(KeyEvent event) {

        srchtble1.getItems().clear();

        String searchtxt = txtsearchbar.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select a.itemid,a.brand,a.name,a.qtyonstock,a.price,a.badgeid,a.describedetail from stock a where itemid like" + searchtxt + " || brand like" + searchtxt + " || name like" + searchtxt + " || describedetail like" + searchtxt + "";
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

    @FXML
    void refreshAll(MouseEvent event) {
        clerll();
    }

    @FXML
    void saveItems(MouseEvent event) {


        String txtqtys = txtqty.getText();

        Pattern pqty = Pattern.compile("[0-9]{1,4}");

        Matcher mqty = pqty.matcher(txtqtys);

        boolean bqty = mqty.matches();

        if (bqty) {
            String sql = "INSERT INTO shop VALUES(?,?)";
            try {
                boolean saved = CrudUtill.executeUpdate(sql, txtitemid.getText(), Integer.parseInt(txtqty.getText()));
                if (saved) {

                    String sql2 = "update stock set qtyonstock=(qtyonstock-?) where itemid=?";
                    boolean saved2 = CrudUtill.executeUpdate(sql2, Integer.parseInt(txtqty.getText()), txtitemid.getText());
                    if (saved2) {
                        new Alert(Alert.AlertType.INFORMATION, "Product Has been Saved !", ButtonType.CLOSE).show();
                        clerll();
                    }

                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//           update();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Fields Missing !", ButtonType.CLOSE).show();
        }


    }

    @FXML
    void updateitems(MouseEvent event) {


        update();

    }

    public void update() {

        String txtqtys = txtqty.getText();

        Pattern pqty = Pattern.compile("[0-9]{1,4}");

        Matcher mqty = pqty.matcher(txtqtys);

        boolean bqty = mqty.matches();

        if (bqty) {
            String sql = "update shop set qty=(qty+?) where id=?";
            try {
                boolean updated = CrudUtill.executeUpdate(sql, Integer.parseInt(txtqty.getText()), txtitemid.getText());
                if (updated) {

                    String sql2 = "update stock set qtyonstock=(qtyonstock-?) where itemid=?";
                    boolean saved2 = CrudUtill.executeUpdate(sql2, Integer.parseInt(txtqty.getText()), txtitemid.getText());
                    if (saved2) {
                        new Alert(Alert.AlertType.INFORMATION, "Product Has been Updated !", ButtonType.CLOSE).show();
                        clerll();
                    }


                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Fields Missing !", ButtonType.CLOSE).show();
        }


    }

//    private void initcusid() {
//
//        String itemid = "";
//
//        String sql = "SELECT itemid FROM item ORDER BY itemid DESC LIMIT 1";
//        try {
//            ResultSet set = CrudUtill.executeQuery(sql);
//            if (set.next()) {
//                String tempcusid = set.getString(1);
//                int id = Integer.parseInt(tempcusid.substring(1));
//                id++;
//                itemid = "I" + id;
//            } else {
//                itemid = "I100";
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        String badgeid = "";
//
//        String sql2 = "SELECT badgeid FROM badge ORDER BY badgeid DESC LIMIT 1";
//        try {
//            ResultSet set = CrudUtill.executeQuery(sql2);
//            if (set.next()) {
//                int tempbadgeid = set.getInt(1);
//                badgeid = "" + tempbadgeid;
//            } else {
//                badgeid = "1";
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        this.txtitemid.setText(itemid);
//        this.txtbadgeid.setText(badgeid);
//    }

    private void clerll() {
//        initcusid();
        txtbrand.setText("");
        txtproductname.setText("");
        txtqty.setText("");
        txtprice.setText("");
        drefreshcription.setText("");
        txtdrefreshc.setText("");
    }

    private void loadBrandset() {

//        try {
//            ResultSet resultSet = CrudUtill.executeQuery("select * from brand");
//            while (resultSet.next()) {
//                txtbrand.getItems().add(resultSet.getString(2));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    int qtyonstocksvar = 0;

    @FXML
    void getItemsbytable(MouseEvent event) {
        String id = srchtble1.getSelectionModel().getSelectedItem().getItemid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from stock where itemid=?", id);
            ResultSet set2 = CrudUtill.executeQuery("select * from shop where id=?", id);

            if (set.next()) {
                txtitemid.setText(set.getString(1));
                txtbrand.setText(set.getString(4));
                txtproductname.setText(set.getString(3));
                qtyonstocksvar = set.getInt(5);
                txtprice.setText(set.getDouble(6) + "");
                txtbadgeid.setText(set.getInt(2) + "");
                drefreshcription.setText(set.getString(7));
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Product Has Deleted...", ButtonType.CLOSE).show();
            }

            if (set2.next()) {
                txtqty.setText(set2.getInt(2) + "");
                a.setDisable(true);
            } else {
//                new Alert(Alert.AlertType.WARNING, "Imposable! This Product Has Deleted...", ButtonType.CLOSE).show();
                txtqty.setText("0");
                a.setDisable(false);
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

    @FXML
    void check(KeyEvent event) {
        int temp = 0;
        if (txtqty.getText().equalsIgnoreCase("") || txtqty.getText().equalsIgnoreCase(" ")) {

        } else {
            temp = Integer.parseInt(txtqty.getText());
        }
        if (qtyonstocksvar < temp) {
            new Alert(Alert.AlertType.WARNING, "Imposable!  There Are No Sufficient QTY", ButtonType.CLOSE).show();
            a.setDisable(true);
            s.setDisable(true);
        } else {
            a.setDisable(false);
            s.setDisable(false);
        }
    }


    @FXML
    private JFXButton a;

    @FXML
    private JFXButton s;

}
