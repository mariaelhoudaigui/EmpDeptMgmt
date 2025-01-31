package pack2;
// Generated 27 mai 2024 10:53:21 by Hibernate Tools 4.3.1


import connect.NewHibernateUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Dept generated by hbm2java
 */
public class Dept  implements java.io.Serializable {


     private Integer idDept;
     private String nom;
     private String localisation;
     private Set emps = new HashSet(0);
    private int id;

    public Dept() {
    }

	
    public Dept(String nom) {
        this.nom = nom;
    }
    public Dept(String nom, String localisation, Set emps) {
       this.nom = nom;
       this.localisation = localisation;
       this.emps = emps;
    }
   
    public Integer getIdDept() {
        return this.idDept;
    }
    
    public void setIdDept(Integer idDept) {
        this.idDept = idDept;
    }
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getLocalisation() {
        return this.localisation;
    }
    
    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    public Set getEmps() {
        return this.emps;
    }
    
    public void setEmps(Set emps) {
        this.emps = emps;
    }
    public String getId() {
    return this.idDept != null ? this.idDept.toString() : null;
}


    public void setId(int id) {
        this.id = id;
    }
    
    public static List<Dept> listDept() {
        SessionFactory sessionFactory = NewHibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        List<Dept> list = session.createQuery("from Dept").list();
        tx.commit();
        session.close();
        return list;
    }

}


