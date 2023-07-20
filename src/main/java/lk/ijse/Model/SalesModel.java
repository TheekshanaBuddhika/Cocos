package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Item;
import lk.ijse.util.CrudUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class SalesModel {
    public static boolean save(String id, LocalDate now, LocalTime now1, double change,double tot,double pay) throws SQLException {

        String sql = "INSERT INTO sales(Sales_Id, Date, Sales_time, `Change`, Total, Payment) VALUES(?, ?, ? , ?,?,?)";

        return CrudUtil.execute(sql,id,now,now1,change,tot,pay);
    }

    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Sales_Id FROM sales ORDER BY Sales_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("SS");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "SS" + id;
        }
        return "SS100000";
    }

    public static int getsalecount() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT COUNT(Sales_Id)  FROM sales WHERE Date = ?");
        pstm.setDate(1, Date.valueOf(LocalDate.now()));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
          return resultSet.getInt(1);
        }
        return 0;
    }

    public static Double getDailytot() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT SUM(Total)  FROM sales WHERE Date = ?");
        pstm.setDate(1, Date.valueOf(LocalDate.now()));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0.0;
    }

    public static Double getMonthlytot() throws SQLException {

        SimpleDateFormat simpleformat = new SimpleDateFormat("MM");
        String strMonth = simpleformat.format(new java.util.Date());


        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT SUM(Total) As Tooo FROM sales WHERE MONTH(CURDATE())= ?");
        pstm.setInt(1, Integer.parseInt(strMonth));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0.0;
    }
}