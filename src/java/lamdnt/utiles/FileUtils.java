/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.utiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

/**
 *
 * @author sasuk
 */
public class FileUtils {
    public final static List<String> SUPPORT_FILE = new ArrayList<>(Arrays.asList("image/png","image/jpeg","image/svg+xml"));
    
    public static Hashtable getParamMultiPart(HttpServletRequest request) throws FileUploadException {
        boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
        Hashtable params = null;
        if (isMultiPart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List items = null;
            RequestContext context = new ServletRequestContext(request);
            items = upload.parseRequest(context);
            Iterator iter = items.iterator();
            if (params == null) {
                params = new Hashtable();
            }

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();
                if (item.isFormField()) {
                    params.put(item.getFieldName(), item.getString());
                } else {
                    params.put("image", item);
                }
            }

        }
        return params;
    }
    
    public static boolean uploadImage(FileItem file, String uploadPath, String fileName) throws IOException, Exception {
        if (file.getSize() > 0) {
            String fileType = file.getFieldName();
            if (!fileType.equals("image")) {
                return false;
            }
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            fileName = uploadPath + File.separator + fileName;
            file.write(new File(fileName));
            return true;
        }
        return false;
    }

    public static String renameWhenExistedFile(String fileName, String path) {
        String filePath = path + File.separator + fileName;
        System.out.println(filePath);
        File f = new File(filePath);
        if (f.exists()) {
            
            String[] splitString = filePath.split("\\.");
            long time = new Date().getTime();
            String oldFileName = splitString[0];
            String newFileName = "";
            if (fileName.length() >= 230) {
                newFileName = oldFileName.substring(0, oldFileName.length() - 13) + "" + time;
            } else {
                newFileName = oldFileName + "" + time;
            }
            filePath = newFileName + "." + splitString[1];
        }
        
        return filePath.substring(filePath.lastIndexOf("\\")+1);
    }
    
    public static boolean checkValidContentType(String type){
        for (String t:SUPPORT_FILE){
            if (type.equals(t)){
                return true;
            }
        }
        return false;
    }
}
