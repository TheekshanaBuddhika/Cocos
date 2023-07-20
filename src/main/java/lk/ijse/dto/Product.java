package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String itemid;
    private String itemname;
    private Double qty;
    private String ingname;
    private Double inqqty;
}
