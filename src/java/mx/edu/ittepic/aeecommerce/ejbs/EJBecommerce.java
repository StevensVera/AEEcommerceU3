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
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.LockTimeoutException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.PessimisticLockException;
import javax.persistence.Query;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import mx.edu.ittepic.aeecommerce.entities.Company;
//import mx.edu.ittepic.aeecommerce.entities.Product;
import mx.edu.ittepic.aeecommerce.entities.Role;
import mx.edu.ittepic.aeecommerce.entities.Users;
import mx.edu.ittepic.aeecommerce.util.Message;

/**
 *
 * @author Stevens Vera
 */
@Stateless
public class EJBecommerce {

    @PersistenceContext
    EntityManager entity;

    public String login(String user, String password) {
        Message m = new Message();
        Users u = new Users();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        try {
            Query q = entity.createNamedQuery("Users.login")
                    .setParameter("username", user)
                    .setParameter("password", password);
            u = (Users) q.getSingleResult();
            m.setCode(200);
            m.setMsg("OK");
            m.setDetail("Login correcto");
        } catch (NoResultException e) {
            m.setCode(403);
            m.setMsg("No autorizado");
            m.setDetail(e.toString());
        }

        return gson.toJson(m);

    }

    public String newUser(String username, String password, String phone, String neigborhood, String zipcode,
            String city, String country, String state, String region, String street, String email,
            String streetnumber, String photo, String cellphone, String companyid, String roleid, String gender) {

        Message m = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Users u = new Users();
        Company c;
        Role r;

        try {
            c = entity.find(Company.class, Integer.parseInt(companyid));

            r = entity.find(Role.class, Integer.parseInt(roleid));

            u.setRoleid(r);
            u.setCompanyid(c);
            u.setCellphone(cellphone);
            u.setCity(city);
            u.setRegion(region);
            u.setCountry(country);
            u.setEmail(email);
            u.setGender(gender.charAt(0));
            u.setNeigborhood(neigborhood);
            u.setPassword(password);
            u.setPhone(phone);
            u.setPhoto(photo);
            u.setState(state);
            u.setStreet(street);
            u.setStreetnumber(streetnumber);
            u.setUsername(username);
            u.setZipcode(zipcode);

            entity.persist(u);
            entity.flush();

            m.setCode(200);
            m.setMsg("El usuario se creó correctamente");
            m.setDetail(u.getUserid() + "");
        } catch (NumberFormatException e) {
            m.setCode(406);
            m.setMsg("Error de tipo de dato '" + username + "'.");
            m.setDetail(e.toString());
        } catch (EntityExistsException e) {
            m.setCode(400);
            m.setMsg("El usuario que intentas ingresar ya existe: '" + username + "'.");
            m.setDetail(e.toString());
        } catch (IllegalArgumentException e) {
            m.setCode(422);
            m.setMsg("Error de entidad, el usuario no es una entidad o ha sido removido.");
            m.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            m.setCode(509);
            m.setMsg("La transacción no pudo ser completada. Espera un momento y vuelve a intentar.");
            m.setDetail(e.toString());
        } catch (EntityNotFoundException e) {
            m.setCode(404);
            m.setMsg("El usuario introducido(" + username + ") no existe, no se puede actualizar.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String updateUser(String username, String userid, String phone, String neigborhood, String zipcode,
            String city, String country, String state, String region, String street, String email,
            String streetnumber, String photo, String cellphone, String companyid, String roleid, String gender) {
        Message m = new Message();
        Company c;
        Role r;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        c = entity.find(Company.class, Integer.parseInt(companyid));

        r = entity.find(Role.class, Integer.parseInt(roleid));

        Query q = entity.createNamedQuery("Users.updateUser")
                .setParameter("username", username)
                .setParameter("phone", phone)
                .setParameter("neigborhood", neigborhood)
                .setParameter("zipcode", zipcode)
                .setParameter("city", city)
                .setParameter("country", country)
                .setParameter("state", state)
                .setParameter("region", region)
                .setParameter("email", email)
                .setParameter("street", street)
                .setParameter("streetnumber", streetnumber)
                .setParameter("photo", photo)
                .setParameter("cellphone", cellphone)
                .setParameter("gender", gender.charAt(0))
                .setParameter("userid", Integer.parseInt(userid))
                .setParameter("roleid", r)
                .setParameter("companyid", c);
        try {
            if (q.executeUpdate() == 1) {
                m.setCode(200);
                m.setMsg("El usuario se actualizó correctamente");
                m.setDetail("OK");
            } else {
                m.setCode(400);
                m.setMsg("No se realizo la actualización");
                m.setDetail("No existe el usuario especificado");
            }

            /*int roleidint=Integer.parseInt(roleid);
        r.setRoleid(roleidint);    
        r.setRolename(rolename);
        //El refresh es para actualizar una entidad
        entity.refresh(entity.merge(r)); // Si el id existe hace un update, sino guarda una nuevo. Persistencia manejada por le contenedor
        entity.flush();*/
        } catch (NumberFormatException e) {
            m.setCode(406);
            m.setMsg("Error de tipo de dato '" + userid + "'.");
            m.setDetail(e.toString());
        } catch (IllegalArgumentException e) {
            m.setCode(422);
            m.setMsg("Error de entidad, el usuario no es una entidad o ha sido removido.");
            m.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            m.setCode(509);
            m.setMsg("La transacción no pudo ser completada. Espera un momento y vuelve a intentar.");
            m.setDetail(e.toString());
        } catch (EntityNotFoundException e) {
            m.setCode(404);
            m.setMsg("El usuario introducido(" + userid + ") no existe, no se puede actualizar.");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);

    }
    public String deleteUser(String userid) {
        Message m = new Message();
        Users user;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNamedQuery("Users.findByUserid").
                    setParameter("userid", Integer.parseInt(userid));
            //se asigna el reultado a category
            user = (Users) q.getSingleResult();
            //esta funcion busca por id en la clase
            //category = entity.find(category.class,Integer.parseInt(categoryid));
            entity.remove(user);
            m.setCode(200);
            m.setMsg("El usuario se elimino correctamente");
            m.setDetail("OK");
        } catch (NumberFormatException e) {
            m.setCode(406);
            m.setMsg("Error de tipo de dato");
            m.setDetail(e.toString());
        } catch (IllegalArgumentException e) {
            m.setCode(422);
            m.setMsg("Su instancia no es una entidad ");
            m.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            m.setCode(500);
            m.setMsg("no hay ninguna transacción para invocar gestor de la entidad ");
            m.setDetail(e.toString());
        }
        return gson.toJson(m);
    }

    public String getUserByEmail(String email) {
        Message m = new Message();
        Users user;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {

            Query q = entity.createNamedQuery("Users.findByEmail")
                    .setParameter("email", email);
            user = (Users) q.getSingleResult();
            user.getCompanyid().setUsersList(null);
            user.getRoleid().setUsersList(null);
            user.setSaleList(null);
            user.setPassword(null);
            m.setCode(200);
            m.setMsg(gson.toJson(user));
            m.setDetail("OK");
        } catch (NoResultException e) {
            m.setCode(404);
            m.setMsg("El usuario no se encontro.");
            m.setDetail(e.toString());
        } catch (PessimisticLockException e) {
            m.setCode(404);
            m.setMsg("Pessimistic locking fails and the transaction is rolled back.");
            m.setDetail(e.toString());
        } catch (LockTimeoutException e) {
            m.setCode(404);
            m.setMsg("Pessimistic locking fails and only the statement is rolled back.");
            m.setDetail(e.toString());
        } catch (PersistenceException e) {
            m.setCode(404);
            m.setMsg("The query execution exceeds the query timeout value set and the transaction is rolled back.");
            m.setDetail(e.toString());
        }

        return gson.toJson(m);
    }

    public String getUserByUsername(String username) {
        Message m = new Message();
        Users user;

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        //try{

        Query q = entity.createNamedQuery("Users.findByUsername")
                .setParameter("username", username);
        user = (Users) q.getSingleResult();
        user.getCompanyid().setUsersList(null);
        user.getRoleid().setUsersList(null);
        user.setSaleList(null);
        user.setPassword(null);
        m.setCode(200);
        m.setMsg(gson.toJson(user));
        m.setDetail("OK");
        //}
        return gson.toJson(m);
    }

    public String getUsers() {

        List<Users> users;
        Message msg = new Message();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        try {
            Query q = entity.createNamedQuery("Users.findAll");

            users = q.getResultList();
            for (Users p : users) {
                p.setSaleList(null);
                p.getCompanyid().setUsersList(null);
                p.getRoleid().setUsersList(null);
            }
            msg.setCode(200);
            msg.setMsg(gson.toJson(users));
            msg.setDetail("OK");
        } catch (IllegalArgumentException e) {
            msg.setCode(422);
            msg.setMsg("Error de entidad, el usuario no es una entidad.");
            msg.setDetail(e.toString());
        } catch (IllegalStateException e) {
            msg.setCode(422);
            msg.setMsg("Error de entidad, el usuario no es una entidad o ha sido removido.");
            msg.setDetail(e.toString());
        } catch (QueryTimeoutException e) {
            msg.setCode(509);
            msg.setMsg("La operación tardo demasiado, por favor vuelve a intentarlo.");
            msg.setDetail(e.toString());
        } catch (TransactionRequiredException e) {
            msg.setCode(509);
            msg.setMsg("La operación tardo demasiado, por favor vuelve a intentarlo.");
            msg.setDetail(e.toString());
        } catch (PessimisticLockException e) {
            msg.setCode(400);
            msg.setMsg("Error, operación bloqueada (Pesimistic), no se realizo la transacción.");
            msg.setDetail(e.toString());
        } catch (LockTimeoutException e) {
            msg.setCode(400);
            msg.setMsg("Error, operación bloqueada (Lock), no se realizo la transacción.");
            msg.setDetail(e.toString());
        } catch (PersistenceException e) {
            msg.setCode(400);
            msg.setMsg("Error, operación bloqueada (Persistence), no se realizo la transacción.");
            msg.setDetail(e.toString());
        }
        return gson.toJson(msg);
    }


    

    

}
