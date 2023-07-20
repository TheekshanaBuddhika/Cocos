package lk.ijse.util;

import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lk.ijse.Controller.DashboardFormController;

import java.io.IOException;

public class Stageset {
    public void getStage(String fxml, AnchorPane root, String title , Image img) throws IOException {

        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxml)));
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.getIcons().add(img);
        stage.centerOnScreen();
    }

    public void getPane(String fxml, AnchorPane mainPane) throws IOException {
        Pane registerPane = FXMLLoader.load(getClass().getResource(fxml));
        mainPane.getChildren().clear();
        mainPane.getChildren().setAll(registerPane);

    }

}
