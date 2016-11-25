package mx.edu.ittepic.aeecommerce.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mx.edu.ittepic.aeecommerce.entities.Category;
import mx.edu.ittepic.aeecommerce.entities.Salesline;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-23T23:30:55")
@StaticMetamodel(Product.class)
public class Product_ { 

    public static volatile SingularAttribute<Product, String> code;
    public static volatile SingularAttribute<Product, Integer> productid;
    public static volatile SingularAttribute<Product, String> photo;
    public static volatile SingularAttribute<Product, Integer> salepricemay;
    public static volatile ListAttribute<Product, Salesline> saleslineList;
    public static volatile SingularAttribute<Product, Integer> reorderpoint;
    public static volatile SingularAttribute<Product, Integer> purchprice;
    public static volatile SingularAttribute<Product, String> productname;
    public static volatile SingularAttribute<Product, Integer> salepricemin;
    public static volatile SingularAttribute<Product, String> currency;
    public static volatile SingularAttribute<Product, Integer> stock;
    public static volatile SingularAttribute<Product, String> brand;
    public static volatile SingularAttribute<Product, Category> categoryid;

}