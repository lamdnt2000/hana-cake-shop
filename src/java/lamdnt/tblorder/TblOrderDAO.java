/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblorder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import lamdnt.utiles.DBHelper;

/**
 *
 * @author sasuk
 */
public class TblOrderDAO implements Serializable {
    private List<TblOrderDTO> listHistory;
    public TblOrderDTO createOrder(String username) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        TblOrderDTO result = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {

                String query = "INSERT INTO tbl_order (customerId,status,dateCreate) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
                stm.setString(1, username);
                stm.setInt(2, 0);
                Timestamp dateCreate = new Timestamp(new Date().getTime());
                stm.setTimestamp(3, dateCreate);
                int row = stm.executeUpdate();
                if (row > 0) {
                    rs = stm.getGeneratedKeys();
                    if (rs.next()) {
                        int orderId = rs.getInt(1);
                        result = new TblOrderDTO(orderId, username,0,dateCreate);
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
    public boolean updateOrder(int orderId,float total) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tbl_order SET status = 1, total = ? "
                        +"WHERE orderId=?";
                stm = con.prepareStatement(sql);
                stm.setFloat(1, total);
                stm.setInt(2, orderId);
                int row = stm.executeUpdate();
                if (row > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
            return result;
        }
    }
    public void searchHistory(String name, Date dateBuy, String member) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String queryName = (!"".equals(name)) ? "AND P.productName LIKE ? " : "";
                String buyDateQuery = (dateBuy!=null) ? "AND datediff(day, ?, O.dateCreate) = 0 ":"";
                String sql = "SELECT DISTINCT O.orderId,O.total,O.dateCreate,O.status "
                        + "FROM tbl_order O, tbl_order_items I, tbl_product P "
                        + "WHERE customerId = ? "
                        + "AND O.status=1 "
                        + "AND O.orderId = I.orderId "
                        + "AND I.productId = P.productId "
                        + queryName
                        + buyDateQuery
                        + "ORDER BY O.dateCreate DESC";
                int count = 0;
                stm = con.prepareStatement(sql);
                stm.setString(++count, member);
                if (!"".equals(queryName)) {
                    stm.setString(++count, "%" + name + "%");
                }
                if (!"".equals(buyDateQuery)) {
                    stm.setTimestamp(++count, new Timestamp(dateBuy.getTime()));
                }

                rs = stm.executeQuery();

                while (rs.next()) {
                    int id = rs.getInt("orderId");
                    float total = rs.getFloat("total");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    TblOrderDTO dto = new TblOrderDTO(id, member, 1, dateCreate);
                    dto.setTotal(total);
                    if (listHistory == null) {
                        listHistory = new ArrayList<>();
                    }
                    listHistory.add(dto);
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
    }

    public List<TblOrderDTO> getListHistory() {
        return listHistory;
    }
    
}
