package lk.ijse.dto.TM;

import com.jfoenix.controls.JFXButton;
import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SellerTM {

    private String id;
    private String name;
    private String address;
    private Date date;
    private int period;

}
