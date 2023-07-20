package lk.ijse.dto.TM;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTM {

    private String itemid;
    private String itemname;
    private Double qty;
    private String ingname;
    private Double inqqty;
    private JFXButton btn;

}
