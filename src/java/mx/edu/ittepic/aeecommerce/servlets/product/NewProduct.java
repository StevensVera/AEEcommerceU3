/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.servlets.product;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import mx.edu.ittepic.aeecommerce.ejbs.EJBEcommerceProducts;
import mx.edu.ittepic.aeecommerce.util.Image;


/**
 *
 * @author gustavo
 */
@WebServlet(name = "NewProduct", urlPatterns = {"/NewProduct"})
public class NewProduct extends HttpServlet {
    @EJB
    private EJBEcommerceProducts ejb;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
      response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        try (PrintWriter out = response.getWriter()) {
            String code=request.getParameter("code");
        String brand=request.getParameter("brand");
        String purchprice=request.getParameter("purchprice");
        String productname=request.getParameter("productname");
        String stock=request.getParameter("stock");
        String salepricemin=request.getParameter("salepricemin");
        String salepricemay=request.getParameter("salepricemay");
        String reorderpoint=request.getParameter("reorderpoint");
        String categoryid=request.getParameter("categoryid");
        String currency=request.getParameter("currency");
       
        //out.print(ejb.newProduct(code, brand, purchprice, productname, stock, salepricemin, salepricemay, reorderpoint, cat, currency));
        out.print(ejb.newProduct(code, brand, purchprice, productname, stock, salepricemin, salepricemay, reorderpoint, categoryid, currency));
        //processRequest(request, response);
            
        }
       */
       
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
     
        String code=request.getParameter("code");
        String productname=request.getParameter("productname");
        String brand=request.getParameter("brand");
        String purchprice=request.getParameter("purchprice");
        String stock=request.getParameter("stock");
        String salepricemin=request.getParameter("salepricemin");
        String reorderpoint=request.getParameter("reorderpoint");
        String currency=request.getParameter("currency");
        String categoryid=request.getParameter("categoryid");
        String salepricemay=request.getParameter("salepricemay");
        
        Part foto= request.getPart("image");
        String fotoname = Paths.get(foto.getSubmittedFileName()).getFileName().toString();
        InputStream fcontent = foto.getInputStream();
        Image imagen = new Image(fotoname, fcontent);
       
        PrintWriter out = response.getWriter();
        //out.print(ejb.newProduct(code, brand, purchprice, productname, stock, salepricemin, salepricemay, reorderpoint, cat, currency));
        //out.print(ejb.newProduct(code, brand, purchprice, productname, stock, salepricemin, salepricemay, reorderpoint, categoryid, currency));
        out.print(ejb.newProduct(code,productname,brand,purchprice,stock,salepricemin,reorderpoint,currency,categoryid,salepricemay,imagen));
        //processRequest(request, response);
    
        }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
