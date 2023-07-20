package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.Model.UserModel;
import lk.ijse.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SetingpswrdFormController  implements Initializable {

    @FXML
    private PasswordField conpastxt;

    @FXML
    private PasswordField pastxt;

    @FXML
    private JFXButton sbtn;

    @FXML
    private AnchorPane settin2;

    @FXML
    private TextField usertxt;

    @FXML
    private Label usnmlbl;

    @FXML
    void conpasTyped(KeyEvent event) {
        if(conpastxt.getText().length()>8){
            conpastxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Registration Error","Password must consisit of only 8 characters");
        }
    }

    @FXML
    void pasPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            conpastxt.requestFocus();
        }
    }

    @FXML
    void pasTyped(KeyEvent event){

        if(pastxt.getText().length()<8){
            conpastxt.setDisable(true);
            Notification.notice(new Image("/assests/image/exclaim.png"),"Registration Error","Password must consisit of only 8 characters");
        }else if(pastxt.getText().length()>8){
            pastxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Registration Error","Password must consisit of only 8 characters");
        }else{
            conpastxt.setDisable(false);
            conpastxt.requestFocus();
        }


    }


    @FXML
    void saveClicked(MouseEvent event) throws SQLException {
        String un = usertxt.getText();
        if(!usertxt.getText().isEmpty()){
            if(!pastxt.getText().isEmpty()){
                if(!conpastxt.getText().isEmpty()){
                    if(pastxt.getText().length()==8&&conpastxt.getText().length()==8){
                    if(pastxt.getText().equals(conpastxt.getText())){

                        boolean isOkay  = UserModel.chnprof(DashboardFormController.useName,pastxt.getText(),usertxt.getText());

                        if(isOkay){
                            usertxt.setText("");
                            pastxt.setText("");
                            conpastxt.setText("");
                            usnmlbl.setText(un);
                            Notification.notice(new Image("assests/image/exclaim.png"),"Setting Error","Profile change successful");
                        }else{
                            Notification.notice(new Image("assests/image/exclaim.png"),"Setting Error","Profile did not change");
                        }

                    } else{
                        Notification.notice(new Image("assests/image/wrong.png"),"Setting Error","Check your password");
                    }
                    } else{
                        pastxt.requestFocus();
                        pastxt.setText("");
                        conpastxt.setText("");
                        Notification.notice(new Image("/assests/image/exclaim.png"),"Registration Error","Password must consisit of only 8 characters");
                    }

                }else{
                    pastxt.requestFocus();
                    pastxt.setText("");
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Setting Error","Please input A password");
                }
            }else{
                pastxt.requestFocus();
                Notification.notice(new Image("/assests/image/exclaim.png"),"Setting Error","Please input A password");
            }
        }else{
            usertxt.requestFocus();
            Notification.notice(new Image("/assests/image/exclaim.png"),"Settng Error","Please input A user name");

        }

    }


    @FXML
    void unmTyped(KeyEvent event) {
        if(usertxt.getText().isEmpty()){
            pastxt.setText("");
            conpastxt.setText("");
        }
    }

    @FXML
    void usPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            pastxt.requestFocus();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usnmlbl.setText(DashboardFormController.useName);
        usertxt.setText(DashboardFormController.useName);
        usertxt.requestFocus();

    }

}
