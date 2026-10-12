package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class TacheService extends AbstractFacade<Tache> {

    public TacheService() {
        super(Tache.class);
    }

    // Tâches dont le prix est supérieur à 1000 DH (requête nommée)
    public void afficherTachesPrixSup1000() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> liste = s.getNamedQuery("Tache.prixSup1000").list();
            for (Tache t : liste) {
                System.out.println(t.getId() + "  " + t.getNom() + "  " + t.getPrix() + " DH");
            }
        }
    }

    // Tâches réalisées entre deux dates
    public void afficherTachesRealiseesEntreDates(Date d1, Date d2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> liste = s.createQuery(
                    "from EmployeTache et where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2", EmployeTache.class)
                    .setParameter("d1", d1).setParameter("d2", d2).list();
            for (EmployeTache et : liste) {
                System.out.println(et.getTache().getNom() + "  " + sdf.format(et.getDateDebutReelle())
                        + "  " + sdf.format(et.getDateFinReelle()));
            }
        }
    }
}
