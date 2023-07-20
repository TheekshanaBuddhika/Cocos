package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import lk.ijse.Model.SellerModel;
import lk.ijse.dto.Seller;
import lk.ijse.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

import static lk.ijse.Controller.DashboardFormController.useName;

public class Seller2FormController implements Initializable {

    @FXML
    private JFXButton clearBtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private TextField supTimetxt;

    @FXML
    private TextField supaddtxt;

    @FXML
    private DatePicker supdatetxt;

    @FXML
    private TextField supidtxt;

    @FXML
    private TextField supnametxt;

    @FXML
    private Label buyidlbl;

    @FXML
    void clearPressed(ActionEvent event) {
            clear();
    }

    @FXML
    void savePressed(ActionEvent event) {
        if(!(supidtxt.getText().equals(""))){
                String id = supidtxt.getText();
                String name = supnametxt.getText();
                String address = supaddtxt.getText();
                java.util.Date date = java.util.Date.from(supdatetxt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date date1 = new java.sql.Date(date.getTime());
            int time = 0;
            if(supTimetxt.getText().isEmpty()){
                time = 0;
            }else{
                time = Integer.parseInt(supTimetxt.getText());
            }

                Seller seller = new Seller(id,name,address,date1,time,useName);

                try {
                    boolean isSaved = SellerModel.save(seller);
                    if (isSaved) {
                        clear();
                        Notification.notice(new Image("/assests/image/correct.png"),"Supplier prompt","Supplier Saved");
                    }
                } catch (SQLException e) {
                    Notification.notice(new Image("/assests/image/wrong.png"),"Supplier prompt","Supplier Doesnot saved");
                }

        }

    }

    private void clear(){
        supidtxt.setText("");
        supnametxt.setText("");
        supdatetxt.setValue(null);
        supTimetxt.setText("");
        supaddtxt.setText("");
    }

    @FXML
    void timeTyped(KeyEvent event) {

        if(!supTimetxt.getText().matches("^(\\d+)$")){
            supTimetxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Seller Error","Only digits approved");
        }

    }

    @FXML
    void supTyPed(KeyEvent event) {

        if(!(supidtxt.getText().length() <7)){
            supidtxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item prompt","Character length must be 6 Digits");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            buyidlbl.setText(SellerModel.getNextOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
