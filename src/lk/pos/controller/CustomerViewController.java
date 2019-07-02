package lk.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.CustomerTM;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerViewController implements Initializable {


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initcusid();

        colcusid.setStyle("-fx-alignment:center");
        colfname.setStyle("-fx-alignment:center");
        collname.setStyle("-fx-alignment:center");
        contact1.setStyle("-fx-alignment:center");

        colcusid.setCellValueFactory(new PropertyValueFactory<>("customerid"));
        colfname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        collname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        contact1.setCellValueFactory(new PropertyValueFactory<>("contact"));
    }

    private ObservableList<ArrayList<CustomerTM>> customerearchlist = FXCollections.observableArrayList();

    @FXML
    private TableColumn<CustomerTM, String> colcusid;

    @FXML
    private TableColumn<CustomerTM, String> colfname;

    @FXML
    private TableColumn<CustomerTM, String> collname;

    @FXML
    private TableColumn<CustomerTM, String> contact1;

    @FXML
    private TableView<CustomerTM> searchtable;


    @FXML
    private TextField searchbar;

    @FXML
    private TextField customerid;

    @FXML
    private TextField firstname;

    @FXML
    private TextField contact;

    @FXML
    private TextField lastname;

    @FXML
    private TextField city;

    @FXML
    private TextArea address;

    @FXML
    void clearPayments(MouseEvent event) {

    }

    @FXML
    void deleteCustomer(MouseEvent event) {
        String sql = "DELETE FROM customer WHERE customerid=?";
        try {
            boolean deleted = CrudUtill.executeUpdate(sql, customerid.getText());
            if (deleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer Has been Deleted !", ButtonType.CLOSE).show();
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
    void loadSearch(KeyEvent event) {

        searchtable.getItems().clear();

        String searchtxt = searchbar.getText();
        searchtxt = "'" + searchtxt + "%'";
        System.out.println(searchtxt);
        String sql = "select customerid,firstname,lastname,address,city,contact from customer where customerid like" + searchtxt + " || city like" + searchtxt + " || firstname like" + searchtxt + " || lastname like" + searchtxt + "";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);

            ArrayList<CustomerTM> tempCustomer = new ArrayList<>();

            while (set.next()) {
                searchtable.getItems().add(new CustomerTM(
                        set.getString("customerid"),
                        set.getString("firstname"),
                        set.getString("lastname"),
                        set.getString("contact")
                ));
//                .add(tempCustomer);
            }
            customerearchlist.add(tempCustomer);
            System.out.println(customerearchlist);
//            searchtable.getItems().add(customerearchlist);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void saveCustomer(MouseEvent event) {

        String txtfname = firstname.getText();
        String txtlname = lastname.getText();
        String txtcontact = contact.getText();
        String txtcity = city.getText();
        String txtaddress = address.getText();

        Pattern pfname = Pattern.compile("[a-zA-z]{3,15}");
        Pattern plname = Pattern.compile("[a-zA-z]{3,15}");
        Pattern pcontact = Pattern.compile("[0-9]{10}");
        Pattern pcity = Pattern.compile("[a-zA-z]{3,15}");
        Pattern paddress = Pattern.compile("[a-z0-9/-:A-z ]{3,400}");


        Matcher mfname = pfname.matcher(txtfname);
        Matcher mlname = plname.matcher(txtlname);
        Matcher mcontact = pcontact.matcher(txtcontact);
        Matcher mcity = pcity.matcher(txtcity);
        Matcher maddress = paddress.matcher(txtaddress);

        boolean bmname = mfname.matches();
        boolean blname = mlname.matches();
        boolean bcontact = mcontact.matches();
        boolean bcity = mcity.matches();
        boolean baddress = maddress.matches();

        if (bmname && blname && bcontact && bcity && baddress) {
            String sql = "INSERT INTO customer VALUE(?,?,?,?,?,?)";
            try {
                boolean saved = CrudUtill.executeUpdate(sql, customerid.getText(), firstname.getText(), lastname.getText(), contact.getText(), address.getText(), city.getText());
                if (saved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Has been Saved !", ButtonType.CLOSE).show();
                    clerll();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Fields Missing !", ButtonType.CLOSE).show();
        }
    }

    @FXML
    void updateCustomer(MouseEvent event) {

        String txtfname = firstname.getText();
        String txtlname = lastname.getText();
        String txtcontact = contact.getText();
        String txtcity = city.getText();
        String txtaddress = address.getText();

        Pattern pfname = Pattern.compile("[a-zA-z]{3,15}");
        Pattern plname = Pattern.compile("[a-zA-z]{3,15}");
        Pattern pcontact = Pattern.compile("[0-9]{10}");
        Pattern pcity = Pattern.compile("[a-zA-z]{3,15}");
        Pattern paddress = Pattern.compile("[a-z0-9/-:A-z ]{3,400}");


        Matcher mfname = pfname.matcher(txtfname);
        Matcher mlname = plname.matcher(txtlname);
        Matcher mcontact = pcontact.matcher(txtcontact);
        Matcher mcity = pcity.matcher(txtcity);
        Matcher maddress = paddress.matcher(txtaddress);

        boolean bmname = mfname.matches();
        boolean blname = mlname.matches();
        boolean bcontact = mcontact.matches();
        boolean bcity = mcity.matches();
        boolean baddress = maddress.matches();

        if (bmname && blname && bcontact && bcity && baddress) {
            String sql = "UPDATE customer SET firstname=?, lastname=?, contact=?, city=?, address=? WHERE customerid=?";
            try {
                boolean saved = CrudUtill.executeUpdate(sql, firstname.getText(), lastname.getText(), contact.getText(), city.getText(), address.getText(), customerid.getText());
                if (saved) {
                    new Alert(Alert.AlertType.INFORMATION, "Customer Has been Updated !", ButtonType.CLOSE).show();
                    clerll();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
//            e.printStackTrace();
            }
        } else {

            new Alert(Alert.AlertType.ERROR, "Fields Missing !", ButtonType.CLOSE).show();
        }





    }

    private void initcusid() {

        String customerid = "";

        String sql = "SELECT customerid FROM customer ORDER BY customerid DESC LIMIT 1";
        try {
            ResultSet set = CrudUtill.executeQuery(sql);
            if (set.next()) {
                String tempcusid = set.getString(1);
                int id = Integer.parseInt(tempcusid.substring(1));
                id++;
                customerid = "C" + id;
            } else {
                customerid = "C100";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        this.customerid.setText(customerid);
    }

    private void clerll() {
        initcusid();
        firstname.setText("");
        lastname.setText("");
        contact.setText("");
        city.setText("");
        address.setText("");
    }


    @FXML
    void refresh(MouseEvent event) {
        clerll();
    }


    @FXML
    void getCustomerid(MouseEvent event) {
        String id = searchtable.getSelectionModel().getSelectedItem().getCustomerid();
        try {
            ResultSet set = CrudUtill.executeQuery("select * from customer where customerid=?", id);

            if (set.next()) {
                customerid.setText(set.getString(1));
                firstname.setText(set.getString(2));
                lastname.setText(set.getString(3));
                contact.setText(set.getString(4));
                city.setText(set.getString(6));
                address.setText(set.getString(5));
            } else {
                new Alert(Alert.AlertType.WARNING, "Imposable! This Customer Has Deleted...", ButtonType.CLOSE).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
