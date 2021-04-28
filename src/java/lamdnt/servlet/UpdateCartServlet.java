/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblorderitems.CartObj;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tblproduct.TblProductDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {

    private static final String URL_CART = "orderCart.jsp";

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
        String pk = request.getParameter("pk");
        String quantity = request.getParameter("quantity");
        String url = URL_CART;
        int id = 0;
        int quantityProduct = 0;
        boolean flag = true;
        String msg = "Error when updating product";
        boolean status = false;
        try {
            /* TODO output your page here. You may use following sample code. */
            try {
                id = Integer.parseInt(pk);
                quantityProduct = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                flag = false;
            }
            if (flag) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    CartObj cartObj = (CartObj) session.getAttribute("PRODUCTCART");
                    if (cartObj != null || cartObj.getItems() != null || !cartObj.getItems().isEmpty()) {
                        TblProductDAO dao = new TblProductDAO();
                        TblProductDTO dto = dao.findProductById(id);
                        if (dto != null) {
                            if (dto.getAmount() >= quantityProduct) {
                                cartObj.updateQuantity(id, quantityProduct);
                                msg="Update product success";
                                status = true;
                            } else {
                                msg = cartObj.getProductFromId(id).getProductName() + " have " + dto.getAmount()+ " left";
                            }
                        }
                    }
                }
            }
            if (status){
                request.setAttribute("SUCCESS", msg);
            }
            else{
                request.setAttribute("FAIL", msg);
            }
        } catch (NamingException ex) {
            log("UpdateCartAmountServlet_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("UpdateCartAmountServlet_SQLException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
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
