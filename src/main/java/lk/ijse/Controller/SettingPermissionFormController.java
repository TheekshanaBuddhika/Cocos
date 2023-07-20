package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import lk.ijse.Model.ItemModel;
import lk.ijse.Model.PositionModel;
import lk.ijse.dto.Item;
import lk.ijse.dto.Position;
import lk.ijse.dto.TM.PositionTM;
import lk.ijse.util.Notification;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SettingPermissionFormController implements Initializable {

    @FXML
    private TextField basesaltxt;

    @FXML
    private JFXComboBox<String> cmbperm;

    @FXML
    private TableColumn colDel;

    @FXML
    private TableColumn<?, ?> colPosName;

    @FXML
    private TableColumn<?, ?> colPrem;

    @FXML
    private JFXButton delbtn;

    @FXML
    private AnchorPane posPane;

    @FXML
    private TextField posidtxt;

    @FXML
    private TextField posnametxt;

    @FXML
    private TableView<PositionTM> postbl;

    @FXML
    private JFXButton sbtn;

    @FXML
    private Label usnmlbl;

    @FXML
    private Label nxposlbl;

    private static final double BUTTON_HEIGHT = 40;

    ObservableList<PositionTM> obList = FXCollections.observableArrayList();


    @FXML
    void clOnAction(ActionEvent event){
        clear();
    }

    @FXML
    void sOnAction(ActionEvent event){
        if (sbtn.getText().equals("Save")) {
            if(!(posidtxt.getText().equals(""))) {
                if(!cmbperm.getValue().isEmpty()){
                    String code = posidtxt.getText();
                    String description = posnametxt.getText();
                    double qtyOnHand = Double.parseDouble(basesaltxt.getText());
                    String type = cmbperm.getValue();

                    Position item = new Position(code,description,qtyOnHand,type);
                    if(!(item==null)){
                        try {
                            boolean isSaved = PositionModel.save(item);
                            if (isSaved) {
                                nxposlbl.setText(PositionModel.getNextOrderId());
                                refresh();
                                clear();
                                Notification.notice(new Image("/assests/image/correct.png"), "Position prompt", "Position Saved");
                            }
                        } catch (SQLException e) {
                            Notification.notice(new Image("/assests/image/wrong.png"), "Position prompt", "Position Doesnot saved");
                        }
                    }else{
                        Notification.notice(new Image("/assests/image/wrong.png"), "Position prompt", "Position Already exists");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"), "Position prompt", "Please fill the form");
                }

            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"), "Position prompt", "Please fill the form");
            }

        }else{

            String code = posidtxt.getText();
            String description = posnametxt.getText();
            double qtyOnHand = Double.parseDouble(basesaltxt.getText());
            String type = cmbperm.getValue();

            Position item = new Position(code,description,qtyOnHand,type);
            try {
                boolean isSaved = PositionModel.update(item);
                if (isSaved) {
                    sbtn.setText("Save");
                    posidtxt.setEditable(true);
                    refresh();
                    clear();
                    Notification.notice(new Image("/assests/image/correct.png"), "Position prompt", "Position Updated");
                }
            } catch (SQLException e) {
                Notification.notice(new Image("/assests/image/wrong.png"), "Position prompt", "Position Doesnot Updated");
            }

        }


    }

    @FXML
    void poTyped(KeyEvent event) {
        if(posidtxt.getText().length()>6){
            posidtxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Position Error","P##### and only contain 5digits");
        }
    }

    @FXML
    void postblClicked(MouseEvent event) throws SQLException {

        if (postbl.getSelectionModel().getSelectedItem() != null) {
            PositionTM em = postbl.getSelectionModel().getSelectedItem();
            Position it = PositionModel.getPos(em.getPosName());
            posidtxt.setText(it.getPositionId());
            cmbperm.setValue(it.getPermision());
            posnametxt.setText(it.getPosName());
            basesaltxt.setText(String.valueOf(it.getSalary()));
            posidtxt.setEditable(false);
            sbtn.setText("Update");

        }

    }

    @FXML
    void slTyPED(KeyEvent event){
        if(!basesaltxt.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            basesaltxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Positon Error","Please enter decimal number");
        }
    }

    private void setCellValueFactory() {
        colPosName.setCellValueFactory(new PropertyValueFactory<>("posName"));
        colPrem.setCellValueFactory(new PropertyValueFactory<>("premission"));

        Callback<TableColumn<PositionTM,String>, TableCell<PositionTM,String>> cellfactory = employeeTMStringTableColumn -> {
            final TableCell<PositionTM,String> cell = new TableCell<PositionTM,String>(){
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
                            PositionTM tm = getTableView().getItems().get(getIndex());
                            try {
                                altbtn();
                                refresh();
                                clear();
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
        colDel.setCellFactory(cellfactory);

    }


    private void getAll() {

        try {

            List<Position> cusList = PositionModel.getAll();

            for (Position item : cusList) {
                obList.add(new PositionTM(
                        item.getPosName(),
                        item.getPermision()
                ));
            }

            postbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
    private void clear(){
        posidtxt.setText("");
        basesaltxt.setText("");
        posnametxt.setText("");
        posidtxt.setEditable(true);
        cmbperm.getSelectionModel().clearSelection();
        cmbperm.setValue(null);
    }
    private void altbtn() throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {

            int index = postbl.getSelectionModel().getSelectedIndex();
            if(index>=0){
                PositionTM em = postbl.getSelectionModel().getSelectedItem();
                boolean isOkay = PositionModel.delete(em.getPosName());
                if(isOkay){
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Deleted");
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Did not Deleted");
                }
                postbl.refresh();
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
            }
        }
    }
    private void refresh(){
            obList.clear();
           getAll();
    }
    private  void loadPrem(){
        ObservableList<String> obList = FXCollections.observableArrayList();
        List<String> codes = new ArrayList<String>();
        codes.add("Granted");
        codes.add("Denied");
        for (String code : codes) {
            obList.add(code);
        }
        cmbperm.setItems(obList);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            setCellValueFactory();
            getAll();
            loadPrem();
            nxposlbl.setText(PositionModel.getNextOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
