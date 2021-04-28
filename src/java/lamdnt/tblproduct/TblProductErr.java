/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblproduct;

import java.io.Serializable;

/**
 *
 * @author sasuk
 */
public class TblProductErr implements Serializable{
    private String productNameLengthErr;
    private String descriptionLengthErr;

    public TblProductErr() {
    }

    public String getProductNameLengthErr() {
        return productNameLengthErr;
    }

    public void setProductNameLengthErr(String productNameLengthErr) {
        this.productNameLengthErr = productNameLengthErr;
    }

    public String getDescriptionLengthErr() {
        return descriptionLengthErr;
    }

    public void setDescriptionLengthErr(String descriptionLengthErr) {
        this.descriptionLengthErr = descriptionLengthErr;
    }

    
    
}
