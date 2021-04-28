/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblorder.TblOrderDAO;
import lamdnt.tblorder.TblOrderDTO;
import lamdnt.tblorderitems.CartObj;
import lamdnt.tblorderitems.TblOrderItemDAO;
import lamdnt.tblorderitems.TblOrderItemDTO;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tblproduct.TblProductDTO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private final static String URL_DEFAULT = "orderCart.jsp";
    private final static String URL_REDIRECT = "history.jsp";
    private final static String URL_LOGIN = "signin.html";

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
        String msg = "Check out fail try again";
        boolean status = false;
        String url = URL_DEFAULT;
        boolean access = false;

        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession(false);
            TblUserDTO user = null;
            if (session != null) {
                user = (TblUserDTO) session.getAttribute("RESULTLOGIN");
                if (user != null) {
                    if (user.getRole() == 0) {
                        access = true;
                    }
                }
            }

            if (access) {
                boolean flag = true;
                CartObj cartObj = (CartObj) session.getAttribute("PRODUCTCART");
                if (cartObj != null) {
                    TblOrderDAO dao = new TblOrderDAO();
                    TblOrderDTO order = dao.createOrder(user.getUsername());
                    if (order != null) {
                        Map<TblProductDTO, Integer> items = cartObj.getItems();
                        TblOrderItemDAO daoItem = new TblOrderItemDAO();
                        TblProductDAO productDAO = new TblProductDAO();

                        for (TblProductDTO product : items.keySet()) {
                            int quantity = items.get(product);
                            TblProductDTO dto = productDAO.findProductById(product.getProductId());
                            if (quantity > dto.getAmount()) {
                                msg = "Out of quantity update product";
                                flag = false;
                            }
                        }
                        if (flag) {
                            for (TblProductDTO product : items.keySet()) {
                                int quantity = items.get(product);

                                TblOrderItemDTO orderItem = new TblOrderItemDTO(order.getOrderId(), product.getProductId(), quantity);
                                daoItem.addProductToCart(orderItem);
                            }

                            boolean updateProduct = productDAO.updateAmountProduct(order.getOrderId());
                            if (updateProduct) {
                                float total = cartObj.getTotalPrice();
                                boolean updateOrder = dao.updateOrder(order.getOrderId(), total);
                                if (updateOrder) {
                                    msg = null;
                                    url = URL_REDIRECT;
                                    session.removeAttribute("PRODUCTCART");
                                    status = true;
                                }
                            }
                        }
                    }
                }
            } else {
                url = URL_LOGIN;
                status = true;
            }
            if (!status) {
                
                request.setAttribute("FAIL", msg);
            }

        } catch (NamingException ex) {
            log("CheckOutServlet_NamingException:" + ex.getMessage());
        } catch (SQLException ex) {
            log("CheckOutServlet_SQLException:" + ex.getMessage());
        } finally {
            if (status) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher rd = request.getRequestDispatcher(url);
                rd.forward(request, response);
            }
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
