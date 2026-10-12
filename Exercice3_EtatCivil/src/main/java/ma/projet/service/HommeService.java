package ma.projet.service;

import java.util.Date;
import java.util.List;
import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class HommeService extends AbstractFacade<Homme> {

    public HommeService() {
        super(Homme.class);
    }

    // Afficher les épouses d'un homme entre deux dates
    public void afficherEpousesEntreDates(Homme h, Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Femme> liste = s.createQuery(
                    "select m.femme from Mariage m where m.homme.id = :id and m.pK.dateDebut between :d1 and :d2",
                    Femme.class).setParameter("id", h.getId()).setParameter("d1", d1).setParameter("d2", d2).list();
            System.out.println("Épouses de " + h.getNom() + " " + h.getPrenom() + " :");
            for (Femme f : liste) {
                System.out.println(f.getNom() + " " + f.getPrenom());
            }
        }
    }
}
