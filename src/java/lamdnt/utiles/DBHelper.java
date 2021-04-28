/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.utiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author sasuk
 */
public class DBHelper implements Serializable {
    public static Connection makeConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:/comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("lamdnt");
        Connection con = ds.getConnection();
        return con;
    }

    

    public static String convertListToJson(List list, int counter) {
        Gson gson = new GsonBuilder().create();
        JsonArray jarray = gson.toJsonTree(list).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("data", jarray);
        jsonObject.addProperty("counter", counter);
        return jsonObject.toString();
    }

    
}
