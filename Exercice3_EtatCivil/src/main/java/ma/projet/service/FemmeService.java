package ma.projet.service;

import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import ma.projet.beans.Femme;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class FemmeService extends AbstractFacade<Femme> {

    public FemmeService() {
        super(Femme.class);
    }

    // Nombre d'enfants d'une femme entre deux dates (requête native nommée)
    public int nbEnfantsEntreDates(Femme f, Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Number n = (Number) s.getNamedQuery("Femme.nbEnfantsEntreDates")
                    .setParameter("id", f.getId())
                    .setParameter("d1", d1, TemporalType.DATE)
                    .setParameter("d2", d2, TemporalType.DATE)
                    .uniqueResult();
            return n.intValue();
        }
    }

    // Femmes mariées au moins deux fois (requête nommée)
    public List<Femme> femmesMarieesDeuxFoisOuPlus() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.getNamedQuery("Femme.mariees2Fois").list();
        }
    }
}
