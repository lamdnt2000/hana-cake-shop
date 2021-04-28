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
public class FilterParam implements Serializable {

    private String searchName;
    private int catId;
    private int status;
    private int minPrice;
    private int maxPrice;
    private int role;
    private int page;

    public FilterParam() {
    }

    public FilterParam(String searchName, int catId, int status, int minPrice, int maxPrice, int role, int page) {
        this.searchName = searchName;
        this.catId = catId;
        this.status = status;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.role = role;
        this.page = page;
    }

    public String getSearchName() {
        return searchName;
    }

    public int getCatId() {
        return catId;
    }

    public int getStatus() {
        return status;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public int getRole() {
        return role;
    }

    public int getPage() {
        return page;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public void setPage(int page) {
        this.page = page;
    }
    
    
    
    
    
    
}
