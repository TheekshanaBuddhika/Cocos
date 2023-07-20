package lk.ijse.Model;


import javafx.scene.image.Image;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Employee;
import lk.ijse.util.CrudUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    private static FileInputStream fis;

    public static Employee getPosition(String empid) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM employee WHERE Emp_id = ?");
        pstm.setString(1, empid);


        ResultSet resultSet = pstm.executeQuery();
        File ff = new File("");

        if(resultSet.next()) {
            return  new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    ff
            );
        }
        return null;
    }

    public static List<Employee> getAll() throws SQLException {

        List<Employee> data = new ArrayList<>();

        String sql = "SELECT * FROM employee";
        ResultSet resultSet = CrudUtil.execute(sql);

        File ff = new File("");

        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getInt(5),
                    resultSet.getDate(6),
                    resultSet.getDate(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    ff

            ));
        }
        return data;
    }

    public static boolean save(Employee employee) throws SQLException, IOException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO employee(Emp_id, First_Name, Last_Name, address, Contact_No, Date_Of_Birth, Joined_date, Gender, Pos_Id, Image) VALUES ( ?, ?, ? ,?, ?, ?, ?,? , ? ,?)";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, employee.getEmpid());
        pstm.setString(2, employee.getFirstName());
        pstm.setString(3, employee.getSecName());
        pstm.setString(4, employee.getAddress());
        pstm.setInt(5, employee.getContact());
        pstm.setDate(6, employee.getBirthdate());
        pstm.setDate(7, employee.getJoinday());
        pstm.setString(8, employee.getGender());
        pstm.setString(9, employee.getPosId());
        fis = new FileInputStream(employee.getFile());
        pstm.setBinaryStream(10, fis, (int)employee.getFile().length());

        return pstm.executeUpdate() > 0;
    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM employee WHERE Emp_id = ?";
        return CrudUtil.execute(sql,id);

    }

    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Emp_id FROM employee ORDER BY Emp_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("EM");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "EM" + id;
        }
        return "EM1000";
    }

    public static boolean update(Employee employee) throws SQLException, FileNotFoundException {

        Connection con = DBConnection.getInstance().getConnection();
       String sql = "Update employee SET First_Name = ?,Last_Name = ?,address = ?,Contact_No = ?,Date_Of_Birth =?,Joined_date= ?, Gender = ?,Pos_Id = ? ,Image =? where Emp_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, employee.getFirstName());
        pstm.setString(2, employee.getSecName());
        pstm.setString(3, employee.getAddress());
        pstm.setInt(4, employee.getContact());
        pstm.setDate(5, employee.getBirthdate());
        pstm.setDate(6, employee.getJoinday());
        pstm.setString(7, employee.getGender());
        pstm.setString(8, employee.getPosId());
        fis = new FileInputStream(employee.getFile());
        pstm.setBinaryStream(9, fis, (int) employee.getFile().length());
        pstm.setString(10, employee.getEmpid());

        return pstm.executeUpdate()>0;
    }

    public static Image settt(String id) throws SQLException, IOException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT Image FROM employee WHERE Emp_id = ?");
        pstm.setString(1, id);
        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            InputStream is = resultSet.getBinaryStream(1);
            OutputStream os = new FileOutputStream(new File("photo.png"));
            byte[] content = new byte[1024];
            int size = 0;
            while ((size = is.read(content)) != -1) {
                os.write(content, 0, size);
            }
            os.close();
            is.close();

            Image img = new Image("file:photo.png",210,285,true,true);
            return img;
        }
        return null;
    }

}
