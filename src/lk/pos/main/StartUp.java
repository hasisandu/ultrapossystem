package lk.pos.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/lk/pos/view/login.fxml"));
        Scene mainScene= new Scene(root);
        primaryStage.setTitle("Ultra Version 1.0");
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(true);
//        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }
}
