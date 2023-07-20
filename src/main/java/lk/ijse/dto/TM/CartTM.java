package lk.ijse.dto.TM;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartTM {
    private String code;
    private String description;
    private Double qty;
    private Double unitPrice;
    private Double total;
    private JFXButton btn;
}