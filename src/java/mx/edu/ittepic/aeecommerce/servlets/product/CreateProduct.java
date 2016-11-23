/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.servlets.product;

import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import mx.edu.ittepic.aeecommerce.ejbs.ejbEcommerceProducts2;

/**
 *
 * @author HP
 */
@WebServlet(name = "CreateProduct", urlPatterns = {"/CreateProduct"})
@MultipartConfig
public class CreateProduct extends HttpServlet {
    @EJB
    ejbEcommerceProducts2 ejb;

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
        processRequest(request, response);
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
        processRequest(request, response);
        
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        
        PrintWriter out = response.getWriter();
        
        String brand  = request.getParameter("brand");
        String categoryid  = request.getParameter("categoryid");
        String code  = request.getParameter("code");
        String currency  = request.getParameter("currency");
        String productname  = request.getParameter("productname");
        String purchprice  = request.getParameter("purchprice");
        String reorderpoint  = request.getParameter("reorderpoint");
        String salepricemay  = request.getParameter("salepricemay");
        String salepricemin  = request.getParameter("salepricemin");
        String stock  = request.getParameter("stock");
        
        Part filePart = request.getPart("img");
        
        out.print(ejb.createProduct(code, productname, brand, purchprice, stock, salepricemin,
                reorderpoint, currency, categoryid, salepricemay, filePart));
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
