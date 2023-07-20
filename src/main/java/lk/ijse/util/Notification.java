package lk.ijse.util;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.awt.*;

public class Notification {
    public static void notice(Image img, String title, String text) {
        Notifications notifications = Notifications.create()
                .title(title)
                .text(text)
                .graphic(new ImageView(img))
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        notifications.show();
        Toolkit.getDefaultToolkit().beep();
    }
}
