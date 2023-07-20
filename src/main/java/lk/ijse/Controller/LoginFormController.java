package lk.ijse.Controller;

import animatefx.animation.*;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.Model.HistoryModel;
import lk.ijse.Model.UserModel;
import lk.ijse.dto.User;
import lk.ijse.util.Notification;
import lk.ijse.util.Stageset;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class LoginFormController {

    @FXML
    private JFXButton clsbtn;

    @FXML
    private JFXButton clsbtn1;

    @FXML
    private Text forgottxt;

    @FXML
    private JFXButton loginbtn;

    @FXML
    private ImageView passimg;

    @FXML
    private PasswordField passtxt;

    @FXML
    private Label passwtxt;

    @FXML
    private JFXButton regbtn;

    @FXML
    private AnchorPane root;

    @FXML
    private ImageView useimg;

    @FXML
    private ImageView logn;

    @FXML
    private TextField usernametxt;

    @FXML
    private Label usetxt;


    @FXML
    void clsClicked(MouseEvent event){
        System.exit(1);
    }

    @FXML
    void clsClicked1(MouseEvent event){
        Stage stage = (Stage) clsbtn1.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void logenter(MouseEvent event){

        new RotateInDownRight(logn).play();

    }

    @FXML
    void logenter1(MouseEvent event){

        new RotateInUpLeft(logn).play();

    }

    @FXML
    void btnPressed(ActionEvent event) throws IOException {
        //username and password checking
        try {
            if(usernametxt.getText().equals("") && passtxt.getText().equals("")){

                new Shake(usernametxt).play();
                new Shake(passtxt).play();
                new Shake(useimg).play();
                new Shake(passimg).play();
                Notification.notice(new Image("/assests/image/exclaim.png"),"Login Error","Please Enter Username And Password");

            }else{
                User user = UserModel.getUser(usernametxt.getText());
                login(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    @FXML
    void userPressed(KeyEvent event) {
        //getting focus from password field
        if (event.getCode().equals(KeyCode.ENTER)) {
            Notification.notice(new Image("/assests/image/exclaim.png"),"Login Infomation","Please Enter Password");
            passtxt.requestFocus();
        }else{
        usetxt.setText("Username");
        }
    }

    @FXML
    void passPressed(KeyEvent event) throws IOException {
    //username and password checking
        if (event.getCode().equals(KeyCode.ENTER)) {
            try {
                User user = UserModel.getUser(usernametxt.getText());
                login(user);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }else{

            passwtxt.setText("Password");
        }
    }



    @FXML
    void regPressed(ActionEvent event) throws IOException {
        //Sign-up form loading
        new Stageset().getStage("/view/Registration_form.fxml",root,"Dedu Pvt.ltd",new Image("/assests/image/logo.png"));
    }

    @FXML
    void forgotClicked(MouseEvent event) throws IOException, SQLException {
        //forgot password
        String nm = usernametxt.getText();
        if(nm.equals("")){
            Notification.notice(new Image("/assests/image/exclaim.png"),"Login Error","Please Enter a Username");
        }else{
            User user = UserModel.getUser(nm);
            if(!(user==null)){

                ForgotFormController.runn(root,nm);
                Parent root2 = FXMLLoader.load(getClass().getResource("/view/forgot_form.fxml"));
                Scene scene = new Scene(root2);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(root.getScene().getWindow());

                stage.show();

            }else{
                Notification.notice(new Image("/assests/image/wrong.png"),"Login Error","You haven't registered");
            }
        }

    }

    //login method
    private void login(User user) throws IOException, SQLException {
        if(user!=null) {
             String name=user.getUsername();
            if (user.getUsername().equals(usernametxt.getText())&&user.getPassword().equals(passtxt.getText())) {

                DashboardFormController.useName = name;
                HistoryModel.setHistory(name, LocalDate.now(), LocalTime.now());
                AnchorPane pane = FXMLLoader.load(getClass().getResource("/view/dashboard_form.fxml"));
                root.getChildren().removeAll();
                root.getChildren().setAll(pane);
                    } else {
                new Shake(usernametxt).play();
                new Shake(passtxt).play();
                new Shake(useimg).play();
                new Shake(passimg).play();
                Notification.notice(new Image("/assests/image/wrong.png"),"Login Error","Username or Password is Wrong");
                    }
        }else{
            new Shake(usernametxt).play();
            new Shake(passtxt).play();
            new Shake(useimg).play();
            new Shake(passimg).play();
            Notification.notice(new Image("/assests/image/wrong.png"),"Login Error","Username or Password is Wrong");
            Toolkit.getDefaultToolkit().beep();
        }
    }

}
