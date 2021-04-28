/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author sasuk
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        String resourcePath = sc.getContextPath() + "/resource";
        String mediaPath = sc.getContextPath() + "/media";
        String apiPath = sc.getContextPath() + "/api";
        sc.setAttribute("PATHMEDIA", mediaPath);
        sc.setAttribute("PATHADMIN", resourcePath + "/admin");
        sc.setAttribute("PATHHOME", resourcePath + "/home");
        sc.setAttribute("PATHAPI", apiPath);
         sc.setAttribute("ADMINURI", sc.getContextPath()+"/admin");
        sc.setAttribute("PATHRESOURCE", resourcePath);
         sc.setAttribute("HOMEURI", sc.getContextPath());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        sc.removeAttribute("INDEXPAGE");
    }

}
