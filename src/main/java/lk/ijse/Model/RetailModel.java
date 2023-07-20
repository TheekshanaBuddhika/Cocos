package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Cart;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RetailModel {
    public static boolean placeOrder(String trid,String total,String change,String pay, List<Cart> cartDTOList,String itm,LocalTime time) throws SQLException {

        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            String id = SalesModel.getNextOrderId();
            boolean isSaved = SalesModel.save( id, LocalDate.now(), LocalTime.now(),Double.parseDouble(change),Double.parseDouble(total),Double.parseDouble(pay));
            if(isSaved) {
                boolean isUpdated = ItemModel.updateQty(cartDTOList);
                if(isUpdated) {
                    boolean isDone = RetailModel.save1(trid,LocalDate.now(),time,Double.parseDouble(total));
                    if (isDone) {
                        boolean isOrdered = RetailModel.savee(trid, cartDTOList, LocalDate.now(), Double.parseDouble(total),time);
                        if (isOrdered) {
                            con.commit();
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (SQLException er) {
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }

    private static boolean save1(String trid, LocalDate now, LocalTime now1, Double total) throws SQLException {
        String sql = "insert into transaction (Trans_Id, Date, Trans_time, Total)  VALUES(?, ?, ? , ?)";

        return CrudUtil.execute(sql,trid,now,now1,total);
    }

    private static boolean savee(String trid, List<Cart> cartDTOList, LocalDate now, double total,LocalTime time) throws SQLException {
        for(Cart dto : cartDTOList) {
            if(!save(trid, dto,now,total,time)) {
                return false;
            }
        }
        return true;
    }

    private static boolean save(String oId, Cart dto,LocalDate dt,Double tot,LocalTime time) throws SQLException {

        String sql = "INSERT INTO transactiondetail (Trans_Id, Item_id, Date, quantity, Total, TimeOfSale) VALUES (?, ?, ?, ? , ? ,?)";
        return CrudUtil.execute(sql,oId,dto.getCode(),dt,dto.getQty(),(dto.getQty()*dto.getUnitPrice()),time);
    }
    public static String getNextOrderId() throws SQLException {

        Connection con = DBConnection.getInstance().getConnection();

        String sql = "SELECT Trans_Id FROM `transaction` ORDER BY Trans_Id DESC LIMIT 1";

        ResultSet resultSet = con.createStatement().executeQuery(sql);

        if (resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }

    private static String splitOrderId(String currentId) {
        if (currentId != null) {
            String[] strings = currentId.split("TR");
            int id = Integer.parseInt(strings[1]);
            id++;
            return "TR" + id;
        }
        return "TR100000";
    }

}

