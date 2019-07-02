package lk.pos.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.External;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class ExternalImportController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadTable();

        coldate.setStyle("-fx-alignment:center");
        coltime.setStyle("-fx-alignment:center");
        colcost.setStyle("-fx-alignment:center");

        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colcost.setCellValueFactory(new PropertyValueFactory<>("price"));
    }


    @FXML
    private TextArea description;

    @FXML
    private TableView<External> table;

    @FXML
    private TableColumn<External, String> coldate;

    @FXML
    private TableColumn<External, String> coltime;

    @FXML
    private TableColumn<External, Double> colcost;

    @FXML
    private TextField cost;

    @FXML
    private JFXDatePicker dates;

    @FXML
    private TextField idtxt;

    @FXML
    void deleteOrder(MouseEvent event) {
        String sql = "DELETE FROM otherbuy WHERE id=?";
        try {
            boolean deleted = CrudUtill.executeUpdate(sql, idtxt.getText());
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Data Has been Deleted !", ButtonType.CLOSE).show();
                idtxt.setText("");
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
    void saveimports(MouseEvent event) {

        String sql = "INSERT INTO otherbuy(reason,date,time,price) VALUES('" + description.getText() + "',now(),now()," + Double.parseDouble(cost.getText()) + ")";
        try {
            boolean saved = CrudUtill.executeUpdate(sql);
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Has been Saved !", ButtonType.CLOSE).show();
                clearAll();
                loadTable();
            }
        } catch (SQLException e) {
            loadTable();
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            loadTable();
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        }

    }

    private void clearAll() {
        cost.setText("");
        description.setText("");
    }


    @FXML
    void updateImports(KeyEvent event) {

    }

    private void loadTable() {
        table.getItems().clear();

        Date d = new Date();
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd");
        String x = sd.format(d);

        try {

            ResultSet resultSet = CrudUtill.executeQuery("select * from otherbuy where date=?", x);
            while (resultSet.next()) {
                table.getItems().add(new External(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadTablebydate() {
        table.getItems().clear();

        try {

            ResultSet resultSet = CrudUtill.executeQuery("select * from otherbuy where date=?", dates.getValue().toString());
            while (resultSet.next()) {
                table.getItems().add(new External(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getDouble(5)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadset(MouseEvent event) {
        loadTablebydate();
    }


    @FXML
    void getID(MouseEvent event) {

        int id = table.getSelectionModel().getSelectedItem().getId();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from otherbuy where id=?", id);

            if (set.next()) {
                idtxt.setText(set.getInt(1) + "");
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Data Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
