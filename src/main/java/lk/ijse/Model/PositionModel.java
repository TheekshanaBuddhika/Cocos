package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Item;
import lk.ijse.dto.Position;
import lk.ijse.dto.SaleType;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionModel {

    public static Position getPos(String pos_Id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM `position` WHERE Position_Name = ?");
        pstm.setString(1, pos_Id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new Position(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)

            );
        }

        return null;
    }

    public static Position getPos2(String pos_Id) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM `position` WHERE Pos_id = ?");
        pstm.setString(1, pos_Id);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new Position(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getString(4)

            );
        }

        return null;
    }

    public static List<String> loadPos() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT Position_Name FROM `position`");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static List<String> loadtype() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT name FROM saletype");

        List<String> data =new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static SaleType gettypePos(String pos) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM `saletype` WHERE name = ?");
        pstm.setString(1, pos);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new SaleType(
                    resultSet.getString(1),
                    resultSet.getString(2)

            );
        }

        return null;
    }

    public static List<Position> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM `position`";
        PreparedStatement pstm = con.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Position> dataList = new ArrayList<>();

        while (resultSet.next()) {
            String code = resultSet.getString(1);
            String description = resultSet.getString(2);
            Double qtyOnHand = resultSet.getDouble(3);
            String unitPrice = resultSet.getString(4);


            Position item = new Position(code, description, qtyOnHand, unitPrice);
            dataList.add(item);
        }
        return dataList;

    }

    public static boolean delete(String posName) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE  FROM `position` WHERE Position_Name = ?";

        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1, posName);

        return pstm.executeUpdate() > 0;
    }

    public static boolean save(Position pos) throws SQLException {

        String sql = "INSERT INTO position (Pos_Id, Position_Name, Basic_Salary, Permission) VALUES (?,?,?,?)";

        return CrudUtil.execute(sql,pos.getPositionId(),pos.getPosName(),pos.getSalary(),pos.getPermision());

    }

    public static boolean update(Position pos) throws SQLException {
        String sql = "UPDATE position SET Position_Name = ? ,Basic_Salary=? , Permission = ? WHERE Pos_Id = ?";

        return CrudUtil.execute(sql,pos.getPosName(),pos.getSalary(),pos.getPermision(),pos.getPositionId());
    }


    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Pos_Id FROM `position` ORDER BY Pos_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("P");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "P" + id;
        }
        return "P1000";
    }

    public static List<String> getPermission() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT Pos_Id FROM `position` WHERE Permission = 'Granted' ";
        ResultSet resultSet = con.createStatement().executeQuery(sql);

        List<String> data = new ArrayList<String>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;


    }
}
