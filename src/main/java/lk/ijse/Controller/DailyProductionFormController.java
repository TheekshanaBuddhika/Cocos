package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.Model.ItemModel;
import lk.ijse.Model.PlaceOrderModel;
import lk.ijse.Model.ProductModel;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Cart;
import lk.ijse.dto.Item;
import lk.ijse.dto.Product;
import lk.ijse.dto.TM.CartTM;
import lk.ijse.dto.TM.ProductTM;
import lk.ijse.util.Notification;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DailyProductionFormController implements Initializable {
    @FXML
    private JFXButton clearBtn;

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmpingradient;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colQuan;

    @FXML
    private TableColumn<?, ?> colingname;

    @FXML
    private TableColumn<?, ?> colingqty;

    @FXML
    private TableColumn<?, ?> colitm;

    @FXML
    private Label ingcode;

    @FXML
    private AnchorPane prpane;


    @FXML
    private JFXButton itmsvbtn;

    @FXML
    private TextField pdid;

    @FXML
    private TableView <ProductTM> prtbl;

    @FXML
    private TextField pttxt;

    @FXML
    private Label qnlbl;

    @FXML
    private TextField qtxt;

    @FXML
    private JFXButton reloadbtn;

    @FXML
    private JFXButton prbtn;

    private ObservableList<ProductTM> obList = FXCollections.observableArrayList();


    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if(!ingcode.getText().isEmpty()){
            if(!qtxt.getText().isEmpty()){
                if(!pdid.getText().isEmpty()){
                    if(!pttxt.getText().isEmpty()){
                        Double calc = Double.parseDouble(qnlbl.getText())- Double.parseDouble(qtxt.getText());
                        if(calc>=0){
                            String code = pdid.getText();
                            String name = cmbItemCode.getValue();
                            Double qty = Double.parseDouble(pttxt.getText());
                            String name2 = cmpingradient.getValue();
                            Double qty2 =Double.parseDouble( qtxt.getText());
                            JFXButton btnRemove = new JFXButton("Remove");
                            btnRemove.setTextFill(Color.WHITE);
                            btnRemove.setCursor(Cursor.HAND);

                            setRemoveBtnOnAction(btnRemove); /* set action to the btnRemove */

                            if (!obList.isEmpty()) {
                                for (int i = 0; i < prtbl.getItems().size(); i++) {
                                    if (colCode.getCellData(i).equals(code)) {
                                        qty += (Double) colQuan.getCellData(i);
                                        qty2 += (Double) colingqty.getCellData(i);
                                        obList.get(i).setQty(qty);
                                       obList.get(i).setInqqty(qty2);
                                        qtxt.setText("");
                                        pttxt.setText("");
                                        prtbl.refresh();
                                        return;
                                    }
                                }
                            }

                            ProductTM tm = new ProductTM(code, name, qty, name2, qty2, btnRemove);

                            obList.add(tm);
                            prtbl.setItems(obList);
                            qtxt.setText("");
                            pttxt.setText("");

                        }else{
                            itmsvbtn.setDisable(true);
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

        }else{
            Notification.notice(new Image("/assests/image/exclaim.png"),"Order Error","Please fill the buyer detail");
        }
    }

    @FXML
    void clearPressed(ActionEvent event) {

        cmpingradient.setDisable(false);
        qnlbl.setText("");
        pttxt.setText("");
        pdid.setText("");
        qtxt.setText("");
        ingcode.setText("");
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.setValue(null);
        cmpingradient.getSelectionModel().clearSelection();
        cmpingradient.setValue(null);
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {

        cmpingradient.setDisable(true);
        String code = cmbItemCode.getSelectionModel().getSelectedItem();
        if(!(code == null)) {
            try {
                Item item = ItemModel.searchById(code);
                pdid.setText(item.getCode());
                pttxt.requestFocus();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }else{

        }
    }

    @FXML
    void ingOnAction(ActionEvent event) {

        String code = cmpingradient.getSelectionModel().getSelectedItem();
        if(!(code == null)) {
            try {
                Item item = ItemModel.searchById(code);
                ingcode.setText(item.getType());
                qnlbl.setText(String.valueOf(item.getQtyOnHand()));
                qtxt.requestFocus();
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
            }
        }else{

        }
    }

    @FXML
    void proOnAction(KeyEvent event) {
        if(!pttxt.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            pttxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Production Error","Please enter decimal number");
        }
    }

    @FXML
    void qOnAction(KeyEvent event) {

        if(!qtxt.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            qtxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Production Error","Please enter decimal number");
        }
    }

    @FXML
    void reloadClicked(MouseEvent event) {
        loaditem();
        loaditm();
    }

    @FXML
    void plcOnAction(ActionEvent event) {
        if(!ingcode.getText().isEmpty()){
            if(!pdid.getText().isEmpty()){
                    String oId = pdid.getText();
                    String ing= cmpingradient.getValue();
                    List<Product> cartDTOList = new ArrayList<>();

                    for (int i = 0; i < prtbl.getItems().size(); i++) {
                        ProductTM cartTM = obList.get(i);

                        Product dto = new Product(
                                cartTM.getItemid(),
                                cartTM.getItemname(),
                                cartTM.getQty(),
                                cartTM.getIngname(),
                                cartTM.getInqqty()
                        );
                        cartDTOList.add(dto);
                    }

                    boolean isPlaced = false;
                    try {
                        isPlaced = ProductModel.placeOrder(oId, ing, cartDTOList, ingcode.getText());
                        if (isPlaced) {
                            prtbl.getItems().clear();
                            new Alert(Alert.AlertType.CONFIRMATION, "Order Placed").show();

                        } else {
                            new Alert(Alert.AlertType.ERROR, "Order Not Placed").show();
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
    }

    @FXML
    void clClicked(MouseEvent event) {
        prtbl.getItems().clear();
        cmpingradient.setDisable(false);
        qnlbl.setText("");
        pttxt.setText("");
        pdid.setText("");
        qtxt.setText("");
        ingcode.setText("");
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.setValue(null);
        cmpingradient.getSelectionModel().clearSelection();
        cmpingradient.setValue(null);

    }

    @FXML
    void prClicked(MouseEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/View/chart_form.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(prpane.getScene().getWindow());
        stage.show();
    }

    private void loaditem() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.loadIds1();

            for (String code : codes) {
                obList.add(code);
            }
            cmpingradient.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void loaditm(){
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = ItemModel.loadIds2();

            for (String code : codes) {
                obList.add(code);
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("itemid"));
        colitm.setCellValueFactory(new PropertyValueFactory<>("itemname"));
        colQuan.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colingname.setCellValueFactory(new PropertyValueFactory<>("ingname"));
        colingqty.setCellValueFactory(new PropertyValueFactory<>("inqqty"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void setRemoveBtnOnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (result.orElse(no) == yes) {

                int index = prtbl.getSelectionModel().getSelectedIndex();
                if(index>=0){
                    obList.remove(index);
                    prtbl.refresh();
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
                }

            }

        });
    }

    @FXML
    void prOnAction(ActionEvent event) throws JRException, SQLException {
        Thread thread = new Thread(()->{
            try {
                InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/Production.jrxml");
                JasperReport compile =  JasperCompileManager.compileReport(rpt);
                JasperPrint report = JasperFillManager.fillReport(compile,null, DBConnection.getInstance().getConnection());
                JasperViewer.viewReport(report,false);
            }catch (Exception e){

            }
        });

        thread.start();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loaditem();
        setCellValueFactory();
        loaditm();
    }
}
