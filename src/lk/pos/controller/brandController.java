package lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.BrandTM;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class brandController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        brandid.setStyle("-fx-alignment:center");
        brandname.setStyle("-fx-alignment:center");

        brandid.setCellValueFactory(new PropertyValueFactory<>("id"));
        brandname.setCellValueFactory(new PropertyValueFactory<>("name"));

        update.setDisable(true);
        delete.setDisable(true);


    }

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton delete;

    @FXML
    void get(MouseEvent event) {


        int id = tbl.getSelectionModel().getSelectedItem().getId();
        ;
        try {
            ResultSet set = CrudUtill.executeQuery("select * from brand where brandid=?", id);

            if (set.next()) {

                update.setDisable(false);
                delete.setDisable(false);

                brandidttxt.setText(set.getString(1));
                txtbrandname.setText(set.getString(2));
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Product Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    private TextField searchtxtbar;

    @FXML
    private TableView<BrandTM> tbl;

    @FXML
    private TableColumn<?, ?> brandid;

    @FXML
    private TableColumn<?, ?> brandname;

    @FXML
    private TextField txtbrandname;

    @FXML
    void save(MouseEvent event) {
        if (txtbrandname.getText().equalsIgnoreCase("")) {
            new Alert(Alert.AlertType.WARNING, "Enter A Name !", ButtonType.CLOSE).show();
        } else {
            String sql = "INSERT INTO brand (brandname) VALUES(?)";
            try {
                boolean saved = CrudUtill.executeUpdate(sql, txtbrandname.getText());
                if (saved) {
                    new Alert(Alert.AlertType.INFORMATION, "Product Has been Saved !", ButtonType.CLOSE).show();

                    txtbrandname.setText("");
                    brandidttxt.setText("");
                    searchtxtbar.setText("");
                    tbl.getItems().clear();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                e.printStackTrace();
            }
        }
    }

    @FXML
    void searchbyid(KeyEvent event) {


        tbl.getItems().clear();

        if (searchtxtbar.getText().equalsIgnoreCase("")) {

        } else {
            String searchtxt = searchtxtbar.getText();
            searchtxt = "'" + searchtxt + "%'";
            String sql = "select brandid,brandname from brand where brandname like" + searchtxt + "";
            try {
                ResultSet set = CrudUtill.executeQuery(sql);

                while (set.next()) {
                    tbl.getItems().add(new BrandTM(
                            set.getInt("brandid"),
                            set.getString("brandname")
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
    void update(MouseEvent event) {
        String sql = "update brand set brandname=? where brandid=?";
        try {
            boolean updated = CrudUtill.executeUpdate(sql, txtbrandname.getText(), Integer.parseInt(brandidttxt.getText()));
            if (updated) {
                new Alert(Alert.AlertType.INFORMATION, "Product Has been Updated !", ButtonType.CLOSE).show();
                txtbrandname.setText("");
                brandidttxt.setText("");
                searchtxtbar.setText("");
                tbl.getItems().clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
            e.printStackTrace();
        }
    }

    @FXML
    private TextField brandidttxt;

    @FXML
    void delete(MouseEvent event) {
        String sql = "DELETE FROM brand WHERE brandid=?";
        try {
            boolean deleted = CrudUtill.executeUpdate(sql, Integer.parseInt(brandidttxt.getText()));
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Brand Has been Deleted !", ButtonType.CLOSE).show();
                txtbrandname.setText("");
                brandidttxt.setText("");
                searchtxtbar.setText("");
                tbl.getItems().clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
        }
    }

}
