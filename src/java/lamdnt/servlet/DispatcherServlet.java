/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "DispatcherServlet", urlPatterns = {"/DispatcherServlet","/admin/DispatcherServlet"})
public class DispatcherServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    final String LOGIN_PAGE = "LOGIN";
    final String LOGIN_PAGE_CONTROLLER = "LoginServlet";
    final String SIGNUP_PAGE = "SIGNUP";
    final String SIGNUP_PAGE_CONTROLLER = "SignUpServlet";
    final String CREATE_CATEGORY = "CREATECATEGORY";
    final String CREATE_CATEGORY_CONTROL = "CreateCategoryServlet";
    final String CREATE_PRODUCT ="CREATEPRODUCT";
    final String CREATE_PRODUCT_CONTROL ="CreateProductServlet";
    final String SEARCH_PRODUCT ="SEARCHPRODUCT";
    final String SEARCH_PRODUCT_CONTROL ="SearchProductServlet";
    final String DELETE_PRODUCT ="DELETEPRODUCT";
    final String DELETE_PRODUCT_CONTROL ="DeleteProductServlet";
    final String GET_DETAIL_PRODUCT = "GETDETAILPRODUCT";
    final String GET_DETAIL_PRODUCT_CONTROL = "GetProductDetailServlet";
    final String UPDATE_PRODUCT = "UPDATEPRODUCT";
    final String UPDATE_PRODUCT_CONTROL = "UpdateProductServlet";
    final String SEARCH_PRODUCT_HOME = "Search Product";
    final String SEARCH_PRODUCT_HOME_CONTROL = "SearchProductHomeServlet";
    final String ADD_TO_CART = "ADDTOCART";
    final String ADD_TO_CART_CONTROL = "AddProductToCartServlet";
    final String UPDATE_CART ="UPDATECART";
    final String UPDATE_CART_CONTROL ="UpdateCartServlet";
    final String DELETE_CART ="DELETECART";
    final String DELETE_CART_CONTROL ="DeleteCartServlet";
    final String CHECK_OUT_CART ="CHECKOUT";
    final String CHECK_OUT_CART_CONTROL ="CheckOutServlet";
    
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String button = request.getParameter("btAction");
        String url="index.jsp"; 
        try{
            /* TODO output your page here. You may use following sample code. */
            if (LOGIN_PAGE.equals(button)){
                url=LOGIN_PAGE_CONTROLLER;
            }
            else if (SIGNUP_PAGE.equals(button)){
                url=SIGNUP_PAGE_CONTROLLER;
            }
            else if (CREATE_CATEGORY.equals(button)){
                url = CREATE_CATEGORY_CONTROL;
            }
            else if (CREATE_PRODUCT.equals(button)){
                url = CREATE_PRODUCT_CONTROL;
            }
            else if (SEARCH_PRODUCT.equals(button)){
                url = SEARCH_PRODUCT_CONTROL;
            }
            else if (DELETE_PRODUCT.equals(button)){
                url = DELETE_PRODUCT_CONTROL;
            }
            else if (GET_DETAIL_PRODUCT.equals(button)){
                url = GET_DETAIL_PRODUCT_CONTROL;
            }
            else if (UPDATE_PRODUCT.equals(button)){
                url = UPDATE_PRODUCT_CONTROL;
            }
            else if (SEARCH_PRODUCT_HOME.equals(button)){
                url = SEARCH_PRODUCT_HOME_CONTROL;
            }
            else if (ADD_TO_CART.equals(button)){
                url = ADD_TO_CART_CONTROL;
            }
            else if (DELETE_CART.equals(button)){
                url = DELETE_CART_CONTROL;
            }
            else if (UPDATE_CART.equals(button)){
                url = UPDATE_CART_CONTROL;
            }
            else if (CHECK_OUT_CART.equals(button)){
                url = CHECK_OUT_CART_CONTROL;
            }
            
        }
        catch (Exception ex){
             log("DispatcherServlet_NamingException:" + ex.getMessage());
        }
        finally{
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
