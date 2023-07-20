package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ShipmentModel {
    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Ship_id FROM shipment ORDER BY Ship_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("SH");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "SH" + id;
        }
        return "SH1000";
    }

    public static boolean save(String id, LocalDate now, LocalTime now1, double change,double tot,double pay) throws SQLException {

        String sql = "insert into shipment(Ship_id, DATE, Time_in, `change`, Total, Payment)  VALUES(?, ?, ? , ?,?,?)";

        return CrudUtil.execute(sql,id,now,now1,change,tot,pay);
    }
}
