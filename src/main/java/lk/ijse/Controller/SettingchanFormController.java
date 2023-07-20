package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import lk.ijse.Model.UserModel;
import lk.ijse.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingchanFormController implements Initializable {

    @FXML
    private TextField newtxt;

    @FXML
    private JFXButton sbtn;

    @FXML
    private TextField usertxt;

    @FXML
    private Label usnmlbl;

    @FXML
    private Label newlbl;

    @FXML
    void saveClicked(MouseEvent event) throws SQLException {

        if(!usertxt.getText().isEmpty()){
            if(!newtxt.getText().isEmpty()){
                boolean isDone =UserModel.chanMail(newtxt.getText());
                if(isDone){
                    usertxt.setText(newtxt.getText());
                    newtxt.setText("");
                    Notification.notice(new Image("/assests/image/wrong.png"),"Setting Error","Email change Successful!");
                }else{
                    newtxt.setText("");
                    Notification.notice(new Image("/assests/image/wrong.png"),"Setting Error","Email change Unsuccessful!");
                }

            }else {
                Notification.notice(new Image("/assests/image/wrong.png"),"Setting Error","Please Enter a Email");
            }
        }else{
            Notification.notice(new Image("/assests/image/wrong.png"),"Setting Error","Enter a Valid Email");
        }

    }

    @FXML
    void unmTyped(KeyEvent event) {

        if(newtxt.getText().isEmpty()){
            newlbl.setVisible(false);
        }else{
            newlbl.setVisible(true);
        }

        if(!newtxt.getText().matches("^(.+)@(\\S+)$")){
            newlbl.setText("Use @gmail.com");
            newlbl.setTextFill(Color.BLACK);
        }else{
            newlbl.setText("Valid Mail");
            newlbl.setTextFill(Color.GREEN);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            usnmlbl.setText(DashboardFormController.useName);
            String name = UserModel.getMail2(DashboardFormController.useName);
            usertxt.setText(name);
            newtxt.requestFocus();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
