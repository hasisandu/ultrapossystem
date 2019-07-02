package lk.pos.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.SuplierTM;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SuplierController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initsupid();


        colcusid.setStyle("-fx-alignment:center");
        colfname.setStyle("-fx-alignment:center");
        collname.setStyle("-fx-alignment:center");
        contact1.setStyle("-fx-alignment:center");

        colcusid.setCellValueFactory(new PropertyValueFactory<>("suplierid"));
        colfname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        collname.setCellValueFactory(new PropertyValueFactory<>("latname"));
        contact1.setCellValueFactory(new PropertyValueFactory<>("contact"));

    }

    private void initsupid() {


        String suplierid = "";

        String sql = "SELECT suplierid FROM suplier ORDER BY suplierid DESC LIMIT 1";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            if (set.next()) {
                String tempcusid = set.getString(1);
                int id = Integer.parseInt(tempcusid.substring(1));
                id++;
                suplierid = "S" + id;
            } else {
                suplierid = "S100";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.sid.setText(suplierid);
    }

    @FXML
    private TextField searchidtxt;

    @FXML
    private TextField sid;

    @FXML
    private TextField fname;

    @FXML
    private TextField contact;

    @FXML
    private TextField lname;

    @FXML
    private TextField city;

    @FXML
    private TextArea addre;

    @FXML
    private TextField comany;

    @FXML
    private TableView<SuplierTM> searchtable;

    @FXML
    private TableColumn<SuplierTM, String> colcusid;

    @FXML
    private TableColumn<SuplierTM, String> colfname;

    @FXML
    private TableColumn<SuplierTM, String> collname;

    @FXML
    private TableColumn<SuplierTM, String> contact1;

    @FXML
    void deletesup(MouseEvent event) {

        String sql = "DELETE FROM suplier WHERE suplierid=?";
        try {
            boolean deleted = CrudUtill.executeUpdate(sql, sid.getText());
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Suplier Has been Deleted !", ButtonType.CLOSE).show();
                clerll();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.WARNING, "You Don't Have Authorized to Delete Data", ButtonType.OK).show();
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.WARNING, "You Don't Have Authorized to Delete Data", ButtonType.OK).show();
            e.printStackTrace();
        }

    }

    private void clerll() {
        initsupid();
        comany.setText("");
        fname.setText("");
        lname.setText("");
        contact.setText("");
        city.setText("");
        addre.setText("");
    }

    @FXML
    void getCustomerid(MouseEvent event) {

        String id = searchtable.getSelectionModel().getSelectedItem().getSuplierid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from suplier where suplierid=?", id);

            if (set.next()) {
                sid.setText(set.getString(1));
                comany.setText(set.getString(7));
                fname.setText(set.getString(2));
                lname.setText(set.getString(3));
                contact.setText(set.getString(5));
                city.setText(set.getString(4));
                addre.setText(set.getString(6));
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Supplier Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void refresh(MouseEvent event) {
        clerll();

    }

    @FXML
    void savesup(MouseEvent event) {

        String sql = "INSERT INTO suplier VALUE(?,?,?,?,?,?,?)";
        try {
            boolean saved = CrudUtill.executeUpdate(sql, sid.getText(), fname.getText(), lname.getText(), city.getText(), contact.getText(), addre.getText(), comany.getText());
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Has been Saved !", ButtonType.CLOSE).show();
                clerll();
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
    void updatesup(MouseEvent event) {

        String sql = "update suplier set firstname=?,lastname=?, address=?,city=?,contact=?,company=? where suplierid=?";
        try {
            boolean saved = CrudUtill.executeUpdate(sql, fname.getText(), lname.getText(), city.getText(), contact.getText(), addre.getText(), comany.getText(), sid.getText());
            if (saved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Has been Updated !", ButtonType.CLOSE).show();
                clerll();
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
    void getserch(KeyEvent event) {

        searchtable.getItems().clear();

        if (searchidtxt.getText().equalsIgnoreCase("")) {

        } else {
            String searchtxt = searchidtxt.getText();
            searchtxt = "'" + searchtxt + "%'";
            String sql = "select suplierid,firstname,lastname,address,city,contact,company from suplier where suplierid like" + searchtxt + " || city like" + searchtxt + " || contact like" + searchtxt + " || firstname like" + searchtxt + " || lastname like" + searchtxt + "";
            try {
                ResultSet set = CrudUtill.executeQuery(sql);

                while (set.next()) {
                    searchtable.getItems().add(new SuplierTM(
                            set.getString("suplierid"),
                            set.getString("firstname"),
                            set.getString("lastname"),
                            set.getString("city"),
                            set.getString("contact"),
                            set.getString("address"),
                            set.getString("company")
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
