package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Product;
import lk.ijse.util.CrudUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ProductModel {
    public static boolean placeOrder(String oId, String ingName, List<Product> cartDTOList, String ingCode) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);
            boolean isOkay = ItemModel.updateQtypr3(cartDTOList);
            if(isOkay){
                boolean isUpdated = ItemModel.updateQtypr(cartDTOList);
                if(isUpdated) {
                    boolean isOrdered = ProductModel.save(oId, cartDTOList,LocalDate.now(),LocalTime.now());
                    if(isOrdered) {
                        con.commit();
                        return true;
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

    private static boolean save(String oId, List<Product> cartDTOList, LocalDate now, LocalTime now1) throws SQLException {

        for(Product dto : cartDTOList) {
            if(!save1(dto,oId,now,now1)) {
                return false;
            }
        }
        return true;
    }


    private static boolean save1(Product dto,String oid,LocalDate now ,LocalTime now1) throws SQLException {

        String sql = "INSERT INTO product(Item_id, Date, quantity, Time_in) VALUES(?,?,?,?)";

        return CrudUtil.execute(sql,dto.getItemid(),Date.valueOf(now),dto.getQty(),Time.valueOf(now1));
    }
}
