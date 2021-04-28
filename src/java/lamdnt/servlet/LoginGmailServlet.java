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
import lamdnt.tbluser.GooglePojo;
import lamdnt.tbluser.TblUserDAO;
import lamdnt.tbluser.TblUserDTO;
import lamdnt.utiles.GoogleUtils;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "LoginGmailServlet", urlPatterns = {"/login_google"})
public class LoginGmailServlet extends HttpServlet {

    private static final String URL_LOGIN = "signin.jsp";
    private static final String URL_HOME = "index.jsp";

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
        String code = request.getParameter("code");
        try {
            if (code == null || code.isEmpty()) {
                RequestDispatcher dis = request.getRequestDispatcher(URL_LOGIN);
                dis.forward(request, response);
            } else {
                String accessToken = GoogleUtils.getToken(code);
                GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
                TblUserDAO dao = new TblUserDAO();
                String userId = googlePojo.getId();
                if (!dao.findUser(userId)) {
                    dao.createUser(new TblUserDTO(userId, "123456", googlePojo.getEmail(), 0));
                }
                HttpSession session = request.getSession(true);
                TblUserDTO user = new TblUserDTO(userId, null, googlePojo.getEmail(), 0);
                session.setAttribute("RESULTLOGIN", user);
            }
        } catch (SQLException ex) {
            log("LoginGmailServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginGmailServlet_NamingException:" + ex.getMessage());
        } finally {
            response.sendRedirect(URL_HOME);
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
