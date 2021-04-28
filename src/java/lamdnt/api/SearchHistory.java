/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.api;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblorder.TblOrderDAO;
import lamdnt.tblorder.TblOrderDTO;
import lamdnt.tbluser.TblUserDTO;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "SearchHistory", urlPatterns = {"/SearchHistory"})
public class SearchHistory extends HttpServlet {

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
        String txtSearchName = request.getParameter("txtSearchName");
        String txtDateBuy = request.getParameter("dateBuy");
        System.out.println(txtDateBuy);
        boolean access = false;
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBuy = null;
        try {
            /* TODO output your page here. You may use following sample code. */
            if (txtDateBuy != "") {
                try {
                    dateBuy = sdf.parse(txtDateBuy);

                } catch (ParseException ex) {
                    log("SearchHistory_ParseException: " + ex.getMessage());
                }
            }
            if (access) {
                TblOrderDAO dao = new TblOrderDAO();
                dao.searchHistory(txtSearchName, dateBuy, user.getUsername());
                List<TblOrderDTO> list = dao.getListHistory();
                String json = new Gson().toJson(list);
                out.println(json);
            }

        } catch (NamingException ex) {
            log("SearchHistory_NamingException: " + ex.getMessage());
        } catch (SQLException ex) {
            log("SearchHistory_SQLException: " + ex.getMessage());
        } finally {
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
