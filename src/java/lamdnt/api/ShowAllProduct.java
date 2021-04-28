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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblproduct.FilterParam;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tblproduct.TblProductDTO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "ShowAllProduct", urlPatterns = {"/api/ShowAllProduct"})
public class ShowAllProduct extends HttpServlet {

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
        String currentPage = request.getParameter("page");
        String searchName = request.getParameter("txtSearchName");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");
        int page = 1;
        int catID = 0;
        int productStatus = 1;
        int min = 0;
        int max = 1000000000;
        try {
            try {
                catID = Integer.parseInt(catId);
                productStatus = Integer.parseInt(status);
                min = Integer.parseInt(minPrice);
                max = Integer.parseInt(maxPrice);
            } catch (NumberFormatException e) {

            }

            page = Integer.parseInt(currentPage);
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);
            int role = 0;
            if (session != null) {
                TblUserDTO user = (TblUserDTO) session.getAttribute("RESULTLOGIN");
                if (user != null) {
                    role = user.getRole();
                }
            }
            TblProductDAO dao = new TblProductDAO();
            FilterParam param = new FilterParam(searchName, catID, productStatus, min, max, role, page);
            dao.findAllProduct(param);
            List<TblProductDTO> result = dao.getListProduct();
            String json = new Gson().toJson(result);
            out.write(json);
        } catch (NamingException ex) {
            Logger.getLogger(ShowAllProduct.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ShowAllProduct.class.getName()).log(Level.SEVERE, null, ex);
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
