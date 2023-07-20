package lk.ijse.dto.TM;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.sql.Date;


@Data
@AllArgsConstructor
public class EmployeeTM {


    private String id;
    private String Fname;
    private String Lname;
    private String address;
    private int contact;
    private Date Dob;
    private Date joinedDate;
    private String position;


}
