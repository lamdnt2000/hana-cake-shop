/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.responsestatus.ResponseMessage;
import lamdnt.tblorderitems.CartObj;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tblproduct.TblProductDTO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "AddProductToCartServlet", urlPatterns = {"/AddProductToCartServlet"})
public class AddProductToCartServlet extends HttpServlet {

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
        String id = request.getParameter("id");
        String quantity = request.getParameter("quantity");
        int productId = 0;
        int quantityProduct = 0;
        int status = 0;
        boolean flag = true;
        String msg = "Add product to cart fail";
        try {
            try {
                productId = Integer.parseInt(id);
                quantityProduct = Integer.parseInt(quantity);
            } catch (NumberFormatException ex) {
                log("AddProductToCartServlet_NamingException:" + ex.getMessage());
                flag = false;
            }
            boolean access = true;
            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUserDTO user = (TblUserDTO) session.getAttribute("RESULTLOGIN");
                if (user != null) {
                    if (user.getRole() == 1) {
                        access = false;
                    }
                }
            }
            /* TODO output your page here. You may use following sample code. */
            if (flag && access) {
                TblProductDAO dao = new TblProductDAO();
                TblProductDTO dto = dao.findProductById(productId);
                if (dto != null) {
                    CartObj cart = (CartObj) session.getAttribute("PRODUCTCART");
                    if (cart == null) {
                        cart = new CartObj();
                    }
                    cart.addItemToCart(dto, quantityProduct);
                    session.setAttribute("PRODUCTCART", cart);
                    msg = "Add product to cart success";
                    status = 1;
                }
            }

        } catch (NamingException ex) {
            log("AddProductToCartServlet_NamingException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("AddProductToCartServlet_SQLException:" + ex.getMessage());
        } finally {
            ResponseMessage res = new ResponseMessage(status, msg);
            String json = new Gson().toJson(res);
            out.println(json);
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
