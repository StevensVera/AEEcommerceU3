/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.edu.ittepic.aeecommerce.util.Message;
import mx.edu.ittepic.aeecommerce.util.ProductShoopingCat;

/**
 *
 * @author Stevens Vera
 */
public class EJBEcommerceShoopingCat {
    @PersistenceContext
    private EntityManager entity;
    
     public String NewCompany(String Product,String Codigo,String Cantidad,String PesoUnit,String Imagen){
     Message m = new Message();
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      ProductShoopingCat p = new ProductShoopingCat();
      p.setProduct(Product);
      p.setCodigo(Codigo);
      p.setCantidad((int) Double.parseDouble(Cantidad));
      p.setPesoUnit((int) Double.parseDouble(Cantidad));
      p.setImagen(Imagen);

      entity.persist(p);
       entity.flush();

        m.setCode(200);
        m.setMsg("ok");
        m.setDetail(p.getCodigo());
        return gson.toJson(m);
     }
     /*
Nombre Producto String
Codigo String
Cantidad int
P.U 100
Imagen String

0,1,2,3,4
Total

*/
}
