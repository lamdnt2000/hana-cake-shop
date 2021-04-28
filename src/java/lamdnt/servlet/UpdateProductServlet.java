/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Hashtable;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblcategory.TblCategoryDAO;
import lamdnt.tblproduct.TblProductDAO;
import lamdnt.tblproduct.TblProductDTO;
import lamdnt.tbluser.TblUserDTO;
import lamdnt.utiles.FileUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;

/**
 *
 * @author sasuk
 */
@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/admin/UpdateProductServlet"})
public class UpdateProductServlet extends HttpServlet {

    final static String URL_DEFAULT = "productdetail.jsp";
    final static String URL_REDIRECT = "product.jsp";
    final static String UPLOAD_DIRECTORY = "media";

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
        Hashtable params = null;
        try {
            params = FileUtils.getParamMultiPart(request);
        } catch (FileUploadException ex) {
            log("CreateProductServlet_FileUploadException:" + ex.getMessage());
        }
        String productId = (String) params.get("id");
        String productName = (String) params.get("txtProductName");
        String description = (String) params.get("txtDescription");
        String productPrice = (String) params.get("productPrice");
        String productAmount = (String) params.get("productAmount");
        String productCatId = (String) params.get("catId");
        String catIdSearch = (String) params.get("catIdSearch");
        String statusSearch = (String) params.get("statusSearch");
        String status = (String) params.get("status");
        FileItem file = (FileItem) params.get("image");
        String image = (String) params.get("filename");
        String url = "DispatcherServlet?btAction=GETDETAILPRODUCT&id="+productId+"&catId="+catIdSearch+"&status="+statusSearch;
        float price = 0;
        int amount = 0;
        int catId = 0;
        int id = 0;
        boolean access = false;
        boolean redirect = false;
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
                boolean flag = true;
                
                TblCategoryDAO catDAO = new TblCategoryDAO();
                if (productName.length() < 6 || productName.length() > 30) {
                    flag = false;
                }
                try {
                    price = Float.parseFloat(productPrice);
                    amount = Integer.parseInt(productAmount);
                    catId = Integer.parseInt(productCatId);
                    id = Integer.parseInt(productId);
                } catch (NumberFormatException e) {
                    log("UpdateProductServlet_NumberFormatException:" + e.getMessage());
                    flag = false;
                }
                if (price < 0 || price > 1000000000) {
                    flag = false;
                }
                if (amount < 0 || amount > 10000) {
                    flag = false;
                }
                if (catDAO.findCategoryById(catId) == null) {
                    flag = false;
                }

                if (flag) {
                    TblProductDAO dao = new TblProductDAO();
                    String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY;

                    if (file.getSize() > 0) {
                        image = FileUtils.renameWhenExistedFile(file.getName(), uploadPath);
                    }
                    TblProductDTO dto = new TblProductDTO(id, productName, description, price, amount, image, "on".equals(status) ? 1 : 0, catId);
                    dto.setUserUpdate(user.getUsername());
                    boolean result = dao.updateProduct(dto);
                    if (result) {
                        FileUtils.uploadImage(file, uploadPath, image);
                        url = URL_REDIRECT + "?catId=" + catIdSearch
                                + "&status=" + statusSearch;
                        redirect=true;
                    } else {
                        request.setAttribute("CREATEFAILED", "Update product failed");

                    }
                }
                else{
                    request.setAttribute("CREATEFAILED", "Invalid data try again");
                }
            }

        } catch (SQLException ex) {
            log("UpdateProductServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("UpdateProductServlet_NamingException:" + ex.getMessage());
        } catch (IOException ex) {
            log("UpdateProductServlet_IOException:" + ex.getMessage());
        } catch (Exception ex) {
            log("UpdateProductServlet_Exception:" + ex.getMessage());
        } finally {
            if (redirect) {
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
