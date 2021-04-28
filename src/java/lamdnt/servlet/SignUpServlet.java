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
import lamdnt.tbluser.TblUserCreateErrors;
import lamdnt.tbluser.TblUserDAO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    private final String URL_SIGNIN = "signin.html";
    private final String URL_SIGNUP = "signup.jsp";

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
        String url = URL_SIGNUP;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullName = request.getParameter("txtFullName");
        TblUserCreateErrors erros = new TblUserCreateErrors();
        
        try {
            boolean flag = false;
            if (username.trim().length() < 6 || username.trim().length() > 20) {
                flag = true;
                erros.setUsernameLengthErr("Username requires typing from 6 to 20");
            }
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                flag = true;
                erros.setPasswordLengthErr("Password requires typing from 6 to 30");
            } else if (!confirm.trim().matches(password)) {
                flag = true;
                erros.setConfirmErr("Confirm must match password");
            }
            if (fullName.trim().length() < 6 || fullName.trim().length() > 50) {
                flag = true;
                erros.setFullnameLengthErr("Fullname requires typing from 6 to 50");
            }
            if (!flag) {
                TblUserDAO dao = new TblUserDAO();
                boolean result = dao.createUser(new TblUserDTO(username, password, fullName, 0));
                if (result) {
                    url = URL_SIGNIN;
                }
            } else {
                request.setAttribute("ERRORSIGNUP", "Invalid data try again!");
            }
        } catch (SQLException ex) {
            log("SignUpServlet_SQLException:" + ex.getMessage());
            String errMsg = ex.getMessage();

            if (errMsg.contains("duplicate")) {
                request.setAttribute("ERRORSIGNUP", "User is existed try another.");
            }
        } catch (NamingException ex) {
            log("SignUpServlet_NamingException:" + ex.getMessage());
        } finally {
            if (url.equals(URL_SIGNIN)) {
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
