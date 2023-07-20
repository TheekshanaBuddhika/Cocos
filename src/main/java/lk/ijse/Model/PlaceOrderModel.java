package lk.ijse.Model;

import lk.ijse.Controller.ReportFormController;
import lk.ijse.dbconnection.DBConnection;
import lk.ijse.dto.Cart;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceOrderModel {
    public static boolean placeOrder(String oId,String total,String change,String pay, List<Cart> cartDTOList,String itm,LocalTime time) throws SQLException {
        Connection con = null;
        try {
            con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            String id = SalesModel.getNextOrderId();
            boolean isSaved = SalesModel.save( id,LocalDate.now(), LocalTime.now(),Double.parseDouble(change),Double.parseDouble(total),Double.parseDouble(pay));
            if(isSaved) {

                  boolean isUpdated = ItemModel.updateQty(cartDTOList);

                if(isUpdated) {

                    boolean isOrdered = BuyerDetailModel.save(oId, cartDTOList,LocalDate.now(),Double.parseDouble(total),time);
                    if(isOrdered) {

                        con.commit();
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            con.rollback();
            return false;
        } finally {
            con.setAutoCommit(true);
        }
    }

}
