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
import lk.pos.modal.ItemDTO;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ItemReportPageController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        colitemid.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colitemname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colbadgeid.setCellValueFactory(new PropertyValueFactory<>("badgeid"));
        colqtyshop.setCellValueFactory(new PropertyValueFactory<>("qtyinshop"));
        colqtystock.setCellValueFactory(new PropertyValueFactory<>("qtyinstock"));
        colbrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        coldescribe.setCellValueFactory(new PropertyValueFactory<>("discribe"));

    }


    @FXML
    private TextField txtitem;

    @FXML
    private TableView<ItemDTO> tbl;

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
        String sql = "select I.itemid,I.brand,I.name,I.qtyonshop,S.qtyonstock,I.price,I.badgeid,I.description from item i join stock s on i.itemid=s.itemid && i.itemid like" + searchtxt + " || i.brand like" + searchtxt + " || i.name like" + searchtxt + " || i.description like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            while (set.next()) {

                System.out.println(set.getInt("qtyonstock"));
                System.out.println(set.getString("description"));

                tbl.getItems().add(new ItemDTO(
                        set.getString("itemid"),
                        set.getString("name"),
                        set.getInt("badgeid"),
                        set.getInt("qtyonshop"),
                        set.getInt("qtyonstock"),
                        set.getString("brand"),
                        set.getDouble("price"),
                        set.getInt("description")
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
