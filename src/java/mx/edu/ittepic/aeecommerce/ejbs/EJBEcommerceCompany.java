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
import javax.persistence.LockTimeoutException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TransactionRequiredException;
import mx.edu.ittepic.aeecommerce.entities.Company;
import mx.edu.ittepic.aeecommerce.util.Message;


/**
 *
 * @author Stevens Vera
 */

@Stateless
public class EJBEcommerceCompany {
    @PersistenceContext
    private EntityManager entity;
    
 public String NewCompany(String companyname,String neighborhood,String zipcode,String city,String country,
         String state, String region,String street,String streetnumber,String phone,String rfc,String logo){
      Message m = new Message();
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      Company c = new Company();
      
      c.setCompanyname(companyname);
      c.setNeighborhood(neighborhood);
      c.setZipcode(zipcode);
      c.setCity(city);
      c.setCountry(country);
      c.setState(state);
      c.setRegion(region);
      c.setStreet(street);
      c.setStreetnumber(streetnumber);
      c.setPhone(phone);
      c.setRfc(rfc);
      c.setLogo(logo);
      
       entity.persist(c);
       entity.flush();

        m.setCode(200);
        m.setMsg("ok");
        m.setDetail(c.getCompanyid().toString());
        return gson.toJson(m);
 }
 
 public String UpdateCompany(String companyid ,String companyname,String neighborhood,String zipcode,String city,String country,
         String state, String region,String street,String streetnumber,String phone,String rfc,String logo) 
         {
      Message m = new Message();
      GsonBuilder builder = new GsonBuilder();
      Gson gson = builder.create();
      Company c = entity.find(Company.class, Integer.parseInt(companyid));
      try{
          if(c != null){
          c.setCompanyname(companyname);
          c.setNeighborhood(neighborhood);
          c.setZipcode(zipcode);
          c.setCity(city);
          c.setCountry(country);
          c.setState(state);
          c.setRegion(region);
          c.setStreet(street);
          c.setStreetnumber(streetnumber);
          c.setPhone(phone);
          c.setRfc(rfc);
          c.setLogo(logo);
          
          entity.persist(c);
          entity.flush();

          m.setCode(200);
          m.setMsg("ok");
          m.setDetail(c.getCompanyid().toString());  
          }else{
           m.setCode(404);
           m.setMsg("Error");
           m.setDetail("No encontrado");
          }
      }catch(IllegalArgumentException e){
           m.setCode(406);
           m.setMsg("Error, tipo de dato invalido");
           m.setDetail(e.getMessage());
      }catch (TransactionRequiredException e) {
            m.setCode(403);
            m.setMsg("Error, prohibido");
            m.setDetail(e.getMessage());
        }
    
     return gson.toJson(m);
}
 
 
 public String deleteCompany(String companyid){
     Message m = new Message();
     GsonBuilder builder = new GsonBuilder();
     Gson gson = builder.create();
     Company c = entity.find(Company.class, Integer.parseInt(companyid));
     try{
       if(c !=null){
        entity.remove(c);
        m.setCode(200);
        m.setMsg("ok");
        m.setDetail("todo hermoso tmb");
       }else{
         m.setCode(404);
         m.setMsg("Error");
         m.setDetail("No encontrado");  
       }
     }catch(IllegalArgumentException e){
         m.setCode(406);
         m.setMsg("Error, tipo de dato invalido");
         m.setDetail(e.getMessage());
     }catch (TransactionRequiredException e) {
            m.setCode(403);
            m.setMsg("Error, prohibido");
            m.setDetail(e.getMessage());
        } 
     return gson.toJson(m);
 }
 
   public String findCompany() {
        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Query q;
        List<Company> listCompany;

        try {
            q = entity.createNamedQuery("Company.findAll");
            listCompany = q.getResultList();
            m.setCode(200);
            m.setMsg(gson.toJson(listCompany));
            m.setDetail("todo hermoso tmb");

        } catch (IllegalArgumentException e) {
            m.setCode(406);
            m.setMsg("Error, tipo de dato invalido");
            m.setDetail(e.getMessage());
        } catch (TransactionRequiredException e) {
            m.setCode(403);
            m.setMsg("Error, prohibido");
            m.setDetail(e.getMessage());
        } catch (QueryTimeoutException e) {
            m.setCode(500);
            m.setMsg("Error, algo paso en el server");
            m.setDetail(e.getMessage());
        } catch (PessimisticLockException e) {
            m.setCode(500);
            m.setMsg("Error, algo paso en el server");
            m.setDetail(e.getMessage());
        } catch (LockTimeoutException e) {
            m.setCode(500);
            m.setMsg("Error, algo paso en el server");
            m.setDetail(e.getMessage());
        } catch (PersistenceException e) {
            m.setCode(500);
            m.setMsg("Error, algo paso en el server");
            m.setDetail(e.getMessage());
        }

        return gson.toJson(m);

    }
 

}
