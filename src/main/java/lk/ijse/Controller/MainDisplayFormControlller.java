package lk.ijse.Controller;

import animatefx.animation.Pulse;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.Model.Production;
import lk.ijse.Model.SalesModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MainDisplayFormControlller implements Initializable {


    @FXML
    private Label Dslbl;

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
    private ImageView imgview;

    @FXML
    private ImageView imgview1;

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

    private int counttt;

    private void sldshow(){
        ArrayList<Image> images = new ArrayList<Image>();
        images.add(new Image("/assests/image/img1.png"));
        images.add(new Image("/assests/image/img2.png"));
        images.add(new Image("/assests/image/img3.png"));
        images.add(new Image("/assests/image/img4.png"));
        images.add(new Image("/assests/image/img5.png"));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event ->{
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sldshow();
        loaddtl();
    }
}
