package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportPageControl implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadPageMethod("DefaultReport");

    }

    @FXML
    private AnchorPane display;

    @FXML
    void loadBuyReportPage(MouseEvent event) {
        loadPageMethod("BuyPaymentReport");
    }

    @FXML
    void loadCustomerReportPage(MouseEvent event) {
        loadPageMethod("CustomereportPage");
    }

    @FXML
    void loadExternalReportPage(MouseEvent event) {
        loadPageMethod("ExternalImportReport");
    }

    @FXML
    void loadImageReportPage(MouseEvent event) {
        loadPageMethod("ItemReportPage");
    }

    @FXML
    void loadOrdersReportPage(MouseEvent event) {
        loadPageMethod("OrderReportPage");
    }

    @FXML
    void loadSellingReportPage(MouseEvent event) {
        loadPageMethod("sellPaymentReport");
    }

    @FXML
    void loadStockReportPage(MouseEvent event) {
        loadPageMethod("StockReportPage");
    }

    @FXML
    void loadSupliersReportPage(MouseEvent event) {
        loadPageMethod("SupplierReport");
    }

    public void loadPageMethod(String name) {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("../view/" + name + ".fxml"));
            display.getChildren().addAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
