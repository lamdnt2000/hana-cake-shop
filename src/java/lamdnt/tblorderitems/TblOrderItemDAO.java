/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblorderitems;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import lamdnt.utiles.DBHelper;

/**
 *
 * @author sasuk
 */
public class TblOrderItemDAO implements Serializable {

    public boolean addProductToCart(TblOrderItemDTO dto) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;

        try {
            con = DBHelper.makeConnection();
            if (con != null) {

                String query = "INSERT INTO tbl_order_items (orderId,productId,quantity) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(query);
                stm.setInt(1, dto.getOrderId());
                stm.setInt(2, dto.getProductId());
                stm.setInt(3, dto.getQuantity());
                int row = stm.executeUpdate();
                if (row > 0) {
                    result = true;

                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    
}
