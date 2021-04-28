/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblorderitems;

import java.io.Serializable;

/**
 *
 * @author sasuk
 */
public class TblCartDTO implements Serializable{
    private int cartId;
    private String username;

    public TblCartDTO() {
    }

    public TblCartDTO(int cartId, String username) {
        this.cartId = cartId;
        this.username = username;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    
}
