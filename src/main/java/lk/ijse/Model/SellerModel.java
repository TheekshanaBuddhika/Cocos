package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Buyer;
import lk.ijse.dto.Seller;
import lk.ijse.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerModel {
    public static List<Seller> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        List<Seller> data = new ArrayList<>();

        String sql = "SELECT * FROM supplier";
        ResultSet resultSet = CrudUtil.execute(sql);

        while (resultSet.next()) {
            data.add(new Seller(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            ));
        }
        return data;
    }

    public static boolean save(Seller seller) throws SQLException {
        String sql = "INSERT INTO supplier(Sup_id, Name, address, Joined_date,Time_period,Entrant) " +
                "VALUES(?, ?, ?, ?, ? , ?)";
        return CrudUtil.execute(
                sql,
                seller.getId(),
                seller.getName(),
                seller.getAddress(),
                seller.getDate(),
                seller.getPeriod(),
                seller.getEntrant()
        );
    }


    public static Seller search(String code) throws SQLException {
        Seller seller = null;
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM supplier WHERE Sup_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String supid = resultSet.getString(1);
            String name = resultSet.getString(2);
            String addrs = resultSet.getString(3);
            Date date = resultSet.getDate(4);
            int prd = resultSet.getInt(5);
            String ent = resultSet.getString(6);

            seller = new Seller(supid, name, addrs, date,prd,ent);
        }
        return seller;
    }

    public static boolean update(Seller seller) throws SQLException {
        String sql = "UPDATE supplier SET Name = ?,address = ?,Joined_date = ?,Time_period = ?,Entrant = ? WHERE Sup_id = ?";

        return CrudUtil.execute(
                sql,

                seller.getName(),
                seller.getAddress(),
                seller.getDate(),
                seller.getPeriod(),
                seller.getEntrant(),
                seller.getId()
        );
    }

    public static List<String> loadIds() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT Name FROM supplier");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Seller searchById(String id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM supplier WHERE Name = ?");
        pstm.setString(1, id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new Seller(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getInt(5),
                    resultSet.getString(6)
            );
        }
        return null;
    }

    public static boolean delete(String id) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE  FROM supplier WHERE Sup_id = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, id);

        return pstm.executeUpdate() > 0;

    }

    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Sup_id FROM supplier ORDER BY Sup_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("SP");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "SP" + id;
        }
        return "SP1000";
    }
}
