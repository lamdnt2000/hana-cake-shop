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
public class TblCartDAO implements Serializable {

    public TblCartDTO createCart(String username) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        TblCartDTO result = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO tblCart (username) "
                        + "VALUES (?)";
                stm = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, username);
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        int cartId = rs.getInt(1);
                        result = new TblCartDTO(cartId, username);
                    }

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
