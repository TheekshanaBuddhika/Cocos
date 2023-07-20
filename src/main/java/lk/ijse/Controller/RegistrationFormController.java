package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.PositionModel;
import lk.ijse.Model.UserModel;
import lk.ijse.dto.Employee;
import lk.ijse.dto.Position;
import lk.ijse.dto.User;
import lk.ijse.util.Notification;
import lk.ijse.util.Service;
import lk.ijse.util.Stageset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegistrationFormController {

    @FXML
    private Label conpaslbl;

    @FXML
    private PasswordField conpastxt;

    @FXML
    private Label emplbl;

    @FXML
    private TextField emptxt;

    @FXML
    private Label namelbl;

    @FXML
    private TextField nametxt;

    @FXML
    private Label paslbl;

    @FXML
    private Label warninglbl;

    @FXML
    private PasswordField pastxt;

    @FXML
    private TextField postxt;

    @FXML
    private JFXButton registerbtn;

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView personImage;

    @FXML
    private JFXButton signInbtn;

    @FXML
    private JFXButton binbtn;

    @FXML
    void empPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            try {
                Employee employee = EmployeeModel.getPosition(emptxt.getText());
                if(employee!=null){
                    User user = UserModel.getUserId(employee.getEmpid());
                    if(user!=null){
                        Notification.notice(new Image("assests/image/wrong.png"),"Registration Error","This Employee already exists");
                    }else{
                        Image img = EmployeeModel.settt(employee.getEmpid());
                        Position position = PositionModel.getPos2(employee.getPosId());
                        postxt.setText(position.getPosName());

                        List<String> data = PositionModel.getPermission();
                        boolean ab = false;
                        for (String a:data) {
                            if(position.getPositionId().equals(a)){
                                ab = true;
                                break;
                            }
                        }

                        if(ab){

                            nametxt.setDisable(false);
                            namelbl.setDisable(false);
                            pastxt.setDisable(false);
                            paslbl.setDisable(false);
                            conpastxt.setDisable(false);
                            conpaslbl.setDisable(false);
                            registerbtn.setDisable(false);
                            binbtn.setDisable(false);
                            emptxt.setEditable(false);
                            personImage.setImage(img);
                        }else{
                            personImage.setImage(img);
                            Notification.notice(new Image("assests/image/wrong.png"),"Registration Error","Permission Denied");
                        }

                    }

                }else{
                    Notification.notice(new Image("assests/image/wrong.png"),"Registration Error","No Employee found");
                    nametxt.setDisable(true);
                    namelbl.setDisable(true);
                    pastxt.setDisable(true);
                    paslbl.setDisable(true);
                    conpastxt.setDisable(true);
                    conpaslbl.setDisable(true);
                    registerbtn.setDisable(true);
                    binbtn.setDisable(true);
                }

            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }

        }


    }

    @FXML
    void usePressed(KeyEvent event) throws SQLException {

        if(event.getCode().equals(KeyCode.ENTER)){
            List<String> data = UserModel.getName();
            for (String a:data) {
               if(nametxt.getText().equals(a)){
                   Notification.notice(new Image("/assests/image/exclaim.png"),"Registration error","Username is already taken");
                   nametxt.setText("");
                   break;
               }
            }
            pastxt.requestFocus();
        }

    }

    @FXML
    void pasTyped(KeyEvent event){

            if(pastxt.getText().length()<8){
                conpastxt.setDisable(true);
                warninglbl.setVisible(true);
                warninglbl.setTextFill(Color.RED);
                warninglbl.setText("Password must have 8 letters");
            }else if(pastxt.getText().length()>8){
                warninglbl.setVisible(false);
                pastxt.setText("");
                Notification.notice(new Image("/assests/image/exclaim.png"),"Registration Error","Password must consisit of only 8 characters");
            }else{
                warninglbl.setVisible(true);
                warninglbl.setText("Password has 8 letters");
                warninglbl.setTextFill(Color.GREEN);
                conpastxt.setDisable(false);
                conpastxt.requestFocus();
            }


    }

    @FXML
    void pasPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            conpastxt.requestFocus();
        }
    }

    @FXML
    void registerPressed(ActionEvent event) throws IOException, SQLException {
        String name = nametxt.getText();
        String con = conpastxt.getText();
        String pas = pastxt.getText();
        String id = emptxt.getText();
        if(!(name.equals("")||con.equals("")||pas.equals(""))){


            List<String> data = UserModel.getName();
            boolean ab = false;
            for (String a:data) {
                if(nametxt.getText().equals(a)){
                    ab = true;
                    break;
                }
            }
            if(!ab){
                if(pas.length()==8&&con.length()==8){
                    if(pas.equals(con)){
                        new Stageset().getStage("/view/OTP_form.fxml", root, "OTP Verification", new Image("/assests/image/logo.png"));
                        OTPFormController otpFormController = new OTPFormController();
                        Thread thread = new Thread(()->{
                            try {
                                otpFormController.getOtp(Service.senMail(),name,pas,id);

                            }catch (Exception e){

                            }
                        });

                        thread.start();

                    } else{
                        Notification.notice(new Image("assests/image/wrong.png"),"Registration Error","Check your password");
                    }
                }else{
                    pastxt.requestFocus();
                    pastxt.setText("");
                    conpastxt.setText("");
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Registration Error","Password must consisit of only 8 characters");
                }
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Registration error","Username is already taken");
                pastxt.setText("");
                conpastxt.setText("");
                nametxt.setText("");
                nametxt.requestFocus();
            }
        }else {
            Notification.notice(new Image("assests/image/exclaim.png"),"Registration Error","PLease fill the form");

        }

    }

    @FXML
    void signPressed(ActionEvent event) throws IOException {

        new Stageset().getStage("/view/Login_form.fxml",root,"Dedu Pvt.ltd",new Image("/assests/image/logo.png"));

    }

    @FXML
    void binPressed(ActionEvent event){
        warninglbl.setVisible(false);
        emptxt.setText("");
        postxt.setText("");
        nametxt.setText("");
        pastxt.setText("");
        conpastxt.setText("");
        personImage.setImage(new Image("/assests/image/manager.png"));
        emptxt.setEditable(true);
        nametxt.setDisable(true);
        namelbl.setDisable(true);
        pastxt.setDisable(true);
        paslbl.setDisable(true);
        conpastxt.setDisable(true);
        conpaslbl.setDisable(true);
        registerbtn.setDisable(true);
        binbtn.setDisable(true);
    }

}
