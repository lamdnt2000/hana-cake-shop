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
import lamdnt.tblcategory.TblCategoryDAO;
import lamdnt.tblcategory.TblCategoryDTO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "CreateCategoryServlet", urlPatterns = {"/admin/CreateCategoryServlet"})
public class CreateCategoryServlet extends HttpServlet {

    private final String CATEGORY = "category.jsp";
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
        String catName = request.getParameter("txtCatName");
        String description = request.getParameter("txtDescription");
        boolean flag = true;
        String url = CATEGORY;
        boolean access = false;
        try {
            /* TODO output your page here. You may use following sample code. */

            HttpSession session = request.getSession(false);
            if (session != null) {
                TblUserDTO user = (TblUserDTO) session.getAttribute("RESULTLOGIN");
                if (user != null) {
                    if (user.getRole() == 1) {
                        access = true;
                    }
                }
            }

            if (access) {
               
                if (catName.trim().length() < 3 || catName.trim().length() > 20) {
                    
                    flag = false;
                }
                if (flag) {
                    TblCategoryDAO dao = new TblCategoryDAO();
                    TblCategoryDTO dto = new TblCategoryDTO(catName, description);
                    boolean result = dao.createCategory(dto);
                    if (result) {
                        request.setAttribute("CREATESUCCESS", "Create new category success");
                    }
                    else{
                        request.setAttribute("CREATEFAIL","Create new category failed");
                    }

                }
                else{
                    request.setAttribute("CREATEFAIL","Invalid data");
                }
            }
        } catch (SQLException ex) {
            log("CreateCategoryServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateCategoryServlet_NamingException:" + ex.getMessage());
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
