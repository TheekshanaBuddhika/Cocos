import animatefx.animation.FadeIn;
import animatefx.animation.Flash;
import animatefx.animation.RubberBand;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.util.Service;

public class Launcher extends Application {
    double x,y =0;
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Entrance_form.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> { x = event.getSceneX();y = event.getSceneY(); });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
        stage.setScene(new Scene(root));
        stage.setTitle("DEDU");
        stage.getIcons().add(new Image("/assests/image/logo.png"));
        stage.centerOnScreen();
        stage.show();

        new FadeIn(root).play();
    }


}
