package lk.ijse.Model;

import lk.ijse.dto.Cart;
import lk.ijse.util.CrudUtil;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class SupplierDetailModel {
    public static boolean save(String oId, List<Cart> cartDTOList, LocalDate now , Double total) throws SQLException {
        for(Cart dto : cartDTOList) {
            if(!save(oId, dto,now,total)) {
                return false;
            }
        }
        return true;
    }
    private static boolean save(String oId, Cart dto,LocalDate dt,Double tot) throws SQLException {

        String sql = "insert into supplydetail(Sup_id, Item_id, Date, quantity, Total) VALUES (?, ?, ?, ? , ?)";
        return CrudUtil.execute(sql,oId,dto.getCode(),dt,dto.getQty(),tot);
    }
}
