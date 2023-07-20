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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import lk.ijse.Model.EmployeeModel;
import lk.ijse.Model.ItemModel;
import lk.ijse.Model.PositionModel;
import lk.ijse.dto.Employee;
import lk.ijse.dto.Item;
import lk.ijse.dto.Position;
import lk.ijse.dto.SaleType;
import lk.ijse.dto.TM.EmployeeTM;
import lk.ijse.dto.TM.ItemTM;
import lk.ijse.util.Notification;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ItemFormController implements Initializable {

    @FXML
    private JFXButton clearBtn;

    @FXML
    private JFXComboBox<String> cmbtype;

    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<?, ?> colBase;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colRet;

    @FXML
    private TableView<ItemTM> itemtbl;

    @FXML
    private Label itmgrtlbl;

    @FXML
    private Label typelbl;

    @FXML
    private JFXButton itmsvbtn;

    @FXML
    private JFXButton reloadbtn;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtUnitPrice2;

    @FXML
    private TextField txtUnitPrice3;

    private static final double BUTTON_HEIGHT = 40;

    ObservableList<ItemTM> obList = FXCollections.observableArrayList();

    @FXML
    void reloadClicked(MouseEvent event) {
        refresh();
        clear();
    }

    private void refresh(){
        try {
            obList.clear();
            List<Item> cusList = ItemModel.getAll();

            for (Item employee : cusList) {
                obList.add(new ItemTM(
                        employee.getCode(),
                        employee.getDescription(),
                        employee.getQtyOnHand(),
                        employee.getUnitPrice(),
                        employee.getRetail(),
                        employee.getBase()
                ));
            }
            itemtbl.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void clearPressed(ActionEvent event) {
        clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

                if (itmsvbtn.getText().equals("Save")) {
                    if(!(txtCode.getText().equals(""))) {
                        if(!cmbtype.getValue().isEmpty()){
                    String code = txtCode.getText();
                    String description = txtDescription.getText();
                    double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
                    double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                    double RetailPrice = Double.parseDouble(txtUnitPrice2.getText());
                    double Base = Double.parseDouble(txtUnitPrice3.getText());
                    String type = typelbl.getText();

                    Item item = new Item(code, description, qtyOnHand, unitPrice,RetailPrice,Base,type);
                    if(!(item==null)){
                            try {
                                boolean isSaved = ItemModel.save(item);
                                if (isSaved) {
                                    itmgrtlbl.setText(ItemModel.getNextOrderId());
                                    refresh();
                                    clear();
                                    Notification.notice(new Image("/assests/image/correct.png"), "Item prompt", "Item Saved");
                                }
                            } catch (SQLException e) {
                                Notification.notice(new Image("/assests/image/wrong.png"), "Item prompt", "Item Doesnot saved");
                            }
                    }else{
                        Notification.notice(new Image("/assests/image/wrong.png"), "Item prompt", "Item Already exists");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"), "Item prompt", "Please fill the form");
                }

            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"), "Item prompt", "Please fill the form");
            }

                }else{

                        String code = txtCode.getText();
                        String description = txtDescription.getText();
                        double qtyOnHand = Double.parseDouble(txtQtyOnHand.getText());
                        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
                        double RetailPrice = Double.parseDouble(txtUnitPrice2.getText());
                        double Base = Double.parseDouble(txtUnitPrice3.getText());
                        String type = typelbl.getText();

                        Item item = new Item(code, description, qtyOnHand, unitPrice,RetailPrice,Base,type);

                        try {
                            boolean isSaved = ItemModel.update(item);
                            if (isSaved) {
                                itmsvbtn.setText("Save");
                                txtCode.setEditable(true);
                                refresh();
                                clear();
                                Notification.notice(new Image("/assests/image/correct.png"), "Item prompt", "Item Updated");
                            }
                        } catch (SQLException e) {
                            Notification.notice(new Image("/assests/image/wrong.png"), "Item prompt", "Item Doesnot Updated");
                        }

                }



        }

    @FXML
    void prcTyped(KeyEvent event) {
        if(!txtUnitPrice.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtUnitPrice.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void prc2Typed(KeyEvent event) {
        if(!txtUnitPrice2.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtUnitPrice2.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void prc3Typed(KeyEvent event) {
        if(!txtUnitPrice3.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtUnitPrice3.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void qtyTyped(KeyEvent event) {
        if(!txtQtyOnHand.getText().matches("^(\\d+\\.?\\d*|\\.\\d+)$")){
            txtQtyOnHand.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","Please enter decimal number");
        }
    }

    @FXML
    void codeSearchOnAction(ActionEvent event) {

            try {
                String code = txtCode.getText();
                if(code.length()==6){
                    Item item = ItemModel.search(code);
                    if(!(item ==null)){
                        typelbl.setText(item.getType());
                        cmbtype.setValue(ItemModel.getname(item.getType()).getTyname());
                        txtCode.setText(item.getCode());
                        txtDescription.setText(item.getDescription());
                        txtUnitPrice.setText((item.getUnitPrice())+"");
                        txtUnitPrice2.setText(item.getRetail()+"");
                        txtUnitPrice3.setText(item.getBase()+"");
                        txtQtyOnHand.setText((item.getQtyOnHand())+"");
                        txtCode.setEditable(false);
                        txtCode.requestFocus();
                        itmsvbtn.setText("Update");

                        Notification.notice(new Image("/assests/image/correct.png"),"Item prompt","Item Retrieved");
                    }else{
                        txtCode.requestFocus();
                        Notification.notice(new Image("/assests/image/exclaim.png"),"Item prompt","Item not Found");
                    }
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Item prompt","Character length must be 6 Digits");
                }


            } catch (SQLException e) {
                Notification.notice(new Image("/assests/image/wrong.png"),"Item prompt","Item Doesnot Retrieved");
            }
        }

    private void setCellValueFactory() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("name"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("Uprc"));
        colRet.setCellValueFactory(new PropertyValueFactory<>("Retail"));
        colBase.setCellValueFactory(new PropertyValueFactory<>("Base"));

        Callback<TableColumn<ItemTM,String>, TableCell<ItemTM,String>> cellfactory = employeeTMStringTableColumn -> {
            final TableCell<ItemTM,String> cell = new TableCell<ItemTM,String>(){

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
                             ItemTM tm = getTableView().getItems().get(getIndex());
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
        colAction.setCellFactory(cellfactory);

    }

    private void getAll() {

        try {

            List<Item> cusList = ItemModel.getAll();

            for (Item item : cusList) {
                obList.add(new ItemTM(
                        item.getCode(),
                        item.getDescription(),
                        item.getQtyOnHand(),
                        item.getUnitPrice(),
                        item.getRetail(),
                        item.getBase()
                ));
            }

            itemtbl.setItems(obList);
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

            int index = itemtbl.getSelectionModel().getSelectedIndex();
            if(index>=0){
                ItemTM em = itemtbl.getSelectionModel().getSelectedItem();
                boolean isOkay = ItemModel.delete(em.getCode());
                if(isOkay){
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Deleted");
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Did not Deleted");
                }
                itemtbl.refresh();
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
            }
        }
    }

    @FXML
    void codePressed(KeyEvent event){
        if(txtCode.getText().length()>6){
            txtCode.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item Error","It#### and only contain 4digits");
        }
    }

    @FXML
    void typeOnAction(ActionEvent event) {
        String pos = cmbtype.getSelectionModel().getSelectedItem();
        try {
            if(!(pos ==null)){
                SaleType tp = PositionModel.gettypePos(pos);
                typelbl.setText(tp.getTypeid());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    void rowClicked(MouseEvent event) throws SQLException {
        if (itemtbl.getSelectionModel().getSelectedItem() != null) {
            ItemTM em = itemtbl.getSelectionModel().getSelectedItem();
            Item it = ItemModel.search(em.getCode());
            SaleType st = ItemModel.getType(it.getType());
            txtCode.setText(em.getCode());
            cmbtype.setValue(st.getTyname());
            typelbl.setText(it.getType());
            txtDescription.setText(em.getName());
            txtQtyOnHand.setText(String.valueOf(em.getQty()));
            txtUnitPrice.setText(String.valueOf(em.getUprc()));
            txtUnitPrice2.setText(String.valueOf(em.getRetail()));
            txtUnitPrice3.setText(String.valueOf(em.getBase()));

            itmsvbtn.setText("Update");

        }
        }

    private void loadtype(){
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> codes = PositionModel.loadtype();

            for (String code : codes) {
                obList.add(code);
            }
            cmbtype.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void clear(){
        txtCode.setText("");
        txtDescription.setText("");
        txtQtyOnHand.setText("");
        txtUnitPrice.setText("");
        txtUnitPrice2.setText("");
        txtUnitPrice3.setText("");
        itmsvbtn.setText("Save");
        txtCode.setEditable(true);
        txtDescription.setEditable(true);
        cmbtype.getSelectionModel().clearSelection();
        cmbtype.setValue(null);
        typelbl.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            itmgrtlbl.setText(ItemModel.getNextOrderId());
            setCellValueFactory();
            getAll();
            loadtype();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
