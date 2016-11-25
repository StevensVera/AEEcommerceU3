/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.edu.ittepic.aeecommerce.util;

/**
 *
 * @author Stevens Vera
 */
public class ProductShoopingCat {
  private String Product,Codigo,Imagen;
  private int Cantidad;
  private double PesoUnit;

    public String getProduct() {
        return Product;
    }

    public void setProduct(String Product) {
        this.Product = Product;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String Imagen) {
        this.Imagen = Imagen;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public double getPesoUnit() {
        return PesoUnit;
    }

    public void setPesoUnit(double PesoUnit) {
        this.PesoUnit = PesoUnit;
    }
  
  
}
