/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tblproduct.TblProductDTO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "GetProductDetailServlet", urlPatterns = {"/admin/GetProductDetailServlet"})
public class GetProductDetailServlet extends HttpServlet {

    final String URL_FORWARD = "productdetail.jsp";
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
        String id = request.getParameter("id");
        String url = DEFAULT_URL;
        int productId = 0;
        boolean access = false;
        try {
            /* TODO output your page here. You may use following sample code. */
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
                url = URL_FORWARD;
                try {
                    productId = Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    log("GetProductDetailServlet_NumberFormatException:" + e.getMessage());
                }
                TblProductDAO dao = new TblProductDAO();
                TblProductDTO dto = dao.findProductById(productId, 1);
                if (dto != null) {
                    request.setAttribute("PRODUCTDETAIL", dto);
                }
            }
        } catch (NamingException ex) {
            log("GetProductDetailServlet_NamingException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("GetProductDetailServlet_SQLException:" + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(URL_FORWARD);
            rd.forward(request, response);
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
