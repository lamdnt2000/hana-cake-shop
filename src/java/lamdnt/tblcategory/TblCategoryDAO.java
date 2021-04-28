/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblcategory;

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
public class TblCategoryDAO implements Serializable{
    private List<TblCategoryDTO> listCategory;
    
    public void findAllCategory() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT catID,catName, description, dateCreate "
                        + "FROM tbl_category "
                        + "order by dateCreate DESC";
                        
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int catId = rs.getInt("catID");
                    String catName = rs.getString("catName");
                    String description = rs.getString("description");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    TblCategoryDTO dto = new TblCategoryDTO(catName,description);
                    dto.setDateCreate(dateCreate);
                    dto.setCatId(catId);
                    if (listCategory == null) {
                        listCategory = new ArrayList<>();
                    }
                    listCategory.add(dto);
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
    
    public TblCategoryDTO findCategoryById(int id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblCategoryDTO result = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT catID,catName, description, dateCreate "
                        + "FROM tbl_category "
                        + "WHERE catID=?";
                        
                stm = con.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int catId = rs.getInt("catID");
                    String catName = rs.getString("catName");
                    String description = rs.getString("description");
                    Timestamp dateCreate = rs.getTimestamp("dateCreate");
                    result = new TblCategoryDTO(catName,description);
                    result.setDateCreate(dateCreate);
                    result.setCatId(catId);
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
    
    public boolean createCategory(TblCategoryDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO tbl_category (catName,description,dateCreate) "
                        + "VALUES (?,?,?)";
                stm = con.prepareStatement(query);
                stm.setString(1, dto.getCatName());
                stm.setString(2, dto.getDescription());
                Date date = new Date();
                stm.setTimestamp(3, new Timestamp(date.getTime()));
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
        }
        return result;
    }

    public List<TblCategoryDTO> getListCategory() {
        return listCategory;
    }
    
}
