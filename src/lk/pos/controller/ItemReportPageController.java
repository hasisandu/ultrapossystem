package lk.pos.controller;

import com.jfoenix.controls.JFXButton;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ItemReportPageController implements Initializable {
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
        colqtyshop.setCellValueFactory(new PropertyValueFactory<>("qtyinshop"));
        colqtystock.setCellValueFactory(new PropertyValueFactory<>("qtyinstock"));
        colbrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        coldescribe.setCellValueFactory(new PropertyValueFactory<>("discribe"));

        bt.setDisable(true);

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
            ResultSet set = CrudUtill.executeQuery("select I.itemid,I.brand,I.name,I.qtyonstock,S.qty,I.price,S.id,I.badgeid,I.describedetail from stock i join shop s on s.id=i.itemid && i.itemid=?", id);

            if (set.next()) {
                txtitemid.setText(set.getString("itemid"));
                txtbrand.setText(set.getString("brand"));
                txtname.setText(set.getString("name"));
                txtqtyonshop.setText(set.getString("qty"));
                txtqtyonstock.setText(set.getString("qtyonstock"));
                txtprice.setText(set.getString("price"));
                txtbadge.setText(set.getString("badgeid"));
                txtdiscribe.setText(set.getString("describedetail"));
                bt.setDisable(false);
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

    List<ItemDTO> list = new ArrayList();

    @FXML
    void loadsearch(KeyEvent event) {

        tbl.getItems().clear();

        String searchtxt = txtitem.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select a.itemid,a.brand,a.name,a.qtyonstock,b.qty,b.id,a.price,a.badgeid,a.describedetail from stock a join shop b on b.id=a.itemid && a.itemid like" + searchtxt + " || a.brand like" + searchtxt + " || a.name like" + searchtxt + " || a.describedetail like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            list.clear();
            while (set.next()) {


                list.add(new ItemDTO(
                        set.getString("itemid"),
                        set.getString("name"),
                        set.getInt("badgeid"),
                        set.getInt("qty"),
                        set.getInt("qtyonstock"),
                        set.getString("brand"),
                        set.getDouble("price"),
                        set.getString("describedetail")
                ));

                tbl.getItems().add(new ItemDTO(
                        set.getString("itemid"),
                        set.getString("name"),
                        set.getInt("badgeid"),
                        set.getInt("qty"),
                        set.getInt("qtyonstock"),
                        set.getString("brand"),
                        set.getDouble("price"),
                        set.getString("describedetail")
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

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ItemReport", jrBeanCollectionDataSource);

        String locate = GlobalLocationContent.getLocation();

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "StockReportbyTable.jrxml");
            JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private JFXButton bt;

    @FXML
    void printuniq(MouseEvent event) {

        list.clear();


        try {

            list.add(new ItemDTO(
                    txtitemid.getText(),
                    txtname.getText(),
                    Integer.parseInt(txtbadge.getText()),
                    Integer.parseInt(txtqtyonshop.getText()),
                    Integer.parseInt(txtqtyonstock.getText()),
                    txtbrand.getText(),
                    Double.parseDouble(txtprice.getText()),
                    txtdiscribe.getText()
            ));


            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
            Map<String, Object> parameters = new HashMap<>();


            String locate = GlobalLocationContent.getLocation();
            JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "ItemByuniq.jrxml");
            JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
            parameters.put("ItemReport", jrBeanCollectionDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }

        bt.setDisable(true);

    }

    @FXML
    void topmw(MouseEvent event) {

    }
}
