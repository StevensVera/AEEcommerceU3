/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.ejbs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import mx.edu.ittepic.aeecommerce.entities.Product;
import mx.edu.ittepic.aeecommerce.entities.Sale;
import mx.edu.ittepic.aeecommerce.entities.Salesline;
import mx.edu.ittepic.aeecommerce.entities.Users;
import mx.edu.ittepic.aeecommerce.entities.Carritows;
import mx.edu.ittepic.aeecommerce.util.Message;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author roberto
 */
@Stateless
@Path("/ejercicios")
public class EjerciciosServices {
   @PersistenceContext
    private EntityManager entity;
   
   
    @POST
    @Path("/usuario")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public String usuario(String json){
        String username=js.getString("username");
        String password=(js.getString("password"));
        password=DigestUtils.md5Hex(password);
        Message m=new Message();
        GsonBuilder b=new GsonBuilder();
        Gson g = b.create();
        String msg;
     //Query q = entity.createNamedQuery("Users.findByLogin").setParameter("username",u).setParameter("password",pa);
        try {
             Users u;
        Query q = entity.createNamedQuery("Users.findByUsername").
        setParameter("username", username);
        
        
        u = (Users) q.getSingleResult();
       
        if(u.getPassword().equals(password)){
        System.out.println(password);
        m.setCode(200);
        m.setMsg(g.toJson(u));
        m.setDetail("ok");
        return g.toJson(m);
        }
  }
        catch (NoResultException e) {
            m.setCode(406);
            m.setMsg("No tienes permiso");
            m.setDetail(e.toString());
        }
      
      
        return g.toJson(m);   
        
        
    }
    @PUT
    @Path("/updateusuario")
    @Produces({MediaType.APPLICATION_JSON})
    public String updateusuario(JsonObject js){
        String api=js.getString("apikey");
        GsonBuilder b=new GsonBuilder();
        Gson g = b.create();
        Message m=new Message();
        
        if(!validarApiKey(api)){
            m.setCode(406);
            m.setMsg("No tienes permiso");
            m.setDetail("Fail");
            return g.toJson(m);   
        }
        
        int u=js.getInt("id");
        String pa=(js.getString("password"));       
        String msg;
     Query q = entity.createNamedQuery("Users.findByUserid").setParameter("userid",u);
        try {
            Users p=(Users) q.getSingleResult();
            p.setPassword(DigestUtils.md5Hex(pa));
            entity.merge(p);
            msg = g.toJson(p);
        m.setCode(200);
        m.setMsg(msg);
        m.setDetail("ok");
        return g.toJson(m);
  }
        catch (NoResultException e) {
            m.setCode(406);
            m.setMsg("El id de usuario no Existe");
            m.setDetail(e.toString());
        }
      
      
        return g.toJson(m);   
        
        
    }
  @GET
  @Path("/productos")
  @Produces({MediaType.APPLICATION_JSON})  
   public String get() {
         List<Product> listPro;
        Message m= new Message();
        
        GsonBuilder builder = new GsonBuilder();
        Gson gson= builder.create();
        try{
        Query q= entity.createNamedQuery("Product.findAll");
        listPro = q.getResultList();
        
        String name="[";
        for(Product listP:listPro){
        name=name+"{\"productid\":"+(listP.getProductid())+",\"code\":\""+(listP.getCode())+"\",\"productname\":\""+(listP.getProductname())+"\",\"brand\":\""+(listP.getBrand())+"\",\"purchprice\":"+(listP.getPurchprice())+",\"stock\":"+(listP.getStock())+",\"salepricemin\":"+(listP.getSalepricemin())+",\"reorderpoint\":"+(listP.getReorderpoint())+",\"currency\":\""+(listP.getCurrency())+"\",\"salepricemay\":"+(listP.getSalepricemay())+",\"category\":\""+(listP.getCategoryid().getCategoryname())+"\"},";  
        
        }
       
        name=name.substring(0,name.length()-1);
        name=name+"]";
        
        
        
        m.setCode(200);
        m.setMsg(name);
        m.setDetail("OK");
        }catch(IllegalArgumentException e){
        m.setCode(406);
        m.setMsg("Error Ilegal de tipo de dato");
        m.setDetail("Advertencia");
        
        }
        catch(NoResultException e){
        m.setCode(406);
        m.setMsg("No se encontro registro con ese nombre");
        m.setDetail("Advertencia");
        
        }
        return gson.toJson(m);
      
    }
   @POST
  @Path("/newproduct")
  @Produces({MediaType.APPLICATION_JSON})
   public String newproduct(JsonObject js){
       int userid =js.getInt("userid");
       int productid =js.getInt("productid");
       int cant =js.getInt("cantidad");
       String api=js.getString("apikey");
       
        GsonBuilder builder=new GsonBuilder();
        Gson gson = builder.create();
        Message m=new Message();
        
        if(!validarApiKey(api)){
            m.setCode(406);
            m.setMsg("No tienes permiso");
            m.setDetail("Fail");
            return gson.toJson(m);   
        }
        
        try{
            Product pr = entity.find(Product.class, productid);
            
            String code = pr.getCode();
            String name = pr.getProductname();
            String pu = pr.getPurchprice()+"";
            String img = pr.getPhoto();
            
            m.setCode(200);
            m.setDetail("OK");
            
            boolean esta = false;
            
            List<Carritows> kart;
            Query q = entity.createNamedQuery("Carritows.findByUserid").setParameter("userid", userid);
            kart = q.getResultList();
            
            for(Carritows p : kart)
                if(p.getCode().equals(code)){
                    int c = p.getCant();
                    c += Integer.parseInt(cant+"");
                    
                    p.setCant(c);
                    entity.merge(p);
                    m.setMsg("Cantidad actualizada");
                    
                    esta = true;
                }
            
            if(!esta){
                Carritows p = new Carritows();
                p.setProductid(productid);
                p.setCode(code);
                p.setName(name);
                p.setCant(cant);
                p.setPu(Double.parseDouble(pu));
                p.setImg(img);
                p.setUserid(userid);

                entity.persist(p);
                m.setMsg("Producto agregado al carrito");
            }
            
            entity.flush();
            kart = q.getResultList();
            m.setMsg(gson.toJson(kart));
        }
        catch(NumberFormatException e){
            m.setCode(406);
            m.setMsg("Error en el tipo de datos");
            m.setDetail("OK");
        }
        
        return gson.toJson(m);
   }
   
   @POST
    @Path("/createsale")
    @Produces({MediaType.APPLICATION_JSON})
   public String createSale(JsonObject js){
       int userid =js.getInt("userid");
       
       String api=js.getString("apikey");
        GsonBuilder builder=new GsonBuilder();
        Gson gson = builder.create();
        Message m=new Message();
        
        if(!validarApiKey(api)){
            m.setCode(406);
            m.setMsg("No tienes permiso");
            m.setDetail("Fail");
            return gson.toJson(m);   
        }
        
        String res = "";
        Double tot = 0.0;
        
        List<Carritows> kart;
        Query q = entity.createNamedQuery("Carritows.findByUserid").setParameter("userid", userid);
        kart = q.getResultList();
        
        for(int i=0; i<kart.size(); i++){
            Carritows p = kart.get(i);
            Product pr = entity.find(Product.class, p.getProductid());
            
            tot += (p.getCant() * p.getPu());
            if(pr.getStock() < p.getCant())
                res += "No hay suficiente "+p.getName()+". Disponible: "+pr.getStock()+"\n";
        }
        
        m.setCode(400);
        
        if(res.equals("")){
            if(kart.size() > 0)
                m = registerSale(tot, userid, kart);
            else
                m.setMsg("Carrito vacio");
        }
        else
            m.setMsg(res);
        
        m.setDetail("OK");
        
        return gson.toJson(m);
    }
    
    private Message registerSale(Double total, int userid, List<Carritows> kart){
        Message m = new Message();
        
        Users u = entity.find(Users.class, userid);
        
        Sale s = new Sale();
        s.setAmount(total);
        s.setDate(Calendar.getInstance().getTime());
        s.setUserid(u);
        s.setSaleslineList(null);
        /*Actualiza la BD*/
        entity.persist(s);
        entity.flush();
        
        for(int i=0; i<kart.size(); i++){
            Carritows p = kart.get(i);
            Product pr = entity.find(Product.class, p.getProductid());
            
            Salesline sl = new Salesline();
            sl.setProductid(pr);
            sl.setPurchprice(p.getPu());
            sl.setQuantity(p.getCant());
            sl.setSaleid(s);
            sl.setSaleprice(p.getPu());
            
            entity.persist(sl);
            
            pr.setStock(pr.getStock() - p.getCant());
            
            entity.remove(p);
            entity.merge(pr);
        }
        
        m.setCode(200);
        m.setMsg("Total venta: "+total+" id: "+s.getSaleid());
        
        
        
        return m;
    }
    
    private boolean validarApiKey(String api){
        try{
          Query q = entity.createNamedQuery("Users.findByApikey").setParameter("apikey",api);  
          Users p=(Users) q.getSingleResult(); 
          
          if(!p.getRoleid().getRolename().equalsIgnoreCase("cliente"))
              return false;
        }
        catch (NoResultException e) {
            return false;
        }
        
        return true;
    }
}
