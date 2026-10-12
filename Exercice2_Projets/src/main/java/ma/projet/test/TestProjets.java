package ma.projet.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import ma.projet.classes.*;
import ma.projet.service.*;

public class TestProjets {

    static Date d(String s) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(s);
    }

    public static void main(String[] args) throws Exception {
        EmployeService es = new EmployeService();
        ProjetService ps = new ProjetService();
        TacheService ts = new TacheService();
        EmployeTacheService ets = new EmployeTacheService();

        Employe e1 = new Employe("Alami", "Ahmed", "0612345678");
        Employe e2 = new Employe("Rami", "Sara", "0698765432");
        es.create(e1);
        es.create(e2);

        Projet p = new Projet("Gestion de stock", d("2013-01-14"), d("2013-06-30"), e1);
        ps.create(p);

        Tache t1 = new Tache("Analyse", d("2013-02-10"), d("2013-02-20"), 800, p);
        Tache t2 = new Tache("Conception", d("2013-03-10"), d("2013-03-15"), 1500, p);
        Tache t3 = new Tache("Développement", d("2013-04-10"), d("2013-04-25"), 3000, p);
        ts.create(t1);
        ts.create(t2);
        ts.create(t3);

        ets.create(new EmployeTache(e2, t1, d("2013-02-10"), d("2013-02-20")));
        ets.create(new EmployeTache(e2, t2, d("2013-03-10"), d("2013-03-15")));
        ets.create(new EmployeTache(e1, t3, d("2013-04-10"), d("2013-04-25")));

        System.out.println("--- Tâches réalisées par un employé ---");
        es.afficherTachesRealisees(e2);

        System.out.println("--- Projets gérés par un employé ---");
        es.afficherProjetsGeres(e1);

        System.out.println("--- Tâches planifiées d'un projet ---");
        ps.afficherTachesPlanifiees(p);

        System.out.println("--- Tâches réalisées avec dates réelles ---");
        ps.afficherTachesRealisees(p);

        System.out.println("--- Tâches dont le prix > 1000 DH ---");
        ts.afficherTachesPrixSup1000();

        System.out.println("--- Tâches réalisées entre deux dates ---");
        ts.afficherTachesRealiseesEntreDates(d("2013-03-01"), d("2013-04-30"));
    }
}
