/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblcategory;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sasuk
 */
public class TblCategoryDTO implements Serializable{
    private String catName;
    private int catId;
    private String description;
    private Timestamp dateCreate;

    public TblCategoryDTO() {
    }
    
    public TblCategoryDTO(String catName, String description){
        this.catName = catName;
        this.description = description;
    }
    
    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }
    
    
    
}
