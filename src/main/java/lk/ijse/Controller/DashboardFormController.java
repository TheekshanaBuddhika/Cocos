package lk.ijse.Controller;

import animatefx.animation.Pulse;
import animatefx.animation.RotateIn;
import animatefx.animation.ZoomIn;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.Model.HistoryModel;
import lk.ijse.Model.Production;
import lk.ijse.Model.SalesModel;
import lk.ijse.util.Stageset;
import lombok.Data;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

@Data
public class DashboardFormController implements Initializable {

    public static String useName;
    @FXML
    private Label Date;

    @FXML
    private Label Dslbl;

    @FXML
    private MenuItem buyerMenu;

    @FXML
    private MenuButton contMenu;

    @FXML
    private ImageView cutbtn;

    @FXML
    private AnchorPane dISPANE;

    @FXML
    private AnchorPane dashroot;

    @FXML
    private AnchorPane displayPane;

    @FXML
    private AnchorPane drawerPane;

    @FXML
    private Label dsimg1;

    @FXML
    private ImageView dsimg2;

    @FXML
    private Pane dspane;

    @FXML
    private Label dtimg1;

    @FXML
    private ImageView dtimg2;

    @FXML
    private Label dtlbl;

    @FXML
    private Pane dtpane;

    @FXML
    private JFXButton employeebtn;

    @FXML
    private ImageView homebtn;

    @FXML
    private ImageView imgview;

    @FXML
    private ImageView imgview1;

    @FXML
    private JFXButton inventorybtn;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton menuBar;

    @FXML
    private Label mpig;

    @FXML
    private ImageView mping2;

    @FXML
    private Label mplbl;

    @FXML
    private Pane mppane;

    @FXML
    private Label mtimg;

    @FXML
    private ImageView mtimg2;

    @FXML
    private Label mtlbl;

    @FXML
    private Pane mtpane;

    @FXML
    private AnchorPane opacitiPane;

    @FXML
    private AnchorPane primaryroot;

    @FXML
    private JFXButton productionbtn;

    @FXML
    private ImageView realimg;

    @FXML
    private JFXButton reportbtn;

    @FXML
    private MenuItem retailMenu;

    @FXML
    private MenuButton salesbtn;

    @FXML
    private MenuItem sellerMenu;

    @FXML
    private JFXButton setbtn;

    @FXML
    private ImageView setimg;

    @FXML
    private MenuItem stockMenu;

    @FXML
    private MenuItem tranbtn;

    @FXML
    private Label usertag;

    @FXML
    private Label welUselbl;

    @FXML
    private AnchorPane welcomePane;

    @FXML
    private JFXButton clsbtn1;

    private int counttt;

    JFXRippler rippler = new JFXRippler(contMenu);
    JFXRippler rippler1 = new JFXRippler(salesbtn);

    @FXML
    void en1(MouseEvent event) {
        new Pulse(dspane).play();
        new Pulse(Dslbl).play();
        new Pulse(dsimg1).play();
        new Pulse(dsimg2).play();
    }

    @FXML
    void en2(MouseEvent event) {
        new Pulse(dtpane).play();
        new Pulse(dtlbl).play();
        new Pulse(dtimg1).play();
        new Pulse(dtimg2).play();
    }

    @FXML
    void en3(MouseEvent event) {
        new Pulse(mtpane).play();
        new Pulse(mtlbl).play();
        new Pulse(mtimg).play();
        new Pulse(mtimg2).play();
    }

    @FXML
    void en4(MouseEvent event) {
        new Pulse(mppane).play();
        new Pulse(mplbl).play();
        new Pulse(mpig).play();
        new Pulse(mping2).play();
    }

    @FXML
    void settingClicked(MouseEvent event) throws IOException {
        Parent root2 = FXMLLoader.load(getClass().getResource("/View/Setting_form.fxml"));
        Scene scene = new Scene(root2);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primaryroot.getScene().getWindow());
        stage.show();
    }

    @FXML
    void cutClicked(MouseEvent event) throws SQLException {

        HistoryModel.sOut(HistoryModel.logDate,HistoryModel.logTime, LocalTime.now());
        System. exit(0);
    }

    @FXML
    void empPressed(ActionEvent event) throws IOException {

        new Stageset().getPane("/View/EmployeeView_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void menuClicked(MouseEvent event) {

    }

    @FXML
    void setenter(MouseEvent event){
        new RotateIn(setimg).play();
    }

    @FXML
    void outClicked(MouseEvent event) throws IOException, SQLException {
        HistoryModel.sOut(HistoryModel.logDate,HistoryModel.logTime, LocalTime.now());
        new Stageset().getStage("/View/Login_form.fxml",primaryroot,"Dedu Pvt.ltd",new Image("/assests/image/logo.png"));
    }

    @FXML
    void clsClicked1(MouseEvent event){
        Stage stage = (Stage) clsbtn1.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void buyPressed(ActionEvent event) throws IOException {

        new Stageset().getPane("/View/Buyer_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void invrnPressed(ActionEvent event) throws IOException {

        new Stageset().getPane("/View/item_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void productionPressed(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/Daily_production_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void reportPressed(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/Report_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void salesPressed(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/place_order_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void retailPressed(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/Daily_sales_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void sellPressed(ActionEvent event) throws IOException {
        new Stageset().getPane("/View/Seller_form.fxml",dISPANE);
        transition();
    }

    @FXML
    void transactionPressed(ActionEvent event) throws IOException {

        new Stageset().getPane("/View/Transaction_form.fxml",dISPANE);
        transition();

    }

    @FXML
    void homeClicked(MouseEvent event) throws IOException {
        new Stageset().getPane("/View/Main_display_form.fxml",dISPANE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hover();
        welUselbl.setText(useName);
        usertag.setText(useName);
        Date.setText(LocalDate.now()+"");
        loaddtl();
        sldshow();

    }

    private void transition(){
        TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
        translateTransition1.setByX(-600);
        translateTransition1.play();
        translateTransition1.setOnFinished(event1 -> {
            opacitiPane.setVisible(false);
        });
    }

    private  void run(){

        rippler.setRipplerFill(Paint.valueOf("#ffffff"));
        rippler1.setRipplerFill(Paint.valueOf("#ffffff"));
        drawerPane.getChildren().addAll(rippler,rippler1);
    }

    private void hover(){
        opacitiPane.setVisible(false);

        FadeTransition fadeTransition=new FadeTransition(Duration.seconds(0.5),opacitiPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        TranslateTransition translateTransition=new TranslateTransition(Duration.seconds(0.5),drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();

        menuBar.setOnMouseClicked(event -> {

            opacitiPane.setVisible(true);
            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),opacitiPane);
            fadeTransition1.setFromValue(0);
            fadeTransition1.setToValue(0.15);
            fadeTransition1.play();

            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setByX(+600);
            translateTransition1.play();
        });

        opacitiPane.setOnMouseClicked(event -> {



            FadeTransition fadeTransition1=new FadeTransition(Duration.seconds(0.5),opacitiPane);
            fadeTransition1.setFromValue(0.15);
            fadeTransition1.setToValue(0);
            fadeTransition1.play();

            fadeTransition1.setOnFinished(event1 -> {
                opacitiPane.setVisible(false);
            });


            TranslateTransition translateTransition1=new TranslateTransition(Duration.seconds(0.5),drawerPane);
            translateTransition1.setByX(-600);
            translateTransition1.play();
        });

    }

    private void loaddtl()  {

        try {

            int count = SalesModel.getsalecount();
            Dslbl.setText(count+"");
            Double dsales  =  SalesModel.getDailytot();
            dtlbl.setText(dsales+"");
            Double msales = SalesModel.getMonthlytot();
            mtlbl.setText(msales+"");
            Double prd = Production.getProduct();
            mplbl.setText(prd+"");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void sldshow(){
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(new Image("/assests/image/img1.png"));
        images.add(new Image("/assests/image/img2.png"));
        images.add(new Image("/assests/image/img3.png"));
        images.add(new Image("/assests/image/img4.png"));
        images.add(new Image("/assests/image/img5.png"));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5),event ->{
            imgview.setImage(images.get(counttt));
            imgview1.setImage(images.get(counttt));
            counttt++;
            if(counttt==5){
                counttt= 0;
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}









