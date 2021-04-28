/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblorder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author sasuk
 */
public class TblOrderDTO implements Serializable{
    private int orderId;
    private String customerId;
    private float total;
    private int status;
    private Timestamp dateCreate;

    public TblOrderDTO() {
    }

    public TblOrderDTO(int orderId, String customerId, int status, Timestamp dateCreate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.status = status;
        this.dateCreate = dateCreate;
    }

    
    
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Timestamp dateCreate) {
        this.dateCreate = dateCreate;
    }
    
    
    
}
