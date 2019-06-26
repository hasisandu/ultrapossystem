package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/Default.fxml"));
            mainwindow.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private AnchorPane mainwindow;


    public void PageLoader(String name) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/" + name + ".fxml"));
            mainwindow.getChildren().add(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadCustomerPage(MouseEvent event) {
        PageLoader("ManageCustomerView");
    }

    @FXML
    void loadItemPage(MouseEvent event) {
        PageLoader("ManageItemView");
    }

    @FXML
    void loadPlaceOrderPage(MouseEvent event) {
        PageLoader("PlaceOrderAria");
    }

    @FXML
    void loadExternalImportPage(MouseEvent event) {
        PageLoader("ExternalImportPage");
    }

    @FXML
    void loadSellPaymentPage(MouseEvent event) {
        PageLoader("SellPaymentPage");
    }

    @FXML
    void loadSuplierPage(MouseEvent event) {
        PageLoader("ManageSupliersPage");
    }
}
