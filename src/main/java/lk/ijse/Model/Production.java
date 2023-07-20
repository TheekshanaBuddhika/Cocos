package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Production {
    public static Double getProduct() throws SQLException {

        SimpleDateFormat simpleformat = new SimpleDateFormat("MM");
        String strMonth = simpleformat.format(new java.util.Date());


        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT SUM(quantity)  FROM product WHERE MONTH(CURDATE())= ?");
        pstm.setInt(1, Integer.parseInt(strMonth));

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0.0;
    }

    public static Double getProduct2(int i) throws SQLException {

        int year = LocalDate.now().getYear();

        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT SUM(quantity)  FROM product WHERE MONTH(Date)= ? AND YEAR(CURDATE()) = ?");
        pstm.setInt(1, i);
        pstm.setInt(2,year);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return resultSet.getDouble(1);
        }
        return 0.0;
    }


}
