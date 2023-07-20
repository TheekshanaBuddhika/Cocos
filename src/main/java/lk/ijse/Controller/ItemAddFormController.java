package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import lk.ijse.Model.ItemModel;
import lk.ijse.Model.PositionModel;
import lk.ijse.Model.RetailModel;
import lk.ijse.dto.Item;
import lk.ijse.dto.SaleType;
import lk.ijse.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ItemAddFormController implements Initializable {

    @FXML
    private JFXButton clearBtn;

    @FXML
    private JFXComboBox<String> cmbtype1;

    @FXML
    private Label itmgrtlbl;

    @FXML
    private Label typelbl;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private TextField txtCode1;

    @FXML
    private TextField txtDescription1;

    @FXML
    private TextField txtQtyOnHand1;

    @FXML
    private TextField txtUnitPrice1;

    @FXML
    private TextField txtUnitPrice2;

    @FXML
    private TextField txtUnitPrice3;

    @FXML
    void clearPressed(ActionEvent event) {
            clear();
    }

    @FXML
    void savePressed(ActionEvent event) {
        if(!(txtCode1.getText().equals(""))) {
            if(!cmbtype1.getValue().isEmpty()){
                String code = txtCode1.getText();
                String description = txtDescription1.getText();
                double qtyOnHand = Double.parseDouble(txtQtyOnHand1.getText());
                double unitPrice = Double.parseDouble(txtUnitPrice1.getText());
                double RetailPrice = Double.parseDouble(txtUnitPrice2.getText());
                double Base = Double.parseDouble(txtUnitPrice3.getText());
                String type = typelbl.getText();

                Item item = new Item(code, description, qtyOnHand, unitPrice,RetailPrice,Base,type);
                if(!(item==null)){
                    try {
                        boolean isSaved = ItemModel.save(item);
                        if (isSaved) {
                            itmgrtlbl.setText(ItemModel.getNextOrderId());
                            clear();
                            Notification.notice(new Image("/assests/image/correct.png"), "Item prompt", "Item Saved");
                        }
                    } catch (SQLException e) {
                        Notification.notice(new Image("/assests/image/wrong.png"), "Item prompt", "Item Doesnot saved");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/wrong.png"), "Item prompt", "Item Already exists");
                }
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"), "Item prompt", "Please fill the form");
            }

            }
    }


    private void clear(){
        txtCode1.setText("");
        txtDescription1.setText("");
        txtQtyOnHand1.setText("");
        txtUnitPrice1.setText("");
        txtUnitPrice2.setText("");
        txtUnitPrice3.setText("");
        saveBtn.setText("Save");
        txtCode1.setEditable(true);
        txtDescription1.setEditable(true);
        cmbtype1.getSelectionModel().clearSelection();
        cmbtype1.setValue(null);
        typelbl.setText("");
    }

    @FXML
    void typeOnAction(ActionEvent event) {
        String pos = cmbtype1.getSelectionModel().getSelectedItem();
        try {
            if(!(pos ==null)){
                SaleType tp = PositionModel.gettypePos(pos);
                typelbl.setText(tp.getTypeid());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void prcTyped(KeyEvent event) {
        if(!txtUnitPrice1.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtUnitPrice1.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void prc2Typed(KeyEvent event) {
        if(!txtUnitPrice2.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtUnitPrice2.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void prc3Typed(KeyEvent event) {
        if(!txtUnitPrice3.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtUnitPrice3.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void qtyTyped(KeyEvent event) {
        if(!txtQtyOnHand1.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtQtyOnHand1.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void codePressed(KeyEvent event){
        if(txtCode1.getText().length()>6){
            txtCode1.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","It#### and only contain 4digits");
        }
    }

    private void loadtype(){
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PositionModel.loadtype();

            for (String code : codes) {
                obList.add(code);
            }
            cmbtype1.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {  loadtype();
            itmgrtlbl.setText(ItemModel.getNextOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
