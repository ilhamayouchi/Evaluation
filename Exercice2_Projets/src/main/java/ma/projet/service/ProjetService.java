package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.List;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class ProjetService extends AbstractFacade<Projet> {

    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ProjetService() {
        super(Projet.class);
    }

    // Afficher la liste des tâches planifiées pour un projet
    public void afficherTachesPlanifiees(Projet p) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> liste = s.createQuery("from Tache t where t.projet.id = :id", Tache.class)
                    .setParameter("id", p.getId()).list();
            System.out.println("Tâches planifiées du projet " + p.getNom() + " :");
            for (Tache t : liste) {
                System.out.println(t.getId() + "  " + t.getNom() + "  " + sdf.format(t.getDateDebut()) + "  " + sdf.format(t.getDateFin()));
            }
        }
    }

    // Afficher la liste des tâches réalisées avec les dates réelles
    public void afficherTachesRealisees(Projet p) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<EmployeTache> liste = s.createQuery(
                    "from EmployeTache et where et.tache.projet.id = :id", EmployeTache.class)
                    .setParameter("id", p.getId()).list();
            System.out.println("Projet : " + p.getId() + "   Nom : " + p.getNom()
                    + "   Date début : " + sdf.format(p.getDateDebut()));
            System.out.println("Liste des tâches :");
            System.out.println("Num  Nom  Date Début Réelle  Date Fin Réelle");
            for (EmployeTache et : liste) {
                System.out.println(et.getTache().getId() + "  " + et.getTache().getNom() + "  "
                        + sdf.format(et.getDateDebutReelle()) + "  " + sdf.format(et.getDateFinReelle()));
            }
        }
    }
}
