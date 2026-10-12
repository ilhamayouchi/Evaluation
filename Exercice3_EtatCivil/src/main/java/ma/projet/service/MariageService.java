package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class MariageService extends AbstractFacade<Mariage> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public MariageService() {
        super(Mariage.class);
    }

    // API Criteria : nombre d'hommes mariés à quatre femmes entre deux dates
    public int nbHommesMariesQuatreFemmes(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Criteria c = s.createCriteria(Mariage.class);
            c.add(Restrictions.between("pK.dateDebut", d1, d2));
            c.setProjection(Projections.projectionList()
                    .add(Projections.groupProperty("pK.homme"))
                    .add(Projections.rowCount()));
            List<Object[]> lignes = c.list();
            int nb = 0;
            for (Object[] l : lignes) {
                if (((Number) l[1]).intValue() == 4) {
                    nb++;
                }
            }
            return nb;
        }
    }

    // Afficher les mariages d'un homme avec tous les détails
    public void afficherMariages(Homme h) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Mariage> liste = s.createQuery("from Mariage m where m.homme.id = :id", Mariage.class)
                    .setParameter("id", h.getId()).list();
            System.out.println("Nom : " + h.getNom().toUpperCase() + " " + h.getPrenom().toUpperCase());

            System.out.println("Mariages en cours :");
            int i = 1;
            for (Mariage m : liste) {
                if (m.getDateFin() == null) {
                    System.out.println(i++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                            + "   Date Début : " + sdf.format(m.getpK().getDateDebut())
                            + "   Nbr Enfants : " + m.getNbrEnfant());
                }
            }

            System.out.println("Mariages échus :");
            i = 1;
            for (Mariage m : liste) {
                if (m.getDateFin() != null) {
                    System.out.println(i++ + ". Femme : " + m.getFemme().getNom() + " " + m.getFemme().getPrenom()
                            + "   Date Début : " + sdf.format(m.getpK().getDateDebut())
                            + "   Date Fin : " + sdf.format(m.getDateFin())
                            + "   Nbr Enfants : " + m.getNbrEnfant());
                }
            }
        }
    }
}
