package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import lk.ijse.Model.UserModel;
import lk.ijse.util.Notification;
import lk.ijse.util.Service;
import lk.ijse.util.Stageset;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;

public class OTPFormController {

    private static String otp2;
    private static String username;
    private static String password;
    private static String employeeid;

    @FXML
    private JFXButton backbtn;

    @FXML
    private TextField fifthbtn;

    @FXML
    private TextField firstbtn;

    @FXML
    private TextField fourthbtn;

    @FXML
    private Text resendlbl;

    @FXML
    private TextField secbtn;

    @FXML
    private TextField thirdbtn;

    @FXML
    private JFXButton verifybtn;

    @FXML
    private AnchorPane root;



    @FXML
    void backPressed(ActionEvent event) throws IOException {
        new Stageset().getStage("/view/Registration_form.fxml",root,"Dedu Pvt.ltd",new Image("/assests/image/logo.png"));
    }

    @FXML
    void fifithTyped(KeyEvent event) {

        if(event.getCharacter().matches("[^\\e\t\r\\d+$]")){
            fifthbtn.setText("");
            Toolkit.getDefaultToolkit().beep();
        }else {
            verifybtn.requestFocus();
        }
    }

    @FXML
    void firstTyped(KeyEvent event) {
        keyHandle(event,firstbtn,secbtn);
    }

    @FXML
    void fourthTyped(KeyEvent event) {
        keyHandle(event,fourthbtn,fifthbtn);
    }

    @FXML
    void resendClicked(MouseEvent event) {
       otp2 =  Service.senMail();
    }

    @FXML
    void secTyped(KeyEvent event) {
        keyHandle(event,secbtn,thirdbtn);
    }

    @FXML
    void thirdTyped(KeyEvent event) {
        keyHandle(event,thirdbtn,fourthbtn);
    }

    @FXML
    void verifyPressed(ActionEvent event) {
        String demootp = firstbtn.getText()+secbtn.getText()+thirdbtn.getText()+fourthbtn.getText()+fifthbtn.getText();
        firstbtn.setText("");
        secbtn.setText("");
        thirdbtn.setText("");
        fourthbtn.setText("");
        fifthbtn.setText("");
        if(otp2.equals(demootp)){

            try {
                boolean num = UserModel.setUser(username,password,employeeid);
                if(num){
                    Notification.notice(new Image("/assests/image/correct.png"),"OTP Verification","Registration Successful");
                    new Stageset().getStage("/view/Login_form.fxml",root,"Dedu Pvt.ltd",new Image("/assests/image/logo.png"));

                }else{
                    Notification.notice(new Image("/assests/image/wrong.png"),"Database verification","Sql server error");
                }
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }


        }else{
            Notification.notice(new Image("/assests/image/wrong.png"),"OTP Verification","Check your OTP Again.");
        }
    }

    public void getOtp(String otp,String name, String pas ,String id) {
        otp2 = otp;
        username=name;
        password = pas;
        employeeid = id;
    }

    private void keyHandle(KeyEvent event,TextField btn,TextField btn2){
        if(event.getCharacter().matches("[^\\e\t\r\\d+$]")){
            btn.setText("");
            Toolkit.getDefaultToolkit().beep();
        }else{
            btn2.requestFocus();
        }
    }

}
