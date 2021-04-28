/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.api;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lamdnt.tblproduct.FilterParam;
import lamdnt.tblproduct.TblProductDAO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "GetDataForPaging", urlPatterns = {"/api/GetDataForPaging"})
public class GetDataForPaging extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String catId = request.getParameter("catId");
        String status = request.getParameter("status");
        String searchName = request.getParameter("txtSearchName");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        int categoryId = 0;
        int productStatus = 1;
        int min = 0;
        int max = 1000000000;
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                categoryId = Integer.parseInt(catId);
                productStatus = Integer.parseInt(status);
                min = Integer.parseInt(minPrice);
                max = Integer.parseInt(maxPrice);
            } catch (NumberFormatException e) {

            }
            TblProductDAO dao = new TblProductDAO();
            FilterParam param = new FilterParam(searchName, categoryId, productStatus, min, max, 0, 0);
            dao.countProduct(param);
            int rowCount = dao.getCountProduct();
            Hashtable<String, Integer> hash = new Hashtable<>();
            hash.put("catId", categoryId);
            hash.put("status", productStatus);
            hash.put("totalProduct", rowCount);
            String json = new Gson().toJson(hash);
            out.write(json);

        } catch (NamingException ex) {
            Logger.getLogger(GetDataForPaging.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(GetDataForPaging.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
