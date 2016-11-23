/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.file.Paths;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import javax.servlet.http.Part;
import mx.edu.ittepic.aeecommerce.entities.Category;
import mx.edu.ittepic.aeecommerce.entities.Product;
import mx.edu.ittepic.aeecommerce.util.Image2;
import mx.edu.ittepic.aeecommerce.util.Message;

/**
 *
 * @author HP
 */
@Stateless
public class ejbEcommerceProducts2 {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext
    EntityManager entity;
    
    public String createProduct(String code, String productname, String brand, String purchprice, String stock,
            String salepricemin, String reorderpoint, String currency, String categoryid, String salepricemay, Part img){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        
        try{
            Category cat = entity.find(Category.class, Integer.parseInt(categoryid));
            
            Product p = new Product();
            p.setBrand(brand);
            p.setCategoryid(cat);
            p.setCode(code);
            p.setCurrency(currency);
            
            p.setProductname(productname);
            p.setPurchprice(Integer.parseInt(purchprice));
            p.setReorderpoint(Integer.parseInt(reorderpoint));
            p.setSalepricemay(Integer.parseInt(salepricemay));
            p.setSalepricemin(Integer.parseInt(salepricemin));
            p.setStock(Integer.parseInt(stock));
            
            entity.persist(p);
            entity.flush();
            
            m.setCode(200);
            m.setMsg("El producto se registro correctamente");
            m.setDetail("Ok id: "+p.getProductid());
            
            //Guardar imagen
            p.setPhoto((new Image2()).createImg(img, "prod", p.getProductid()));
            entity.merge(p);
        }
        catch(NumberFormatException e){
            m.setCode(406);
            m.setMsg("Error de tipo de datos");
            m.setDetail(e.toString());
        }
        
        return gson.toJson(m);
    }
    public String updateProduct(String productid,
            String code, String productname, String brand, String purchprice, String stock, String salepricemin,
            String reorderpoint, String currency, String categoryid, String salepricemay, Part img){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        
        try{
            Product p = entity.find(Product.class, Integer.parseInt(productid));
            
            if(p == null){
                m.setCode(404);
                m.setMsg("No se encontro");
                m.setDetail("");
            }
            else{
                Category cat = entity.find(Category.class, Integer.parseInt(categoryid));
            
                p.setBrand(brand);
                p.setCategoryid(cat);
                p.setCode(code);
                p.setCurrency(currency);
                p.setProductname(productname);
                p.setPurchprice(Integer.parseInt(purchprice));
                p.setReorderpoint(Integer.parseInt(reorderpoint));
                p.setSalepricemay(Integer.parseInt(salepricemay));
                p.setSalepricemin(Integer.parseInt(salepricemin));
                p.setStock(Integer.parseInt(stock));
                
                String nomArchi = Paths.get(img.getSubmittedFileName()).getFileName().toString();
                
                if(! nomArchi.equals("")) //Guardar imagen si selecciono alguna
                    p.setPhoto((new Image2()).updateImg(p.getPhoto(), img, "prod", p.getProductid()));
                
                entity.merge(p);
                
                m.setCode(200);
                m.setMsg("El rol se actualizo correctamente");
                m.setDetail("OK");
            }
            
        }
        catch(NumberFormatException e){
            m.setCode(406);
            m.setMsg("Error de tipo de datos");
            m.setDetail(e.toString());
        }
        catch(IllegalArgumentException e){
            m.setCode(406);
            m.setMsg("Error ilegal argument");
            m.setDetail(e.toString());
        }
        catch(TransactionRequiredException e){
            m.setCode(406);
            m.setMsg("Error transaction");
            m.setDetail(e.toString());
        }
        catch(QueryTimeoutException e){
            m.setCode(406);
            m.setMsg("Error query");
            m.setDetail(e.toString());
        }
        
        return gson.toJson(m);
    }
    
    public String deleteProduct(String productid){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Message m = new Message();
        
        try{
            Product p = entity.find(Product.class, Integer.parseInt(productid));
            String img = p.getPhoto();
            entity.remove(p);
            
            (new Image2()).deleteImg(img);
            
            m.setCode(200);
            m.setMsg("El id:'"+productid+"' fue eliminado");
            m.setDetail("OK");
        }
        catch(NumberFormatException e){
            m.setCode(406);
            m.setMsg("Error de tipo de datos '"+productid+"'");
            m.setDetail(e.toString());
        }
        
        return gson.toJson(m);
    }
}
