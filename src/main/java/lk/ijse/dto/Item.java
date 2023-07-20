package lk.ijse.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Item {
    private String code;
    private String description;
    private Double qtyOnHand;
    private Double unitPrice;
    private Double Retail;
    private Double Base;
    private String Type;


}