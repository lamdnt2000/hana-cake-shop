/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamdnt.tblorderitems;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import lamdnt.tblproduct.TblProductDTO;

/**
 *
 * @author sasuk
 */
public class CartObj implements Serializable {

    private Map<TblProductDTO, Integer> items;

    public Map<TblProductDTO, Integer> getItems() {
        return items;
    }


    public void addItemToCart(TblProductDTO product, int quantity) {
        if (this.items == null) {
            items = new HashMap<>();
        }
        int amount = 0;
        if (this.items.containsKey(product)) {
            amount = this.items.get(product);
        }
        this.items.put(product, amount+quantity);
    }

    public void removeItemFromCart(int productId) {
        if (this.items == null) {
            return;
        }
        TblProductDTO product = new TblProductDTO();
        product.setProductId(productId);
        if (this.items.containsKey(product)) {
            this.items.remove(product);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }

    }
    
    public TblProductDTO getProductFromId(int productId){
        if (this.items == null || this.items.isEmpty()){
            return null;
        }
        for (TblProductDTO dto: this.items.keySet()){
            if (dto.getProductId()==productId){
                return dto;
            }
        }
        return null;
    }
    
    public boolean updateQuantity(int productId, int quantity){
        TblProductDTO product = getProductFromId(productId);
        if (product==null){
            return false;
        }
        this.items.put(product, quantity);
        return true;
    }
    public float getTotalPrice() {
        if (this.items == null || this.items.isEmpty()) {
            return 0;
        }
        float total = 0;
        for (TblProductDTO dto : this.items.keySet()) {
            total += dto.getPrice() * items.get(dto);
        }
        return total;
    }
}
