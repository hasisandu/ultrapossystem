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
import lk.pos.TM.BestMvTm;
import lk.pos.modal.ItemDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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


    List<ItemDTO> list = new ArrayList();
    List<BestMvTm> list2 = new ArrayList();

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
            JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "ItemsReportByTable.jrxml");
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
            String locate = GlobalLocationContent.getLocation();
            JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "ItemReportByUniq.jrxml");
            JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
            Map<String, Object> parameters = new LinkedHashMap<>();

            parameters.put("itemid", txtitemid.getText());
            parameters.put("name", txtname.getText());
            parameters.put("badgeid", Integer.parseInt(txtbadge.getText()));
            parameters.put("qtyinshop", Integer.parseInt(txtqtyonshop.getText()));
            parameters.put("qtyinstock", Integer.parseInt(txtqtyonstock.getText()));
            parameters.put("brand", txtbrand.getText());
            parameters.put("price", Double.parseDouble(txtprice.getText()));
            parameters.put("discribe", txtdiscribe.getText());
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
        Date d1 = new Date();
        SimpleDateFormat sd1 = new SimpleDateFormat("YYYY-MM-dd");
        String x1 = sd1.format(d1);
        String sql1 = "select count(qty) as fuck,itemid from orderdetail where date=? group by itemid order by fuck desc";

        Map<String, Integer> m = new HashMap<>();

        try {
            ResultSet resultSet = CrudUtill.executeQuery(sql1, x1);
            while (resultSet.next()) {
                m.put(resultSet.getString("itemid"), resultSet.getInt("fuck"));
            }

            for (Map.Entry xxx : m.entrySet()
                    ) {
                ResultSet f = CrudUtill.executeQuery("select name from stock where itemid=?", xxx.getKey());
                if (f.next()) {
                    list2.add(new BestMvTm(xxx.getKey() + "", f.getString("name"), (int) xxx.getValue()));
                }
            }

            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list2);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("BestMv", jrBeanCollectionDataSource);

            String locate = GlobalLocationContent.getLocation();

            try {
                JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "bstMv.jrxml");
                JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                e.printStackTrace();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void badsell(MouseEvent event) {

    }

    @FXML
    void bestsellitem(MouseEvent event) {

    }

    @FXML
    void lesthan10(MouseEvent event) {

        try {
            ResultSet set = CrudUtill.executeQuery("select a.itemid, a.name, a.badgeid, b.qty, a.qtyonstock, a.brand, a.price, a.describedetail from stock a join shop b on a.itemid=b.id && a.qtyonstock<=10");

            while (set.next()) {
                System.out.println("ok");
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
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ItemReport", jrBeanCollectionDataSource);

        String locate = GlobalLocationContent.getLocation();

        try {
            JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "ItemsReportByTable.jrxml");
            JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        }


    }
}
