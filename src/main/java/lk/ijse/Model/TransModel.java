package lk.ijse.Model;

import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Cart;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class TransModel {
    public static boolean placeOrder(String oId,String total,String change,String pay, List<Cart> cartDTOList,String itm) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            String id = ShipmentModel.getNextOrderId();
            boolean isSaved = ShipmentModel.save( id, LocalDate.now(), LocalTime.now(),Double.parseDouble(change),Double.parseDouble(total),Double.parseDouble(pay));
            if(isSaved) {
                boolean isUpdated = ItemModel.updateQtyyy(cartDTOList);
                if(isUpdated) {
                    boolean isOrdered = SupplierDetailModel.save(oId, cartDTOList,LocalDate.now(),Double.parseDouble(total));
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
}
