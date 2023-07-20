package lk.ijse.Model;

import javafx.scene.control.Alert;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Cart;
import lk.ijse.dto.Item;
import lk.ijse.dto.Product;
import lk.ijse.dto.SaleType;
import lk.ijse.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ItemModel {

    public static boolean delete(String code) throws SQLException {

            Connection connection = DBConnection.getInstance().getConnection();
            String sql = "DELETE  FROM item WHERE item_id = ?";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, code);

            return pstm.executeUpdate() > 0;

    }

    public static boolean save(Item item) throws SQLException {
        String sql = "INSERT INTO item(Item_id, Name, quantity, Unit_Price, Retail_Price, Base_Price, Type) VALUES (?, ?, ?, ? , ? ,? ,?)";
        return CrudUtil.execute(
                sql,
                item.getCode(),
                item.getDescription(),
                item.getQtyOnHand(),
                item.getUnitPrice(),
                item.getRetail(),
                item.getBase(),
                item.getType());
    }

    public static boolean update(Item item) throws SQLException {
            String sql = "UPDATE item SET Name = ? , quantity = ? , Unit_Price = ?, Retail_Price = ?, Base_Price = ? , Type = ? WHERE Item_id = ?";

            return CrudUtil.execute(
                    sql,
                    item.getDescription(),
                    item.getQtyOnHand(),
                    item.getUnitPrice(),
                    item.getRetail(),
                    item.getBase(),
                    item.getType(),
                    item.getCode()

                    );
    }

    public static Item search(String code) throws SQLException {
        Item item = null;
       Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM item WHERE item_id = ?";
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1, code);
            ResultSet resultSet = pstm.executeQuery();
            if(resultSet.next()) {
                String item_code = resultSet.getString(1);
                String desc = resultSet.getString(2);
                double qty = resultSet.getDouble(3);
                double price = resultSet.getDouble(4);
                double price2 = resultSet.getDouble(5);
                double price3 = resultSet.getDouble(6);
                String type = resultSet.getString(7);

                item = new Item(item_code, desc, qty, price,price2,price3,type);
            }
            return item;
        }

    public static List<Item> getAll() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
            String sql = "SELECT * FROM item";
            PreparedStatement pstm = con.prepareStatement(sql);

            ResultSet resultSet = pstm.executeQuery();

            List<Item> dataList = new ArrayList<>();

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String description = resultSet.getString(2);
                Double qtyOnHand = resultSet.getDouble(3);
                Double unitPrice = resultSet.getDouble(4);
                Double price2 = resultSet.getDouble(5);
                Double price3 = resultSet.getDouble(6);
                String type = resultSet.getString(7);


                Item item = new Item(code, description, qtyOnHand, unitPrice,price2,price3,type);
                dataList.add(item);
            }
            return dataList;

    }

    public static List<String> loadIds() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT NAME FROM item");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static Item searchById(String code) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        PreparedStatement pstm = con.prepareStatement("SELECT * FROM item WHERE Name = ?");
        pstm.setString(1, code);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4),
                    resultSet.getDouble(5),
                    resultSet.getDouble(6),
                    resultSet.getString(7)
            );
        }
        return null;
    }

    public static boolean updateQty(List<Cart> cartDTOList) throws SQLException {
        for(Cart dto : cartDTOList) {
            if(!updateQty(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQty(Cart dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE item SET quantity = (quantity - ?) WHERE item_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setDouble(1, dto.getQty());
        pstm.setString(2, dto.getCode());

        return pstm.executeUpdate() > 0;
    }


    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT item_id FROM item ORDER BY item_id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("It");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "It" + id;
        }
        return "It1000";
    }


    public static boolean updateQtyyy(List<Cart> cartDTOList) throws SQLException {
        for(Cart dto : cartDTOList) {
            if(!updateQtyh(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQtyh(Cart dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE item SET quantity = (quantity + ?) WHERE item_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setDouble(1, dto.getQty());
        pstm.setString(2, dto.getCode());

        return pstm.executeUpdate() > 0;
    }


    public static SaleType getname(String type) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = con.prepareStatement("select * from saletype where type_id = ?");
        pstm.setString(1, type);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            return  new SaleType(
                    resultSet.getString(1),
                    resultSet.getString(2)
            );
        }
        return null;
    }

    public static List<String> loadIds1() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT NAME FROM item WHERE Type ='S00'");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static List<String> loadIds2() throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();
        ResultSet resultSet = con.createStatement().executeQuery("SELECT NAME FROM item WHERE Type ='S01'");

        List<String> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(resultSet.getString(1));
        }
        return data;
    }

    public static boolean updateQtypr(List<Product> cartDTOList) throws SQLException {
        for(Product dto : cartDTOList) {
            if(!updateQtypr2(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQtypr2(Product dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE item SET quantity = (quantity - ?) WHERE Name = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setDouble(1, dto.getInqqty());
        pstm.setString(2, dto.getIngname());

        return pstm.executeUpdate() > 0;
    }

    public static boolean updateQtypr3(List<Product> cartDTOList) throws SQLException {
        for(Product dto : cartDTOList) {
            if(!updateQtypr4(dto)) {
                return false;
            }
        }
        return true;
    }

    private static boolean updateQtypr4(Product dto) throws SQLException {
        Connection con = DBConnection.getInstance().getConnection();

        String sql = "UPDATE item SET quantity = (quantity + ?) WHERE item_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setDouble(1, dto.getQty());
        pstm.setString(2, dto.getItemid());

        return pstm.executeUpdate() > 0;
    }

    public static SaleType getType(String code)throws SQLException{
        SaleType item = null;
        Connection con = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM saletype WHERE type_id = ?";
        PreparedStatement pstm = con.prepareStatement(sql);
        pstm.setString(1, code);
        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String item_code = resultSet.getString(1);
            String desc = resultSet.getString(2);

            item = new SaleType(item_code, desc);
        }
        return item;
    }

}


