package lk.ijse.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private String username;
    private String password;
    private String employeeid;
}
