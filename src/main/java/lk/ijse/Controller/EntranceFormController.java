package lk.ijse.Controller;


import animatefx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.util.Stageset;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class EntranceFormController implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private AnchorPane paane;

    @FXML
    private AnchorPane paanee;

    private volatile boolean stop =false;

    @FXML
    void paneClicked(MouseEvent event) throws IOException {
        stop = true;
        new Stageset().getPane("/View/Login_form.fxml",paanee);

    }

    @FXML
    void tlblmouse(MouseEvent event){
        new Pulse(time).play();
    }

    @FXML
    void tlblmouse2(MouseEvent event){
        new Pulse(date).play();
    }

    private void Timenow(){
        Thread thread = new Thread(()->{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while(!stop){
                try {
                    Thread.sleep(1);
                }catch (Exception e){
                    e.printStackTrace();
                }
                final String timenow = simpleDateFormat.format(new Date());
                Platform.runLater(()->{
                    time.setText(timenow);
                });
            }
        });
        thread.start();
    }

    private void Datenow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, MMMM dd,yyyy");
        String datemow = simpleDateFormat.format(new Date());
        date.setText(datemow);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new ZoomIn(time).play();
        new ZoomIn(date).play();
            Timenow();
            Datenow();


    }
}


