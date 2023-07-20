package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Employee;
import lk.ijse.dto.TM.EmployeeTM;
import lk.ijse.util.Notification;
import lk.ijse.util.Service;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Getter
@Setter

public class EmployeeViewFormController implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private TableColumn<?, ?> colCon;

    @FXML
    private TableColumn  colDelete;

    @FXML
    private TableColumn  colEdit;

    @FXML
    private TableColumn<?, ?> colFname;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colDob;


    @FXML
    private TableColumn<?, ?> colJdate;

    @FXML
    private TableColumn<?, ?> colLname;

    @FXML
    private TableColumn<?, ?> colPos;

    @FXML
    private TableColumn<?, ?> coladdress;

    @FXML
    private TableView<EmployeeTM> empTable;

    @FXML
    private JFXButton reloadbtn;

    @FXML
    private AnchorPane empPane;

    private static final double BUTTON_HEIGHT = 40;

    ObservableList<EmployeeTM> obList = FXCollections.observableArrayList();

    @FXML
    void addPressed(ActionEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/View/employee_form.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Employee Manage");
        stage.getIcons().add(new Image("/assests/image/logo.png"));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(empPane.getScene().getWindow());
        stage.show();
    }

    @FXML
    void reloadPressed(ActionEvent event) throws SQLException {
      refresh();
    }

    private void refresh(){
        try {
            obList.clear();
            List<Employee> cusList = EmployeeModel.getAll();

            for (Employee employee : cusList) {
                obList.add(new EmployeeTM(
                        employee.getEmpid(),
                        employee.getFirstName(),
                        employee.getSecName(),
                        employee.getAddress(),
                        employee.getContact(),
                        employee.getBirthdate(),
                        employee.getJoinday(),
                        employee.getPosId()
                ));
            }
            empTable.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colFname.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        colLname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCon.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("Dob"));
        colJdate.setCellValueFactory(new PropertyValueFactory<>("joinedDate"));
        colPos.setCellValueFactory(new PropertyValueFactory<>("position"));

        Callback<TableColumn<EmployeeTM,String>,TableCell<EmployeeTM,String>> cellfactory = employeeTMStringTableColumn -> {
            final TableCell<EmployeeTM,String> cell = new TableCell<EmployeeTM,String>(){

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else{

                        ImageView view = new ImageView(new Image("/assests/image/icons8-delete-100.png"));
                        view.setFitHeight(BUTTON_HEIGHT);
                        view.setPreserveRatio(true);

                        final JFXButton ebtn = new JFXButton("",view);
                        ebtn.setPrefSize(Region.USE_COMPUTED_SIZE,BUTTON_HEIGHT);
                        ebtn.setOnAction(event -> {
                            EmployeeTM tm = getTableView().getItems().get(getIndex());
                            try {
                                altbtn();
                                refresh();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        });
                        setGraphic(ebtn);
                        setText(null);

                    }
                }
            };
            return cell;
        };
        colDelete.setCellFactory(cellfactory);


        Callback<TableColumn<EmployeeTM,String>,TableCell<EmployeeTM,String>> cellfactory2 = employeeTMStringTableColumn -> {
            final TableCell<EmployeeTM,String> cell = new TableCell<EmployeeTM,String>(){

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }else{

                        ImageView view = new ImageView(new Image("/assests/image/icons8-edit-100.png"));
                        view.setFitHeight(BUTTON_HEIGHT);
                        view.setPreserveRatio(true);

                        final JFXButton ebtn = new JFXButton("",view);
                        ebtn.setPrefSize(Region.USE_COMPUTED_SIZE,BUTTON_HEIGHT);
                        ebtn.setOnAction(event -> {
                            int index = empTable.getSelectionModel().getSelectedIndex();
                            if(index>=0) {
                                EmployeeTM em = empTable.getSelectionModel().getSelectedItem();
                                EmployeeFormController.updateinfo(em);
                                Parent root2 = null;
                                try {
                                    root2 = FXMLLoader.load(getClass().getResource("/View/employee_form.fxml"));

                                    Scene scene = new Scene(root2);
                                    Stage stage = new Stage();
                                    stage.setScene(scene);
                                    stage.setTitle("Employee Manage");
                                    stage.getIcons().add(new Image("/assests/image/logo.png"));
                                    stage.initModality(Modality.WINDOW_MODAL);
                                    stage.initOwner(empPane.getScene().getWindow());
                                    stage.show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
                            }
                        });
                        setGraphic(ebtn);
                        setText(null);

                    }
                }
            };
            return cell;
        };
        colEdit.setCellFactory(cellfactory2);


    }

    private void getAll() {

        try {

            List<Employee> cusList = EmployeeModel.getAll();

            for (Employee employee : cusList) {
                obList.add(new EmployeeTM(
                        employee.getEmpid(),
                        employee.getFirstName(),
                        employee.getSecName(),
                        employee.getAddress(),
                        employee.getContact(),
                        employee.getBirthdate(),
                        employee.getJoinday(),
                        employee.getPosId()
                ));
            }

            empTable.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void altbtn() throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {

            int index = empTable.getSelectionModel().getSelectedIndex();
            if(index>=0){
            EmployeeTM em = empTable.getSelectionModel().getSelectedItem();
            boolean isOkay = EmployeeModel.delete(em.getId());
            if(isOkay){
                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Deleted");
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Did not Deleted");
            }
            empTable.refresh();
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
            }
        }
    }

    @FXML
    void empOnAction(ActionEvent event) throws JRException, SQLException {

        Thread thread = new Thread(()->{
            try {
                InputStream rpt = ReportFormController.class.getResourceAsStream("/assests/report/Employee2.jrxml");
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
        setCellValueFactory();
        getAll();
    }
}
