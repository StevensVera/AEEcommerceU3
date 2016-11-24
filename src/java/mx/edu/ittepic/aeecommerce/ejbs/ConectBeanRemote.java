/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.ejbs;

import javax.ejb.Remote;

/**
 *
 * @author luis
 */
@Remote
public interface ConectBeanRemote {
    public String addProduct(String code,String photo,int stock, String productname,double purchprice,int id);
    public String removeProduct(String code);
     public void removeProducts();
    public void remove();
    public void initialize();
    public String getCart();
    public String CheckOut(String amount,String date, String userid, String saleprice);
    public String getUsersByNameLogin(String username,String password);
    public String getUsername();
   // public void setUsername(String username);
    public int getUserid();
   // public void setUserid(int userid) ;
    
}
