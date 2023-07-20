package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;


public class HistoryModel {

    public static  LocalDate logDate;
    public static LocalTime logTime;

    public static void setHistory(String name, LocalDate date, LocalTime timein) throws SQLException {

        logDate = date;
        logTime = timein;

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO loghistory(UserName, Date,Time_In,Time_Out)" +
                "VALUES(?, ?, ?,?)";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, name);
        pstm.setDate(2, Date.valueOf(date));
        pstm.setTime(3, Time.valueOf(timein));
        pstm.setTime(4, null);

        pstm.executeUpdate();

    }

    public static void sOut(LocalDate dt, LocalTime lt, LocalTime nt) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "UPDATE loghistory set Time_Out = ? WHERE Date = ? AND Time_In = ?";

        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setTime(1, Time.valueOf(nt));
        pstm.setDate(2, Date.valueOf(dt));
        pstm.setTime(3, Time.valueOf(lt));

        pstm.executeUpdate();
    }
}
