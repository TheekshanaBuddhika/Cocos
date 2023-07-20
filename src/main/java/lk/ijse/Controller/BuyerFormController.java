package lk.ijse.Controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import lk.ijse.Model.BuyerModel;
import lk.ijse.Model.SellerModel;
import lk.ijse.dto.Buyer;
import lk.ijse.dto.Seller;
import lk.ijse.dto.TM.BuyerTM;
import lk.ijse.dto.TM.SellerTM;
import lk.ijse.util.Notification;
import lk.ijse.util.Stageset;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static lk.ijse.Controller.DashboardFormController.useName;

public class BuyerFormController implements Initializable {

    @FXML
    private AnchorPane buyerPane;

    @FXML
    private Label buylbl;

    @FXML
    private JFXButton clearBtn;

    @FXML
    private TableColumn<?, ?> colAdd;

    @FXML
    private TableColumn<?, ?> colD;

    @FXML
    private TableColumn colDelete;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colTperiod;

    @FXML
    private TableColumn<?, ?> colsell;

    @FXML
    private JFXButton reloadbtn;

    @FXML
    private JFXButton saveBtn;

    @FXML
    private TableView<BuyerTM> selltable;

    @FXML
    private TextField supTimetxt;

    @FXML
    private TextField supaddtxt;

    @FXML
    private DatePicker supdatetxt;

    @FXML
    private TextField supidtxt;

    @FXML
    private TextField supnametxt;

    @FXML
    private Label buyidlbl;

    private static final double BUTTON_HEIGHT = 40;

    ObservableList<BuyerTM> obList = FXCollections.observableArrayList();

    @FXML
    void buylblClicked(MouseEvent event) throws IOException {
        new Stageset().getPane("/View/Seller_form.fxml",buyerPane);
    }

    @FXML
    void clearPressed(ActionEvent event) {
      clear();
    }

    private void clear(){
        supidtxt.setEditable(true);
        supidtxt.setText("");
        supnametxt.setText("");
        supdatetxt.setValue(null);
        supTimetxt.setText("");
        supaddtxt.setText("");
        saveBtn.setText("Save");
    }
    @FXML
    void reloadPressed(ActionEvent event) {
        refresh();
        clear();
    }
    @FXML
    void supTyPed(KeyEvent event) {

        if(!(supidtxt.getText().length() <7)){
            supidtxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Item prompt","Character length must be 6 Digits");
        }

    }
    @FXML
    void savePressed(ActionEvent event) {
        if(!(supidtxt.getText().equals(""))){
            if(saveBtn.getText().equals("Save")){
                String id = supidtxt.getText();
                String name = supnametxt.getText();
                String address = supaddtxt.getText();
                java.util.Date date = java.util.Date.from(supdatetxt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date date1 = new java.sql.Date(date.getTime());
                int time = 0;
                if(supTimetxt.getText().isEmpty()){
                    time = 0;
                }else{
                    time = Integer.parseInt(supTimetxt.getText());
                }

                Buyer buyer = new Buyer(id,name,address,date1,time,useName);

                try {
                    boolean isSaved = BuyerModel.save(buyer);
                    if (isSaved) {
                        buyidlbl.setText(BuyerModel.getNextOrderId());
                        refresh();
                        clear();
                        Notification.notice(new Image("/assests/image/correct.png"),"Supplier prompt","Supplier Saved");
                    }
                } catch (SQLException e) {
                    Notification.notice(new Image("/assests/image/wrong.png"),"Supplier prompt","Supplier Doesnot saved");
                }
            }
            if(saveBtn.getText().equals("Update")) {

                String id = supidtxt.getText();
                String name = supnametxt.getText();
                String address = supaddtxt.getText();
                java.util.Date date = java.util.Date.from(supdatetxt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
                java.sql.Date date1 = new java.sql.Date(date.getTime());
                int time = 0;
                if(supTimetxt.getText().isEmpty()){
                    time = 0;
                }else{
                    time = Integer.parseInt(supTimetxt.getText());
                }

                Buyer buyer = new Buyer(id,name,address,date1,time,useName);

                try {

                    boolean isSaved = BuyerModel.update(buyer);
                    if (isSaved) {
                        saveBtn.setText("Save");
                        supidtxt.setEditable(true);
                        refresh();
                        clear();
                        Notification.notice(new Image("/assests/image/correct.png"),"Supplier prompt","Supplier Updated");
                    }
                } catch (SQLException e) {
                    Notification.notice(new Image("/assests/image/wrong.png"),"Supplier prompt","Supplier Doesnot Updated");
                }
            }
        }

    }

    @FXML
    void supPressed(ActionEvent event) {
        try {
            String code = supidtxt.getText();
                Buyer seller = BuyerModel.search(code);
                if(!(seller ==null)){
                    supidtxt.setEditable(false);
                    supidtxt.setText(seller.getId());
                    supnametxt.setText(seller.getName());
                    supaddtxt.setText(seller.getAddress());
                    supTimetxt.setText((seller.getPeriod())+"");
                    supdatetxt.setValue(seller.getDate().toLocalDate());
                    saveBtn.setText("Update");

                    Notification.notice(new Image("/assests/image/correct.png"),"Item prompt","Seller info Retrieved");
                }else{
                    supnametxt.requestFocus();
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Item prompt","Seller info not Found");
                }

        } catch (SQLException e) {
            Notification.notice(new Image("/assests/image/wrong.png"),"Item prompt","Seller info not Found");
        }
    }

    private void getAll() {

        try {
            List<Buyer> cusList = BuyerModel.getAll();

            for (Buyer seller : cusList) {
                obList.add(new BuyerTM(
                        seller.getId(),
                        seller.getName(),
                        seller.getAddress(),
                        seller.getDate(),
                        seller.getPeriod()
                ));
            }
            selltable.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    private void setCellValueFactory() {
        colsell.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        colD.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTperiod.setCellValueFactory(new PropertyValueFactory<>("period"));

        Callback<TableColumn<BuyerTM,String>, TableCell<BuyerTM,String>> cellfactory = employeeTMStringTableColumn -> {
            final TableCell<BuyerTM,String> cell = new TableCell<BuyerTM,String>(){

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
                            BuyerTM tm = getTableView().getItems().get(getIndex());
                            try {
                                altbtn();
                                clear();
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
    }

    private void altbtn() throws SQLException {
        ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

        if (result.orElse(no) == yes) {

            int index = selltable.getSelectionModel().getSelectedIndex();
            if(index>=0){
                BuyerTM em = selltable.getSelectionModel().getSelectedItem();
                boolean isOkay = BuyerModel.delete(em.getId());
                if(isOkay){
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Deleted");
                }else{
                    Notification.notice(new Image("/assests/image/exclaim.png"),"Table Prompt","Employee Did not Deleted");
                }
                selltable.refresh();
            }else{
                Notification.notice(new Image("/assests/image/exclaim.png"),"Table Error","Please select the row");
            }
        }
    }

    @FXML
    void timeTyped(KeyEvent event) {
        if(!supTimetxt.getText().matches("^(\\d+)$")){
            supTimetxt.setText("");
            Notification.notice(new Image("/assests/image/exclaim.png"),"Buyer Error","Only digits approved");
        }
    }

    private void refresh(){
        try {
            obList.clear();
            List<Buyer> cusList = BuyerModel.getAll();

            for (Buyer seller : cusList) {
                obList.add(new BuyerTM(
                        seller.getId(),
                        seller.getName(),
                        seller.getAddress(),
                        seller.getDate(),
                        seller.getPeriod()
                ));
            }
            selltable.setItems(obList);
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void rowClicked(MouseEvent event)  {
        if (selltable.getSelectionModel().getSelectedItem() != null) {
            BuyerTM em = selltable.getSelectionModel().getSelectedItem();
            supidtxt.setText(em.getId());
            supnametxt.setText(em.getName());
            supaddtxt.setText(em.getAddress());
            supTimetxt.setText(String.valueOf(em.getPeriod()));
            supdatetxt.setValue(LocalDate.parse(String.valueOf(em.getDate())));

            saveBtn.setText("Update");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setCellValueFactory();
            getAll();
            buyidlbl.setText(BuyerModel.getNextOrderId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
