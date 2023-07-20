package lk.ijse.Model;


import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Cart;
import lk.ijse.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class BuyerDetailModel {

    public static boolean save(String oId, List<Cart> cartDTOList, LocalDate now , Double total, LocalTime time) throws SQLException {
        for(Cart dto : cartDTOList) {
            if(!save(oId, dto,now,total,time)) {
                return false;
            }
        }
        return true;
    }
    private static boolean save(String oId, Cart dto,LocalDate dt,Double tot,LocalTime time) throws SQLException {

        String sql = "INSERT INTO buyerdetail(Buy_id, Item_id, Date, quantity, Total, TimeOfSale) VALUES (?, ?, ?, ? , ? , ?)";
        return CrudUtil.execute(sql,oId,dto.getCode(),dt,dto.getQty(),dto.getQty()*dto.getUnitPrice(),time);
    }

}
