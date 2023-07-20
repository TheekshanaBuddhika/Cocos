package lk.ijse.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cart {
    private String code;
    private Double qty;
    private Double unitPrice;
}