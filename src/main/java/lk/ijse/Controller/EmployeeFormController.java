package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.PositionModel;
import lk.ijse.Model.UserModel;
import lk.ijse.dto.Employee;
import lk.ijse.dto.Position;
import lk.ijse.dto.TM.EmployeeTM;
import lk.ijse.dto.User;
import lk.ijse.util.Notification;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.*;
import java.util.ResourceBundle;

import static lk.ijse.Model.EmployeeModel.getNextOrderId;

public class EmployeeFormController implements Initializable {

    @FXML
    private JFXButton choosebtn;

    @FXML
    private JFXButton clearBtn;

    @FXML
    private JFXComboBox<String > cmpGender;

    @FXML
    private JFXComboBox<String> cmpPos;

    @FXML
    private DatePicker dobpick;

    @FXML
    private TextField empaddtxt;

    @FXML
    private TextField empconttxt;

    @FXML
    private TextField empftxt;

    @FXML
    private TextField empidtxt;

    @FXML
    private TextField empltxt;

    @FXML
    private Label empnxtlbl;

    @FXML
    private TextField emppostxt;

    @FXML
    private DatePicker jdaypick;

    @FXML
    private Label newemplbl;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private JFXButton brbtn;

    @FXML
    private AnchorPane xxx;

    @FXML
    private ImageView imgView;

    private static EmployeeTM emp1;


    private File file;

    private  void loadGender(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = new ArrayList<String>();
        codes.add("Male");
        codes.add("Female");
        for (String code : codes) {
            obList.add(code);
        }
        cmpGender.setItems(obList);
    }


    private void loadPosition() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PositionModel.loadPos();

            for (String code : codes) {
                obList.add(code);
            }
            cmpPos.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }

    }

    @FXML
    void clearPressed(ActionEvent event) {
        clear();
    }

    private void clear(){
        empidtxt.setText("");
        empaddtxt.setText("");
        dobpick.setValue(null);
        jdaypick.setValue(null);
        empltxt.setText("");
        empftxt.setText("");
        empconttxt.setText("");
        emppostxt.setText("");
        cmpPos.getSelectionModel().clearSelection();
        cmpPos.setValue(null);
        cmpGender.getSelectionModel().clearSelection();
        cmpGender.setValue(null);
        imgView.setImage(new Image("/assests/image/manager.png"));

    }

    @FXML
    void savePressed(ActionEvent event) throws SQLException, IOException {

            if (saveBtn.getText().equals("Save")) {
                    if(!(empidtxt.getText().isEmpty())) {
                        if(!(cmpPos.getValue().isEmpty())) {
                            if(!(empconttxt.getText().isEmpty())){
                                String id = empidtxt.getText();
                                String name = empftxt.getText();
                                String lname = empltxt.getText();
                                String address = empaddtxt.getText();
                                int contact = Integer.parseInt(empconttxt.getText());
                                java.util.Date date = java.util.Date.from(dobpick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                java.sql.Date date1 = new java.sql.Date(date.getTime());
                                java.util.Date datee = java.util.Date.from(jdaypick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                java.sql.Date date2 = new java.sql.Date(datee.getTime());
                                String pos = emppostxt.getText();
                                String gender = cmpGender.getValue();

                                Employee employee = new Employee(id, name, lname, address, contact, date1, date2, gender,pos,file);
                                try {
                                    boolean isSaved = EmployeeModel.save(employee);
                                    if (isSaved) {
                                        clear();
                                        Notification.notice(new Image("/assests/image/correct.png"), "Employee enrolment", "Enrollment Successfull!");
                                    }
                                } catch (SQLException | FileNotFoundException e) {
                                    Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Enrollment Unsuccessful!");
                                }
                            }else{
                                Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Please add a position!");
                            }
                        }else{
                            Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Please add a position!");
                        }
                    }else{
                        Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Please add a valid id!");
                    }
            }

            if (saveBtn.getText().equals("Update")) {
                if(!(empidtxt.getText().isEmpty())) {
                    if(!(cmpPos.getValue().isEmpty())) {
                        if(!(empconttxt.getText().isEmpty())){
                            if(file!=null){
                                String id = empidtxt.getText();
                                String name = empftxt.getText();
                                String lname = empltxt.getText();
                                String address = empaddtxt.getText();
                                int contact = Integer.parseInt(empconttxt.getText());
                                java.util.Date date = java.util.Date.from(dobpick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                java.sql.Date date1 = new java.sql.Date(date.getTime());
                                java.util.Date datee = java.util.Date.from(jdaypick.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                                java.sql.Date date2 = new java.sql.Date(datee.getTime());
                                String pos = emppostxt.getText();
                                String gender = cmpGender.getValue();

                                Employee employee = new Employee(id, name, lname, address, contact, date1, date2,gender,pos,file);
                                try {
                                    boolean isUpdated = EmployeeModel.update(employee);
                                    if (isUpdated) {
                                        clear();
                                        Notification.notice(new Image("/assests/image/correct.png"), "Employee Update", "Update Successfull!");
                                    }
                                } catch (SQLException | FileNotFoundException e) {
                                    Notification.notice(new Image("/assests/image/wrong.png"), "Employee Update", "Update Unsuccessful!");
                                }


                            }else{
                                Notification.notice(new Image("/assests/image/wrong.png"), "Employee Update", "Update Image!");
                            }

                        }else{
                            Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Please add a position!");
                        }
                    }else{
                        Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Please add a position!");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/wrong.png"), "Employee enrolment", "Please add a valid id!");
                }
            }
        }

    @FXML
    void genPressed(ActionEvent event){

        }

    @FXML
    void posPressed(ActionEvent event){
        String pos = cmpPos.getSelectionModel().getSelectedItem();
        try {
            if(!(pos ==null)){
                Position position = PositionModel.getPos(pos);
                emppostxt.setText(position.getPositionId());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void conTyped(KeyEvent event) {

            if(!(empidtxt.getText().isEmpty())){
                if(empconttxt.getText().matches("^(\\d+)$")){
                    if(empconttxt.getText().length()<10){

                    }else{
                        empconttxt.setText("");
                        Notification.notice(new Image("/assests/image/exclaim.png"),"Employee Alert","10 Digits Only");
                    }
                }else{
                    empconttxt.setText("");
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Employee Alert","Digits Only");
                }

            }else{
                empconttxt.setText("");
                Notification.notice(new Image("/assests/image/exclaim.png"),"Employee Error","Please enter all info");
            }


    }

    @FXML
    void empidTyped(KeyEvent event) {
        if(empidtxt.getText().length()>6){
            empidtxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Employee Alert","\"EM####\" and 4digitsonlyOnly");
        }
    }

    @FXML
    void btOnAction(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Pic");
        fileChooser.setInitialDirectory(new File("C:\\Users\\HP\\Documents\\employee"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Images","*.jpg","*.png"));
        file = fileChooser.showOpenDialog(imgView.getScene().getWindow());
        if(file!=null){
            Image img = new Image(file.getPath(),210,285,true,true);
            imgView.setImage(img);
            imgView.setFitHeight(285);
            imgView.setFitWidth(210);

        }

    }

    public static void updateinfo(EmployeeTM emp){
       emp1 = emp;

    }

    private void setdetail() throws SQLException, IOException {
        if (!(emp1 == null)) {
            empidtxt.setText(emp1.getId());
            empidtxt.setEditable(false);
            emppostxt.setText(emp1.getPosition());
            Position position = PositionModel.getPos2(emp1.getPosition());
            cmpPos.setValue(position.getPosName());
            Employee emp =EmployeeModel.getPosition(emp1.getId());
            cmpGender.setValue(emp.getGender());
            emppostxt.setEditable(false);
            empftxt.setText(emp1.getFname());
            empltxt.setText(emp1.getLname());
            empconttxt.setText(String.valueOf(emp1.getContact()));
            empaddtxt.setText(emp1.getAddress());
            dobpick.setValue(emp1.getDob().toLocalDate());
            jdaypick.setValue(emp1.getJoinedDate().toLocalDate());
            empnxtlbl.setVisible(false);
            newemplbl.setVisible(false);
            Image img = EmployeeModel.settt(emp1.getId());
            imgView.setImage(img);
            imgView.setFitHeight(285);
            imgView.setFitWidth(210);
            saveBtn.setText("Update");
        }else{
            empidtxt.setText(empnxtlbl.getText());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            emppostxt.setEditable(false);
            loadGender();
            loadPosition();
            empnxtlbl.setText(EmployeeModel.getNextOrderId()+"");
            setdetail();
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }

    }
}
