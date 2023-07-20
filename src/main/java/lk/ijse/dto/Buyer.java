package lk.ijse.dto;

import lombok.*;

import java.sql.Date;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Buyer {
    private String id;
    private String name;
    private String address;
    private Date date;
    private int period;
    private String Entrant;
}
