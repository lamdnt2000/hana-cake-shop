/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblproduct;

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
public class TblProductDAO implements Serializable {

    final int PRODUCT_PER_PAGE = 20;
    private List<TblProductDTO> listProduct;
    private int countProduct = 0;

    public boolean createProduct(TblProductDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "INSERT INTO tbl_product (productName,image,price,catID,description,amount,status,userCreate,createDate) "
                        + "VALUES (?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(query);
                stm.setString(1, dto.getProductName());
                stm.setString(2, dto.getImage());
                stm.setFloat(3, dto.getPrice());
                stm.setInt(4, dto.getCatId());
                stm.setString(5, dto.getDescription());
                stm.setInt(6, dto.getAmount());
                stm.setInt(7, dto.getStatus());
                stm.setString(8, dto.getUserCreate());
                Date date = new Date();
                stm.setTimestamp(9, new Timestamp(date.getTime()));
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

    public boolean updateProduct(TblProductDTO dto) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "Update tbl_product "
                        + "SET productName=?, image=?, price=?, catID=?, description=?, amount=?, status=?, userUpdate=?,updateDate=? "
                        + "WHERE productID=?";
                stm = con.prepareStatement(query);
                stm.setString(1, dto.getProductName());
                stm.setString(2, dto.getImage());
                stm.setFloat(3, dto.getPrice());
                stm.setInt(4, dto.getCatId());
                stm.setString(5, dto.getDescription());
                stm.setInt(6, dto.getAmount());
                stm.setInt(7, dto.getStatus());
                stm.setString(8, dto.getUserUpdate());
                Date date = new Date();
                stm.setTimestamp(9, new Timestamp(date.getTime()));
                stm.setInt(10, dto.getProductId());
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

    public List<TblProductDTO> getListProduct() {
        return listProduct;
    }

    public void findAllProduct(FilterParam param) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String queryCategoryId = (param.getCatId() > 0) ? "AND P.catID = ? " : "";
                String sql = "SELECT P.productID, P.productName, P.image, P.price, P.createDate, P.description, P.amount, P.status, P.userCreate, P.userUpdate, C.catID, C.catName "
                        + "FROM tbl_product P, tbl_category C "
                        + "WHERE P.catID = C.catID "
                        + "AND P.status = ? "
                        + "AND P.productName LIKE ? "
                        + "AND P.price>=? AND P.price<=? "
                        + queryCategoryId
                        + "ORDER BY P.createDate DESC "
                        + "OFFSET ? ROWS "
                        + "FETCH NEXT ? ROWS ONLY";

                stm = con.prepareStatement(sql);
                int count = 0;
                stm.setInt(++count, param.getStatus());
                stm.setString(++count, "%" + param.getSearchName() + "%");
                stm.setInt(++count, param.getMinPrice());
                stm.setInt(++count, param.getMaxPrice());
                if (queryCategoryId != "") {
                    stm.setInt(++count, param.getCatId());
                }
                stm.setInt(++count, (param.getPage() - 1) * PRODUCT_PER_PAGE);
                stm.setInt(++count, PRODUCT_PER_PAGE);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int productId = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    Timestamp dateCreate = rs.getTimestamp("createDate");
                    float price = rs.getFloat("price");
                    int catId = rs.getInt("catID");
                    String catName = rs.getString("catName");
                    int amount = rs.getInt("amount");
                    int status = rs.getInt("status");
                    TblProductDTO dto = new TblProductDTO(productId, productName, description, price, amount, image, status, catId);
                    dto.setCreateDate(dateCreate);
                    dto.setCatName(catName);
                    if (param.getRole() > 0) {
                        String userCreate = rs.getString("userCreate");
                        String userUpdate = rs.getString("userUpdate");
                        dto.setUserCreate(userCreate);
                        dto.setUserUpdate(userUpdate);
                    }
                    dto.setCatId(catId);
                    if (listProduct == null) {
                        listProduct = new ArrayList<>();
                    }
                    listProduct.add(dto);
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

    public boolean deleteProduct(List ids) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();

            if (con != null) {

                String query = "UPDATE tbl_product "
                        + "SET status=? "
                        + "WHERE productID IN ids";
                String idList = "";
                for (int i = 0; i < ids.size(); i++) {
                    idList += ids.get(i);
                    if (i < ids.size() - 1) {
                        idList += ",";
                    }
                }
                query = query.replace("ids", "(" + idList + ")");
                stm = con.prepareStatement(query);
                stm.setInt(1, 0);
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

    public TblProductDTO findProductById(int id, int role) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductDTO result = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT productID, productName, image, price, createDate, description, amount, status, userCreate, userUpdate, catID "
                        + "FROM tbl_product "
                        + "WHERE productID = ?";

                stm = con.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {

                    int productId = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String description = rs.getString("description");
                    String image = rs.getString("image");
                    Timestamp dateCreate = rs.getTimestamp("createDate");
                    float price = rs.getFloat("price");
                    int catId = rs.getInt("catID");
                    int amount = rs.getInt("amount");
                    int status = rs.getInt("status");
                    result = new TblProductDTO(productId, productName, description, price, amount, image, status, catId);
                    result.setCreateDate(dateCreate);
                    if (role > 0) {
                        String userCreate = rs.getString("userCreate");
                        String userUpdate = rs.getString("userUpdate");
                        result.setUserCreate(userCreate);
                        result.setUserUpdate(userUpdate);
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
            return result;
        }
    }

    public void countProduct(FilterParam param) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int row = 0;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String queryCategoryId = (param.getCatId() > 0) ? "AND catID = ? " : "";
                String sql = "SELECT COUNT(productID) "
                        + "FROM tbl_product "
                        + "WHERE status = ? "
                        + "AND productName LIKE ? "
                        + "AND price>=? AND price<=? "
                        + queryCategoryId;

                stm = con.prepareStatement(sql);
                stm.setInt(1, param.getStatus());
                stm.setString(2, "%" + param.getSearchName() + "%");
                stm.setInt(3, param.getMinPrice());
                stm.setInt(4, param.getMaxPrice());
                if (queryCategoryId != "") {
                    stm.setInt(5, param.getCatId());
                }
                rs = stm.executeQuery();
                if (rs.next()) {
                    countProduct = rs.getInt(1);
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

    public int getCountProduct() {
        return countProduct;
    }

    public TblProductDTO findProductById(int id) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        TblProductDTO result = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "SELECT P.productID, P.productName, P.image, P.price,P.amount, C.catID, C.catName "
                        + "FROM tbl_product P, tbl_category C "
                        + "WHERE P.productID = ? "
                        + "AND P.catId = C.catId";
                stm = con.prepareStatement(sql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int productId = rs.getInt("productID");
                    String productName = rs.getString("productName");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    int catId = rs.getInt("catID");
                    int amount = rs.getInt("amount");
                    String catName = rs.getString("catName");
                    result = new TblProductDTO(productId, productName, "", price, amount, image, 1, catId);
                    result.setCatName(catName);
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
            return result;
        }
    }

    public boolean updateAmountProduct(int orderId) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "UPDATE tbl_product SET amount = Q.amount - Q.quantity "
                        + "FROM tbl_product P INNER JOIN "
                        + "(SELECT P.productId, P.amount, O.quantity "
                        + "FROM tbl_product P INNER JOIN "
                        + "(SELECT productId, quantity "
                        + "FROM tbl_order_items WHERE orderId = ?) O "
                        + "ON P.productID = O.productId) Q "
                        + "ON P.productID=Q.productID";
                stm = con.prepareStatement(sql);
                stm.setInt(1, orderId);
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

}
