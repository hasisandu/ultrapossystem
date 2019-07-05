package lk.pos.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.External;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExternalReportController implements Initializable {

    List<External> list = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setMonth();
        coldate.setStyle("-fx-alignment:center");
        coltime.setStyle("-fx-alignment:center");
        colreason.setStyle("-fx-alignment:center");
        colprice.setStyle("-fx-alignment:center");

        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colreason.setCellValueFactory(new PropertyValueFactory<>("reason"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    private void setMonth() {
        month.getItems().addAll(
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
    private ComboBox<String> month;

    @FXML
    private JFXDatePicker date;

    @FXML
    private TableView<External> tbl;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> coltime;

    @FXML
    private TableColumn<?, ?> colreason;

    @FXML
    private TableColumn<?, ?> colprice;

    @FXML
    void clickdate(MouseEvent event) {
        tbl.getItems().clear();
    }

    @FXML
    void clickmonth(MouseEvent event) {
        tbl.getItems().clear();
    }

    @FXML
    void print(MouseEvent event) {

        try {

            JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
            Map<String, Object> parameters = new HashMap<>();
            System.out.println(list.size());
            String locate = GlobalLocationContent.getLocation();
            JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "externalTable.jrxml");
            JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
            parameters.put("ItemDataSource", jrBeanCollectionDataSource);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.viewReport(jasperPrint, false);

        } catch (JRException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void searchbtn(MouseEvent event) {

        if (month.getSelectionModel().getSelectedItem() != null) {

            tbl.getItems().clear();

            String searchtxt = month.getSelectionModel().getSelectedItem();
            String sql = "select * from otherbuy where MONTHNAME(date)=?";
            try {
                ResultSet set = CrudUtill.executeQuery(sql, searchtxt);

                list.clear();

                while (set.next()) {

                    list.add(new External(
                            set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5)

                    ));

                    tbl.getItems().add(new External(
                            set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5)

                    ));
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        if (date.getValue() != null) {

            tbl.getItems().clear();
            String sql = "select * from otherbuy where date=?";
            try {
                ResultSet set = CrudUtill.executeQuery(sql, date.getValue());


                list.clear();

                while (set.next()) {

                    list.add(new External(
                            set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5)

                    ));


                    tbl.getItems().add(new External(
                            set.getInt(1),
                            set.getString(2),
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5)

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
