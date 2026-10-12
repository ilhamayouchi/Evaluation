package ma.projet.util;

import java.io.InputStream;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            Properties p = new Properties();
            InputStream in = HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties");
            p.load(in);
            Configuration cfg = new Configuration().setProperties(p);
            cfg.addAnnotatedClass(ma.projet.classes.Categorie.class);
            cfg.addAnnotatedClass(ma.projet.classes.Produit.class);
            cfg.addAnnotatedClass(ma.projet.classes.Commande.class);
            cfg.addAnnotatedClass(ma.projet.classes.LigneCommandeProduit.class);
            sessionFactory = cfg.buildSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
