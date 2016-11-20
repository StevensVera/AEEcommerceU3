/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TransactionRequiredException;
import mx.edu.ittepic.aeecommerce.entities.Role;
import mx.edu.ittepic.aeecommerce.entities.Users;
import mx.edu.ittepic.aeecommerce.util.Message;

/**
 *
 * @author Stevens Vera
 */
@Stateless
public class EJBEcommercerUsers {
 @PersistenceContext
    private EntityManager entity;   
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

 
 public String getUsersByName(String nameusers) {
        Message m = new Message();
        Users r = new Users();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNativeQuery("select * from role where rolename like '" + nameusers + "%'", Role.class)
                    .setParameter("name",nameusers + "%");
            List<Role> list = q.getResultList();

            m.setCode(200);
            m.setMsg(gson.toJson(list));
            m.setDetail("ok");
        } catch (IllegalArgumentException e) {
            m.setCode(406);
            m.setMsg("Error, el tipo de dato '" + nameusers + "' debe ser string");
            m.setDetail(e.getMessage());
        } catch (TransactionRequiredException e) {
            m.setCode(406);
            m.setMsg("Error, algo paso con la base de datos");
            m.setDetail(e.getMessage());
        }
        //catch(NumberFormatException e){}
        return gson.toJson(m);
    }
 
 
  public String NewUser(String userid, String username,String password) {
          Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Users p = new Users();
        p.setUserid(Integer.parseInt(userid));
        p.setUsername(username);
        p.setPassword(password);
        
        entity.persist(p);
        entity.flush();
        
        m.setCode(200);
        m.setMsg("ok");
        m.setDetail(p.getUserid().toString());
        return gson.toJson(m);
}
/*
  public String newProduct(String code, String brand, String purchprice, String productname, String stock,
            String salepricemin, String salepricemay, String reorderpoint, String categoryid, String curency) {

        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Product p = new Product();
        Category c = entity.find(Category.class, Integer.parseInt(categoryid));
        p.setCode(code);
        p.setBrand(brand);
        p.setPurchprice(Integer.parseInt(purchprice));
        p.setProductname(productname);
        p.setStock(Integer.parseInt(stock));
        p.setSalepricemin(Integer.parseInt(salepricemin));
        p.setSalepricemay(Integer.parseInt(salepricemay));
        p.setReorderpoint(Integer.parseInt(reorderpoint));
        p.setCurrency(curency);
        p.setCategoryid(c);

        entity.persist(p);
        entity.flush();

        m.setCode(200);
        m.setMsg("todo hermoso");
        m.setDetail(p.getProductid().toString());
        return gson.toJson(m);

    }
  
  */
}