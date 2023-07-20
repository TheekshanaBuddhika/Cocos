package lk.ijse.dto.TM;

import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ItemTM {

    private String code;
    private String name;
    private Double qty;
    private Double Uprc;
    private Double Retail;
    private Double Base;

}
