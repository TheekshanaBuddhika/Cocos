package lk.ijse.dto;

import javafx.scene.image.Image;
import lombok.*;

import java.io.File;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;


@Data
@AllArgsConstructor
public class Employee {
    private String empid;
    private String firstName;
    private String secName;
    private String address;
    private int contact;
    private Date birthdate;
    private Date joinday;
    private String Gender;
    private String PosId;
    private File file;


}
