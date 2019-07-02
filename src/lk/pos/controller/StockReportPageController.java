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
import lk.pos.modal.StockDTO;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class StockReportPageController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colitemid.setStyle("-fx-alignment:center");
        colitemname.setStyle("-fx-alignment:center");
        colbadgeid.setStyle("-fx-alignment:center");
        colqtyshop.setStyle("-fx-alignment:center");
        colqtystock.setStyle("-fx-alignment:center");
        colbrand.setStyle("-fx-alignment:center");
        colprice.setStyle("-fx-alignment:center");
        coldescribe.setStyle("-fx-alignment:center");

        colitemid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colitemname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbadgeid.setCellValueFactory(new PropertyValueFactory<>("badgeid"));
        colqtyshop.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colqtystock.setCellValueFactory(new PropertyValueFactory<>("stockqty"));
        colbrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        coldescribe.setCellValueFactory(new PropertyValueFactory<>("describe"));

    }


    @FXML
    private TextField txtitem;

    @FXML
    private TableView<StockDTO> tbl;

    @FXML
    private TableColumn<?, ?> colitemid;

    @FXML
    private TableColumn<?, ?> colitemname;

    @FXML
    private TableColumn<?, ?> colbadgeid;

    @FXML
    private TableColumn<?, ?> colqtyshop;

    @FXML
    private TableColumn<?, ?> colqtystock;

    @FXML
    private TableColumn<?, ?> colbrand;

    @FXML
    private TableColumn<?, ?> colprice;

    @FXML
    private TableColumn<?, ?> coldescribe;

    @FXML
    private JFXTextField txtitemid;

    @FXML
    private JFXTextField txtname;

    @FXML
    private JFXTextField txtbadge;

    @FXML
    private JFXTextField txtbrand;

    @FXML
    private JFXTextField txtqtyonshop;

    @FXML
    private JFXTextField txtqtyonstock;

    @FXML
    private JFXTextField txtprice;

    @FXML
    private JFXTextArea txtdiscribe;

    @FXML
    void badsell(MouseEvent event) {

    }

    @FXML
    void bestsellitem(MouseEvent event) {

    }

    @FXML
    void getuniq(MouseEvent event) {


        String id = tbl.getSelectionModel().getSelectedItem().getItemid();
        try {
            ResultSet set = CrudUtill.executeQuery("select I.itemid,I.brand,I.name,I.qtyonshop,S.qtyonstock,I.price,I.badgeid,I.description from item i join stock s on s.itemid=i.itemid && i.itemid=?", id);

            if (set.next()) {
                txtitemid.setText(set.getString("itemid"));
                txtbrand.setText(set.getString("brand"));
                txtname.setText(set.getString("name"));
                txtqtyonshop.setText(set.getString("qtyonshop"));
                txtqtyonstock.setText(set.getString("qtyonstock"));
                txtprice.setText(set.getString("price"));
                txtbadge.setText(set.getString("badgeid"));
                txtdiscribe.setText(set.getString("description"));
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
    void lesthan10(MouseEvent event) {

    }

    @FXML
    void loadsearch(KeyEvent event) {

        tbl.getItems().clear();

        String searchtxt = txtitem.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select a.itemid,a.brand,a.name,a.qtyonshop,b.qty,b.id,a.price,a.badgeid,a.description from stock a join shop b on b.id=a.itemid && a.itemid like" + searchtxt + " || a.brand like" + searchtxt + " || a.name like" + searchtxt + " || a.description like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {

                System.out.println(set.getInt("qtyonstock"));
                System.out.println(set.getString("description"));

                tbl.getItems().add(new StockDTO(
                        set.getString("itemid"),
                        set.getInt("badgeid"),
                        set.getString("name"),
                        set.getString("brand"),
                        set.getInt("qty"),
                        set.getDouble("price"),
                        set.getString("description"),
                        set.getInt("qtyonstock")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void printAll(MouseEvent event) {

    }

    @FXML
    void printuniq(MouseEvent event) {

    }

    @FXML
    void topmw(MouseEvent event) {

    }
}
