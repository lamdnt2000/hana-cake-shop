/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "DeleteProductServlet", urlPatterns = {"/admin/DeleteProductServlet"})
public class DeleteProductServlet extends HttpServlet {

    final String URL_REDIRECT = "product.jsp";
    final String DEFAULT_URL = "login.jsp";

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String catId = request.getParameter("catId");
        String status = request.getParameter("status");
        String urlRewriting = DEFAULT_URL;
        String[] listDelete = request.getParameterValues("productId");
        boolean access = false;
        List<Integer> listProductId = null;
        boolean flag = false;
        try {
            HttpSession session = request.getSession(false);
            TblUserDTO user = null;
            if (session != null) {
                user = (TblUserDTO) session.getAttribute("RESULTLOGIN");
                if (user != null) {
                    if (user.getRole() == 1) {
                        access = true;
                    }
                }
            }
            if (access) {
                urlRewriting = URL_REDIRECT + "?"
                        + "catId=" + catId + "&"
                        + "status=" + status + "&"
                        + "btAction=SEARCHPRODUCT";
                if (listDelete != null) {
                    listProductId = new ArrayList<>();
                    try {
                        for (String str : listDelete) {

                            listProductId.add(Integer.parseInt(str));
                        }
                    } catch (NumberFormatException e) {
                        log("DeleteProductServlet_NumberFormatException:" + e.getMessage());
                        flag = true;
                    }
                    if (!flag) {
                        TblProductDAO dao = new TblProductDAO();

                        boolean result = dao.deleteProduct(listProductId);
                    }
                }
            }
        } catch (SQLException ex) {
            log("DeleteProductServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("DeleteProductServlet_NamingException:" + ex.getMessage());
        } finally {
            response.sendRedirect(urlRewriting);
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
