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
import lamdnt.tbluser.TblUserDAO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet", "/admin/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String URL_LOGIN = "signin.jsp";
    private final String URL_HOME = "index.jsp";
    private final String HOME_ADMIN_PATH = "admin";

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
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        boolean flag = true;
        String path = request.getRequestURI();
        String url = URL_LOGIN;
        try {
            /* TODO output your page here. You may use following sample code. */
            TblUserDAO dao = new TblUserDAO();
            TblUserDTO dto = dao.checkLogin(username, password);
            HttpSession session = request.getSession(true);
            if (dto != null) {
                session.setAttribute("RESULTLOGIN", dto);
                url = request.getContextPath() + "/" + HOME_ADMIN_PATH + "/" + URL_HOME;
                if (dto.getRole() == 0) {
                    url = URL_HOME;
                }

            } else {
                request.setAttribute("ERRORlOGIN", "User is not found");
                flag = false;
            }

        } catch (SQLException ex) {
            log("LoginServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet_NamingException:" + ex.getMessage());
        } finally {
            if (flag) {
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
