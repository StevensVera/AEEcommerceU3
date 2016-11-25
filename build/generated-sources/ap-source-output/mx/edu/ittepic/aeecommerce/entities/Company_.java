package mx.edu.ittepic.aeecommerce.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import mx.edu.ittepic.aeecommerce.entities.Users;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-11-23T23:30:55")
@StaticMetamodel(Company.class)
public class Company_ { 

    public static volatile SingularAttribute<Company, String> country;
    public static volatile ListAttribute<Company, Users> usersList;
    public static volatile SingularAttribute<Company, String> city;
    public static volatile SingularAttribute<Company, String> streetnumber;
    public static volatile SingularAttribute<Company, String> rfc;
    public static volatile SingularAttribute<Company, String> zipcode;
    public static volatile SingularAttribute<Company, Integer> companyid;
    public static volatile SingularAttribute<Company, String> phone;
    public static volatile SingularAttribute<Company, String> companyname;
    public static volatile SingularAttribute<Company, String> street;
    public static volatile SingularAttribute<Company, String> logo;
    public static volatile SingularAttribute<Company, String> neighborhood;
    public static volatile SingularAttribute<Company, String> state;
    public static volatile SingularAttribute<Company, String> region;

}