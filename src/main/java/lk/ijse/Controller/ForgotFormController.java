package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lk.ijse.Model.HistoryModel;
import lk.ijse.util.Notification;
import lk.ijse.util.Service;
import lk.ijse.util.Stageset;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;


public class ForgotFormController {


    @FXML
    private TextField fifthbtn;

    @FXML
    private TextField firstbtn;

    @FXML
    private TextField fourthbtn;

    @FXML
    private Text otplbl;

    @FXML
    private Text ddlbl;

    @FXML
    private Text resendlbl;

    @FXML
    private AnchorPane root2;

    @FXML
    private static AnchorPane npane;

    @FXML
    private static String name;

    @FXML
    private TextField secbtn;

    @FXML
    private JFXButton snd;

    @FXML
    private TextField thirdbtn;

    @FXML
    private JFXButton verifybtn;

    private static String fgotp;

    public static void runn(AnchorPane root,String nm) {
        npane = root;
        name = nm;
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
        Thread thread = new Thread(()->{
            try {
                fgotp = Service.senMail();

            }catch (Exception e){

            }
        });

        thread.start();
    }

    @FXML
    void secTyped(KeyEvent event) {
        keyHandle(event,secbtn,thirdbtn);
    }

    @FXML
    void sndPressed(ActionEvent event)  {

        Thread thread = new Thread(()->{
            try {
                fgotp = Service.senMail();

            }catch (Exception e){

            }
        });

        thread.start();

        firstbtn.setVisible(true);
        secbtn.setVisible(true);
        thirdbtn.setVisible(true);
        fourthbtn.setVisible(true);
        fifthbtn.setVisible(true);
        resendlbl.setVisible(true);
        ddlbl.setVisible(true);
        verifybtn.setVisible(true);
        snd.setVisible(false);
    }

    @FXML
    void thirdTyped(KeyEvent event) {
        keyHandle(event,thirdbtn,fourthbtn);
    }

    @FXML
    void verifyPressed(ActionEvent event) throws IOException, SQLException {
            String demootp = firstbtn.getText()+secbtn.getText()+thirdbtn.getText()+fourthbtn.getText()+fifthbtn.getText();
            if(!(demootp.equals(""))){
                firstbtn.setText("");
                secbtn.setText("");
                thirdbtn.setText("");
                fourthbtn.setText("");
                fifthbtn.setText("");

                if(fgotp.equals(demootp)){
                    DashboardFormController.useName = name;
                    HistoryModel.setHistory(name, LocalDate.now(), LocalTime.now());
                    Notification.notice(new javafx.scene.image.Image("/assests/image/correct.png"),"OTP Verification","Login Successful");
                    new Stageset().getStage("/View/dashboard_form.fxml",npane,"Dedu Pvt.ltd",new javafx.scene.image.Image("/assests/image/logo.png"));
                    Stage stage = (Stage) root2.getScene().getWindow();
                    stage.close();
                }else{
                    Notification.notice(new Image("/assests/image/wrong.png"),"OTP Verification","Check your OTP Again.");
                }
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"OTP Verification","Add your OTP .");
            }

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
