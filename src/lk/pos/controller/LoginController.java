package lk.pos.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    void login(MouseEvent event) {
        String uname = username.getText();
        String upass = password.getText();


        if (uname.equalsIgnoreCase("admin1") && upass.equals("admin1") || uname.equalsIgnoreCase("admin2") && upass.equals("admin2")) {
            try {
                Parent p = FXMLLoader.load(getClass().getResource("../view/MainView.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(p);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.WARNING, "You Have Entered Wrong User Name Or Password. And Try Again !", ButtonType.OK).show();
        }
    }
}
