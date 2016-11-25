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
import mx.edu.ittepic.aeecommerce.entities.Category;
import mx.edu.ittepic.aeecommerce.entities.Role;
import mx.edu.ittepic.aeecommerce.util.Message;

/**
 *
 * @author Stevens Vera
 */
@Stateless
public class EJBEcommerceCate {
 @PersistenceContext
    private EntityManager entity;
    
    public String getCategory() {
        List<Category> listCategory;
        Message m = new Message();
        String msg = "";

        Query q = entity.createNamedQuery("Category.findAll");
        listCategory = q.getResultList();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        msg = gson.toJson(listCategory);

        m.setCode(200);
        m.setMsg(msg);
        m.setDetail("Ok");

        return gson.toJson(m);
    }
    public String updateCategory(String categoryid, String  categoryname) {
        Message m = new Message();
        Category c = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
          
            c = entity.find(Category.class, Integer.parseInt(categoryid));
            if (c == null) {
                m.setCode(404);
                m.setMsg("No se encontró el elemento");
                m.setDetail("");
            } else {
                c.setCategoryname(categoryname);
                entity.merge(c);
                m.setCode(200);
                m.setMsg("El rol se actualizo correctamente");
                m.setDetail("ok: ");
            }
        } catch (NumberFormatException e) {
            m.setCode(406);
            m.setMsg("Error, el tipo de dato '" + categoryid + "' debe ser entero");
            m.setDetail(e.getMessage());
        } catch (IllegalArgumentException e) {
            m.setCode(406);
            m.setMsg("Error, el id '" + categoryid + "' ya se ha usado previamente");
            m.setDetail(e.getMessage());
        }

        return gson.toJson(m);
    }
    
    public String createCategory(String categoryid, String categoryname) {
        Message m = new Message();
        Category c = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            //r.setRoleid(Integer.parseInt(roleid));
            c.setCategoryname(categoryname);
            entity.persist(c);
            entity.flush();
            m.setCode(200);
            m.setMsg("El rol se insertó correctamente");
            m.setDetail("ok: " + c.getCategoryid());
        } catch (Exception e) {

        }
        return gson.toJson(m);
    }
    
    public String getCategory(String categoryid) {
        Message m = new Message();
        Category c = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            /*
            r = entity.find(Role.class, Integer.parseInt(roleid));
            m.setCode(200);
            m.setMsg(gson.toJson(r));
            m.setDetail("ok");
            */
            Query q= entity.createNativeQuery("Select categoryid,categoryname from category where roleid="+categoryid, Category.class);
            c=(Category) q.getSingleResult();
            m.setCode(200);
            m.setMsg(gson.toJson(c));
            m.setDetail("ok");
            
        } catch (Exception e) {

        }
        return gson.toJson(m);
    }
    
    public String getCategoryByName(String categoryname) {
        Message m = new Message();
        Category r = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNativeQuery("select * from category where categoryname like '" + categoryname + "%'", Category.class)
                    .setParameter("name", categoryname + "%");
            List<Role> list = q.getResultList();

            m.setCode(200);
            m.setMsg(gson.toJson(list));
            m.setDetail("ok");
        } catch (IllegalArgumentException e) {
            m.setCode(406);
            m.setMsg("Error, el tipo de dato '" + categoryname + "' debe ser string");
            m.setDetail(e.getMessage());
        } catch (TransactionRequiredException e) {
            m.setCode(406);
            m.setMsg("Error, algo paso con la base de datos");
            m.setDetail(e.getMessage());
        }
        //catch(NumberFormatException e){}
        return gson.toJson(m);
    }
    
    public String deleteCategory(String categoryid) {
        Message m = new Message();
        Category c = new Category();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            c = entity.find(Category.class, Integer.parseInt(categoryid));
            entity.remove(c);
            m.setCode(200);
            m.setMsg("eliminado correctamente");
            m.setDetail("ok");
        } catch (IllegalArgumentException e) {
            m.setCode(406);
            m.setMsg("Error, el tipo de dato '" + categoryid + "' debe ser entero");
            m.setDetail(e.getMessage());
        } catch (TransactionRequiredException e) {
            m.setCode(406);
            m.setMsg("Error, algo paso con la base de datos");
            m.setDetail(e.getMessage());
        }
        //catch(NumberFormatException e){}

        return gson.toJson(m);
    }
   
}
