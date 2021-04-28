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
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lamdnt.tblcategory.TblCategoryDAO;
import lamdnt.tblcategory.TblCategoryDTO;
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
@WebServlet(name = "CreateProductServlet", urlPatterns = {"/admin/CreateProductServlet"})
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 50
)
public class CreateProductServlet extends HttpServlet {

    final String UPLOAD_DIRECTORY = "media";

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
        String url = "createproduct.jsp";
        String productName = (String) params.get("txtProductName");
        String description = (String) params.get("txtDescription");
        String productPrice = (String) params.get("productPrice");
        String productAmount = (String) params.get("productAmount");
        String productCatId = (String) params.get("catId");
        String status = (String) params.get("status");
        FileItem file = (FileItem) params.get("image");
        String image = "";
        float price = -1;
        int amount = -1;
        int catId = -1;
        boolean access = false;
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
                } catch (NumberFormatException e) {
                    log("CreateProductServlet_NumberFormatException:" + e.getMessage());
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
                if (!FileUtils.checkValidContentType(file.getContentType())) {
                    flag = false;
                }
                request.setAttribute("PARAMS", params);
                if (flag) {
                    TblCategoryDAO daoCat = new TblCategoryDAO();
                    daoCat.findAllCategory();
                    List<TblCategoryDTO> listCategory = daoCat.getListCategory();
                    request.setAttribute("CATEGORYRESULT", listCategory);

                    TblProductDAO dao = new TblProductDAO();
                    String uploadPath = request.getServletContext().getRealPath("") + UPLOAD_DIRECTORY;

                    if (file.getSize() > 0) {
                        image = FileUtils.renameWhenExistedFile(file.getName(), uploadPath);
                    }
                    TblProductDTO dto = new TblProductDTO(0, productName, description, price, amount, image, "on".equals(status) ? 1 : 0, catId);

                    dto.setUserCreate(user.getUsername());
                    boolean result = dao.createProduct(dto);
                    if (result) {
                        request.setAttribute("CREATESUCCESS", "Create new product success");
                        FileUtils.uploadImage(file, uploadPath, image);
                    } else {
                        request.setAttribute("CREATEFAIL", "Create new product failed");
                    }
                } else {
                    request.setAttribute("CREATEFAIL", "Invalid format try again!");
                }
            }

        } catch (SQLException ex) {
            log("CreateProductServlet_SQLException:" + ex.getMessage());
        } catch (NamingException ex) {
            log("CreateProductServlet_NamingException:" + ex.getMessage());
        } catch (IOException ex) {
            log("CreateProductServlet_IOException:" + ex.getMessage());
        } catch (Exception ex) {
            log("CreateProductServlet_Exception:" + ex.getMessage());
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
