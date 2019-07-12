package lk.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.pos.DBUtility.CrudUtill;
import lk.pos.TM.OrderTM;
import lk.pos.TM.PaymentTM;
import lk.pos.db.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellingPriceController implements Initializable {


    @FXML
    private ImageView x;
    int i;
    private PlaceOrderController placeOrderController = null;

    Map<String, OrderTM> or;
    List<OrderTM> fuckorder = new ArrayList<>();

    public void setControll(PlaceOrderController controll) {


        search.setDisable(true);
        x.setDisable(true);
        searchtxtfff.setDisable(true);
        ordertable.setDisable(true);


        placeOrderController = controll;


        for (Map.Entry<String, OrderTM> entry : controll.map.entrySet()
                ) {
            System.out.println(entry.getValue().toString());
            fuckorder.add(entry.getValue());
        }

        txtrest.setText(placeOrderController.totalcost + "");

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            ResultSet resultSet = CrudUtill.executeQuery("select orderid from orders order by orderid desc limit 1");
            if (resultSet.next()) {
                i = resultSet.getInt(1);
                txtorderid.setText(++i + "");
            } else {
                txtorderid.setText(1 + "");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        colpaymentid.setStyle("-fx-alignment:center");
        coldate.setStyle("-fx-alignment:center");
        coltime.setStyle("-fx-alignment:center");
        colpaymenttype.setStyle("-fx-alignment:center");
        ss.setStyle("-fx-alignment:center");


        colpaymentid.setCellValueFactory(new PropertyValueFactory<>("paymentid"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        coltime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colpaymenttype.setCellValueFactory(new PropertyValueFactory<>("paytype"));
        ss.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }

    @FXML
    private TableView<PaymentTM> ordertable;

    @FXML
    private TableColumn<PaymentTM, Integer> colpaymentid;

    @FXML
    private TableColumn<PaymentTM, String> coldate;

    @FXML
    private TableColumn<PaymentTM, String> coltime;

    @FXML
    private TableColumn<PaymentTM, String> colpaymenttype;

    @FXML
    private TableColumn<PaymentTM, String> ss;

    @FXML
    private ToggleGroup a1;

    @FXML
    private TextField txtorderid;

    @FXML
    private TextField searchtxtfff;

    @FXML
    private TextField txtrest;

    @FXML
    private TextField txtamount;

    @FXML
    private TextField txtbalance;

    @FXML
    private TextField search;

    @FXML
    private RadioButton a;

    @FXML
    private RadioButton b;

    private Connection connection;


    @FXML
    void Save(MouseEvent event) {


        String sorderid = txtorderid.getText();
        String srest = txtrest.getText();
        String samount = txtamount.getText();
        String sbalance = txtbalance.getText();

        Pattern porderid = Pattern.compile("[0-9]");
        Pattern prest = Pattern.compile("[0-9]+([.][0-9]{1,2})?");
        Pattern pamount = Pattern.compile("[0-9]+([.][0-9]{1,2})?");
        Pattern pbalance = Pattern.compile("[0-9]+([.][0-9]{1,2})?");

        Matcher morderid = porderid.matcher(sorderid);
        Matcher mrest = prest.matcher(srest);
        Matcher mamount = pamount.matcher(samount);
        Matcher mbalance = pbalance.matcher(sbalance);

        boolean borderid = morderid.matches();
        boolean brest = mrest.matches();
        boolean bamount = mamount.matches();
        boolean bbalance = mbalance.matches();

        if (borderid && brest && bamount && bbalance) {
            Date d1 = new Date();
            SimpleDateFormat sd1 = new SimpleDateFormat("YYYY-MM-dd");
            String x1 = sd1.format(d1);

            Date d2 = new Date();
            SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
            String x2 = sd2.format(d2);

            String type = "Cash";
            if (b.isSelected()) {
                type = "Check";
            }


            if (placeOrderController != null) {

                try {

                    connection = DBConnection.getInstanse().getConnection();
                    connection.setAutoCommit(false);

                    ArrayList<OrderTM> list = new ArrayList();

                    PreparedStatement preparedStatement = connection.prepareStatement("insert into orders values(?,?,?,?,?,?,?)");
                    preparedStatement.setObject(1, Integer.parseInt(txtorderid.getText()));
                    preparedStatement.setObject(2, placeOrderController.cusid);
                    preparedStatement.setObject(3, x1);
                    preparedStatement.setObject(4, x2);
                    preparedStatement.setObject(5, Double.parseDouble(txtrest.getText()));
                    preparedStatement.setObject(6, "");
                    preparedStatement.setObject(7, 0.00);

                    boolean saved = preparedStatement.executeUpdate() > 0;

                    if (saved) {

                        Date d = new Date();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
                        String fuffff = simpleDateFormat.format(d);

                        PreparedStatement preparedStatement2 = connection.prepareStatement("insert into orderdetail values(?,?,?,?,?)");

                        for (OrderTM g : fuckorder
                                ) {
                            preparedStatement2.setObject(1, g.getItemid());
                            preparedStatement2.setObject(2, txtorderid.getText());
                            preparedStatement2.setObject(3, g.getQty());
                            preparedStatement2.setObject(4, g.getAmount());
                            preparedStatement2.setObject(5, fuffff);
                            list.add(new OrderTM(g.getItemid(), g.getName(), g.getDescribe(), g.getQty(), g.getAmount()));
                            preparedStatement2.executeUpdate();

                            PreparedStatement pr = connection.prepareStatement("update shop set qty=(qty-?) where id=?");
                            pr.setObject(1, g.getQty());
                            pr.setObject(2, g.getItemid());
                            pr.executeUpdate();
                        }

                        String sql = "INSERT INTO sellpayment(paymenttype,orderid,date,time,payment,description) VALUE(?,?,?,?,?,?)";
                        try {
                            boolean saved2 = CrudUtill.executeUpdate(sql, type, Integer.parseInt(txtorderid.getText()), x1, x2, Double.parseDouble(txtamount.getText()), "");
                            if (saved2) {
                                new Alert(Alert.AlertType.INFORMATION, "Payment Has been Saved !", ButtonType.CLOSE).show();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                            e.printStackTrace();
                        }


                    }


                    connection.commit();
//                    try {
//
//                        double fuckttl;
//                        fuckttl = (Double.parseDouble(txtrest.getText()) - Double.parseDouble(txtamount.getText()));
//
//                        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(list);
//                        Map<String, Object> parameters = new HashMap<>();
//                        String locate = GlobalLocationContent.getLocation();
//                        JasperReport jasperReport = JasperCompileManager.compileReport("" + locate + "BuyPaymentTable.jrxml");
//                        JREmptyDataSource jrEmptyDataSource = new JREmptyDataSource();
//                        parameters.put("ItemDataSource", jrBeanCollectionDataSource);
//                        parameters.put("orderid", Integer.parseInt(txtorderid.getText()));
//                        parameters.put("customerid", placeOrderController.cusid);
//                        parameters.put("total", Double.parseDouble(txtrest.getText()));
//                        parameters.put("payment", Double.parseDouble(txtamount.getText()));
//                        parameters.put("rest", fuckttl);
//                        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jrEmptyDataSource);
//                        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//                        jasperViewer.viewReport(jasperPrint, false);

//                    } catch (JRException e) {
//                        e.printStackTrace();
//                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        connection.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        connection.setAutoCommit(true);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }


            } else {

                String sql = "INSERT INTO sellpayment(paymenttype,orderid,date,time,payment,description) VALUE(?,?,?,?,?,?)";
                try {
                    boolean saved = CrudUtill.executeUpdate(sql, type, Integer.parseInt(txtorderid.getText()), x1, x2, Double.parseDouble(txtamount.getText()), "");
                    if (saved) {
                        new Alert(Alert.AlertType.INFORMATION, "Payment Has been Saved !", ButtonType.CLOSE).show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Contact US (0787418689)", ButtonType.OK).show();
                    e.printStackTrace();
                }
            }

            txtorderid.setText("");
            txtrest.setText("");
            txtamount.setText("");
            txtbalance.setText("");
            search.setText("");

        } else {
            new Alert(Alert.AlertType.WARNING, "Some Fields With Wrong..", ButtonType.OK).show();
        }


    }


    @FXML
    private JFXButton btn;

    @FXML
    void getorders(KeyEvent event) {


        ordertable.getItems().clear();

        if (searchtxtfff.getText().equalsIgnoreCase("")) {

        } else {
            String searchtxt = searchtxtfff.getText();
            searchtxt = "'" + searchtxt + "%'";
            String sql = "select * from sellpayment where orderid like" + searchtxt + "";
            try {
                ResultSet set = CrudUtill.executeQuery(sql);

                while (set.next()) {
                    ordertable.getItems().add(new PaymentTM(
                            set.getInt(1),
                            set.getString(2),
                            set.getInt(3),
                            set.getString(4),
                            set.getString(5),
                            set.getDouble(6),
                            set.getDouble(6) + "",
                            set.getDouble(6)
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
    void loadorderforpay(MouseEvent event) {

        txtamount.setDisable(false);
        txtbalance.setDisable(false);
        btn.setDisable(false);

        double temptotal = 0.00;
        double payidtemptotal = 0.00;
        double amount = 0.00;

        int searchtxt = Integer.parseInt(search.getText());
        String sql = "select * from sellpayment where orderid=?";
        try {
            ResultSet set = CrudUtill.executeQuery(sql, searchtxt);

            while (set.next()) {
                payidtemptotal += set.getDouble(6);
                txtorderid.setText(set.getString(3));
            }

            String sql2 = "select * from orders where orderid=?";
            ResultSet set2 = CrudUtill.executeQuery(sql2, searchtxt);

            if (set2.next()) {
                temptotal = set2.getDouble("total");
                txtorderid.setText(set2.getInt("orderid") + "");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        amount = temptotal - payidtemptotal;
        this.txtrest.setText(amount + "");
        if (amount == 0) {
            txtamount.setDisable(true);
            txtbalance.setDisable(true);
            btn.setDisable(true);
        }

    }


    @FXML
    void getbalance(KeyEvent event) {
        if (txtrest.getText().equalsIgnoreCase("")) {
            new Alert(Alert.AlertType.WARNING, "Something Went Wrong Please Select An Order", ButtonType.OK).show();
        } else {
            double insertamount = 0.00;
            double balance = 0.00;

            if (txtamount.getText().equalsIgnoreCase("")) {
                btn.setDisable(true);
            } else {
                balance = Double.parseDouble(txtrest.getText()) - Double.parseDouble(txtamount.getText());
                btn.setDisable(false);
                if (balance >= 0) {
                    txtbalance.setText(balance + "");
                } else {
                    txtbalance.setText(balance + "");
                    btn.setDisable(true);
                }
            }
        }
    }


}
