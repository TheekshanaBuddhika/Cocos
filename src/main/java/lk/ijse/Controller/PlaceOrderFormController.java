package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import lk.ijse.Model.BuyerDetailModel;
import lk.ijse.Model.BuyerModel;
import lk.ijse.Model.ItemModel;
import lk.ijse.Model.PlaceOrderModel;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Buyer;
import lk.ijse.dto.Cart;
import lk.ijse.dto.Item;
import lk.ijse.dto.TM.CartTM;
import lk.ijse.util.Notification;
import lk.ijse.util.Stageset;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PlaceOrderFormController implements Initializable {


    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton cartbtn;

    @FXML
    private TextField cashtxt;

    @FXML
    private JFXButton clbtn;

    @FXML
    private JFXButton clrbtn;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private JFXButton conbtn;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private Label lblchn;


    @FXML
    private JFXButton rtlbtn;

    @FXML
    private JFXButton stkbtn;

    @FXML
    private TableView<CartTM> tblOrderCart;

    @FXML
    private JFXButton trcbtn;

    @FXML
    private Label lblrmn;

    @FXML
    private JFXButton rldbtn;

    @FXML
    private Label lblrmnnm;

    @FXML
    private TextField txtQty;

    private ObservableList<CartTM> obList = FXCollections.observableArrayList();

    @FXML
    void reloadClicked(MouseEvent event) {
        cmbCustomerId.getSelectionModel().clearSelection();
        lblCustomerName.setText("");
        loadId();
    }

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

        if(!lblCustomerName.getText().isEmpty()){
            if(!lblDescription.getText().isEmpty()){
                if(!txtQty.getText().isEmpty()){
                    Double calc = Double.parseDouble(lblrmn.getText())- Double.parseDouble(txtQty.getText());
                    if(calc>=0){
                        String code = lblDescription.getText();
                        String name = cmbItemCode.getValue();
                        Double qty = Double.parseDouble(txtQty.getText());
                        double unitPrice = Double.parseDouble(lblUnitPrice.getText());
                        double total = qty * unitPrice;
                        JFXButton btnRemove = new JFXButton("Remove");
                        btnRemove.setTextFill(Color.WHITE);
                        btnRemove.setCursor(Cursor.HAND);

                        setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

                        if (!obList.isEmpty()) {
                            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                                if (colItemCode.getCellData(i).equals(code)) {
                                    qty += (Double) colQty.getCellData(i);
                                    total = qty * unitPrice;

                                    obList.get(i).setQty(qty);
                                    obList.get(i).setTotal(total);
                                    lblrmn.setText((Double.parseDouble(lblrmn.getText())-Double.parseDouble(txtQty.getText()))+"");
                                    txtQty.setText("");
                                    tblOrderCart.refresh();
                                    calculateNetTotal();
                                    return;
                                }
                            }
                        }

                        CartTM tm = new CartTM(code, name, qty, unitPrice, total, btnRemove);

                        obList.add(tm);
                        tblOrderCart.setItems(obList);
                        lblrmn.setText((Double.parseDouble(lblrmn.getText())-qty)+"");
                        txtQty.setText("");
                        calculateNetTotal();
                    }else{
                        cartbtn.setDisable(true);
                        Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","No item left");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Pleas eadd the quantity");
                }
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please fill the Item detail");
            }
        }else{
            Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please fill the buyer detail");
        }

    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/View/order_form.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Buyer Manage");
        stage.getIcons().add(new Image("/assests/image/logo.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(pane.getScene().getWindow());
        stage.show();
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        if(!lblCustomerName.getText().isEmpty()){
            if(!lblDescription.getText().isEmpty()){
                    if((!cashtxt.getText().isEmpty())) {
                        String oId = lblCustomerName.getText();

                        List<Cart> cartDTOList = new ArrayList<>();

                        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                            CartTM cartTM = obList.get(i);

                            Cart dto = new Cart(
                                    cartTM.getCode(),
                                    cartTM.getQty(),
                                    cartTM.getUnitPrice()
                            );
                            cartDTOList.add(dto);
                        }
                        LocalTime time = LocalTime.now();
                        Time t = Time.valueOf(time);
                        boolean isPlaced = false;
                        try {
                            isPlaced = PlaceOrderModel.placeOrder(oId, lblNetTotal.getText(), lblchn.getText(), cashtxt.getText(), cartDTOList, lblDescription.getText(),time);
                            if (isPlaced) {

                                Platform.runLater(()->{
                                    InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/bill.jrxml");
                                    JasperReport compile = null;
                                    try {
                                        compile = JasperCompileManager.compileReport(rpt);
                                        Map<String,Object> data = new HashMap<>();
                                        data.put("stime", t);
                                        data.put("buyid",oId);
                                        data.put("nettot",Double.parseDouble(lblNetTotal.getText()));
                                        data.put("pay",Double.parseDouble(cashtxt.getText()));
                                        data.put("change",Double.parseDouble(lblchn.getText()));
                                        JasperPrint report = JasperFillManager.fillReport(compile,data, DBConnection.getInstance().getConnection());
                                        JasperViewer.viewReport(report,false);
                                        tblOrderCart.getItems().clear();
                                        cashtxt.setText("");
                                        lblNetTotal.setText("");
                                        lblchn.setText("");
                                        Notification.notice(new Image("/assests/image/correct.png"),"Stock Confirmation","Order Successful");
                                    } catch (JRException | SQLException e) {
                                        e.printStackTrace();
                                    }


                                });



                            } else {
                                Notification.notice(new Image("/assests/image/wrong.png"),"Stock Confirmation","Order Unsuccessful");
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            new Alert(Alert.AlertType.ERROR, "SQL Error").show();
                        }
                    }else{
                        Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please enter all info");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please enter all info");
                }
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please enter all info");
            }

    }

    @FXML
    void cashPressed(KeyEvent event) {

            if(!(lblNetTotal.getText().isEmpty())){
                if(cashtxt.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
                    Double cash = Double.parseDouble(cashtxt.getText()) - Double.parseDouble(lblNetTotal.getText());
                    lblchn.setText(cash + "");
                }else{
                    cashtxt.setText("");
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please enter decimal number");
                }

            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please enter all info");
            }

        if(cashtxt.getText().isEmpty()){
            lblchn.setText("");
        }

    }

    @FXML
    void clOnAction(ActionEvent event) {

        tblOrderCart.getItems().clear();
        lblNetTotal.setText("");
        cashtxt.setText("");
        lblchn.setText("");
        cartbtn.setDisable(false);
        lblQtyOnHand.setText("");
        lblUnitPrice.setText("");
        lblrmn.setText("");
        txtQty.setText("");
        lblCustomerName.setText("");
        cmbCustomerId.setDisable(false);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbCustomerId.setValue(null);
        lblDescription.setText("");
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.setValue(null);
    }

    @FXML
    void clrOnAction(ActionEvent event) {

        cartbtn.setDisable(false);
        lblQtyOnHand.setText("");
        lblUnitPrice.setText("");
        lblrmn.setText("");
        txtQty.setText("");
        lblDescription.setText("");
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.setValue(null);

    }

    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = cmbCustomerId.getSelectionModel().getSelectedItem();
        if( !(id == null)){
            try {
                Buyer buyer = BuyerModel.searchById(id);
                lblCustomerName.setText(buyer.getId());
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }else{

        }

    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        cartbtn.setDisable(false);
        cmbCustomerId.setDisable(true);
        String code = cmbItemCode.getSelectionModel().getSelectedItem();
        if(!(code == null)) {
            try {
                Item item = ItemModel.searchById(code);
                fillItemFields(item);
                txtQty.requestFocus();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }else{

        }
    }

    @FXML
    void rtlOnAction(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/Daily_sales_form.fxml",pane);
    }

    @FXML
    void trcOnAction(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/Transaction_form.fxml",pane);
    }

    @FXML
    void txtQtyOnAction(KeyEvent event) {
       if(txtQty.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){

       }else{
           txtQty.setText("");
           Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please enter decimal number");
       }
    }

    private void loadId() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = BuyerModel.loadIds();

            for (String code : codes) {
                obList.add(code);
            }
            cmbCustomerId.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loaditem() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.loadIds();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void fillItemFields(Item item) {
        lblDescription.setText(item.getCode());
        lblUnitPrice.setText(String.valueOf(item.getUnitPrice()));
        lblQtyOnHand.setText(String.valueOf(item.getQtyOnHand()));
        lblrmn.setText(String.valueOf(item.getQtyOnHand()));

        if(!tblOrderCart.getItems().isEmpty()){
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(item.getCode())) {

                    Double cc =  Double.parseDouble(lblQtyOnHand.getText()) - (Double) colQty.getCellData(i);
                   lblrmn.setText(cc+"");
                    return;
                }
            }
        }else{
            lblrmn.setText(String.valueOf(item.getQtyOnHand()));
        }

    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = tblOrderCart.getSelectionModel().getSelectedIndex();
                if(index>=0){
                    lblrmn.setText(lblQtyOnHand.getText());
                    obList.remove(index);
                    tblOrderCart.refresh();
                    calculateNetTotal();
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
                }

            }

        });
    }

    private void calculateNetTotal() {
        double netTotal = 0.0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            double total = (double) colTotal.getCellData(i);
            netTotal += total;
        }
        lblNetTotal.setText(String.valueOf(netTotal));

    }

    void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        lblOrderDate.setText(LocalDate.now()+"");
        loadId();
        loaditem();
        setCellValueFactory();
        cmbCustomerId.setDisable(false);
    }
}
