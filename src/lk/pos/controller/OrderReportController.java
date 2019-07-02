package lk.pos.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.modal.OrderDTO;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderReportController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMonth();

        colorderid.setStyle("-fx-alignment:center");
        colcusid.setStyle("-fx-alignment:center");
        coldebd.setStyle("-fx-alignment:center");
        coldate.setStyle("-fx-alignment:center");
        coltime.setStyle("-fx-alignment:center");
        colttl.setStyle("-fx-alignment:center");
        coldiscount.setStyle("-fx-alignment:center");
        coldescribe.setStyle("-fx-alignment:center");

        colorderid.setCellValueFactory(new PropertyValueFactory<>("orderid"));
        colcusid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        coldebd.setCellValueFactory(new PropertyValueFactory<>("debd"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colttl.setCellValueFactory(new PropertyValueFactory<>("ttl"));
        coldiscount.setCellValueFactory(new PropertyValueFactory<>("discount"));
        coldescribe.setCellValueFactory(new PropertyValueFactory<>("describe"));

    }

    private void setMonth() {
        txtmonth.getItems().addAll(
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
    private TextField txtordeid;

    @FXML
    private ComboBox<String> txtmonth;

    @FXML
    private JFXDatePicker txtdate;

    @FXML
    private TableView<OrderDTO> tbl;

    @FXML
    private TableColumn<?, ?> colorderid;

    @FXML
    private TableColumn<?, ?> colcusid;

    @FXML
    private TableColumn<?, ?> coldebd;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> coltime;

    @FXML
    private TableColumn<?, ?> colttl;

    @FXML
    private TableColumn<?, ?> coldiscount;

    @FXML
    private TableColumn<?, ?> coldescribe;

    @FXML
    void print(MouseEvent event) {

    }

    @FXML
    void searchbtn(MouseEvent event) {

        if (txtmonth.getSelectionModel().getSelectedItem() != null) {

            tbl.getItems().clear();

            String searchtxt = txtmonth.getSelectionModel().getSelectedItem();
            String sql = "select * from orders where MONTHNAME(date)=?";
            try {
                ResultSet set = CrudUtill.executeQuery(sql, searchtxt);

                while (set.next()) {
                    tbl.getItems().add(new OrderDTO(
                            set.getInt(1),
                            set.getString(2),
                            255,
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5),
                            250,
                            set.getString(6)
                    ));
                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
        if (txtdate.getValue() != null) {
            System.out.println(txtdate.getValue());

            tbl.getItems().clear();
            String sql = "select * from orders where date=?";
            try {
                ResultSet set = CrudUtill.executeQuery(sql, txtdate.getValue());

                while (set.next()) {
                    tbl.getItems().add(new OrderDTO(
                            set.getInt(1),
                            set.getString(2),
                            255,
                            set.getString(3),
                            set.getString(4),
                            set.getDouble(5),
                            250,
                            set.getString(6)
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    void gt(KeyEvent event) {

        tbl.getItems().clear();

        String searchtxt = txtordeid.getText();
        searchtxt = "'" + searchtxt + "%'";
        String sql = "select * from orders where orderid like " + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            while (set.next()) {
                tbl.getItems().add(new OrderDTO(
                        set.getInt(1),
                        set.getString(2),
                        255,
                        set.getString(3),
                        set.getString(4),
                        set.getDouble(5),
                        250,
                        set.getString(6)
                ));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void fuck(MouseEvent event) {
        tbl.getItems().clear();
    }

    @FXML
    void clear(MouseEvent event) {
        tbl.getItems().clear();
    }

}
