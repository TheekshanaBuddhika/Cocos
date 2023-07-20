package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.User;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public static User getUser(String UserName) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM user WHERE UserName = ?");
        pstm.setString(1, UserName);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static String getEmail() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "select Email from user where Emp_id IS NULL";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if(resultSet.next()){
           return (resultSet.getString(1));
        }
        return null ;
    }

    public static User getUserId(String empid) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM user WHERE Emp_id = ?");
        pstm.setString(1, empid);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new User(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static boolean setUser(String username, String password , String employeeid) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO user(UserName, Password,Email, Emp_id)" +
                "VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, username);
        pstm.setString(2, password);
        pstm.setString(3,null);
        pstm.setString(4, employeeid);

        return pstm.executeUpdate() > 0;
    }

    public static String getMail(String useName) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT Email FROM user WHERE UserName = ?");
        pstm.setString(1, useName);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
           return resultSet.getString(1);

        }
        return null;

    }

    public static boolean chnprof(String useName, String password, String newname) throws SQLException {
        String sql = "UPDATE user SET UserName = ? , Password = ? WHERE UserName = ?";

        return CrudUtil.execute(sql,newname,password,useName);

    }

    public static String getMail2(String useName) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("SELECT Email FROM user WHERE UserName = ?");
        pstm.setString(1, useName);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;

    }

    public static boolean chanMail(String txt) throws SQLException {
        String sql = "UPDATE user SET Email = ?  WHERE Email IS NOT NULL";

        return CrudUtil.execute(sql,txt);

    }

    public static List<String>  getName() throws SQLException{

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT UserName FROM user";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<String> data = new ArrayList<String>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }
}
