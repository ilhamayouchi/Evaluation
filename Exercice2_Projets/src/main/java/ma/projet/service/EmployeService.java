package ma.projet.service;

import java.util.List;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class EmployeService extends AbstractFacade<Employe> {

    public EmployeService() {
        super(Employe.class);
    }

    // Afficher la liste des tâches réalisées par un employé
    public void afficherTachesRealisees(Employe e) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Tache> liste = s.createQuery(
                    "select et.tache from EmployeTache et where et.employe.id = :id", Tache.class)
                    .setParameter("id", e.getId()).list();
            System.out.println("Tâches réalisées par " + e.getNom() + " " + e.getPrenom() + " :");
            for (Tache t : liste) {
                System.out.println(t.getId() + "  " + t.getNom());
            }
        }
    }

    // Afficher la liste des projets gérés par un employé
    public void afficherProjetsGeres(Employe e) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Projet> liste = s.createQuery("from Projet p where p.chefProjet.id = :id", Projet.class)
                    .setParameter("id", e.getId()).list();
            System.out.println("Projets gérés par " + e.getNom() + " " + e.getPrenom() + " :");
            for (Projet p : liste) {
                System.out.println(p.getId() + "  " + p.getNom());
            }
        }
    }
}
