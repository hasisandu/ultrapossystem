package lk.pos.controller;

import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OrderReportController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private TextField txtordeid;

    @FXML
    private ComboBox<?> txtmonth;

    @FXML
    private JFXDatePicker txtdate;

    @FXML
    private TableView<?> tbl;

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

    }

}
