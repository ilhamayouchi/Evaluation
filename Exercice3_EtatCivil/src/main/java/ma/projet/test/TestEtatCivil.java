package ma.projet.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import ma.projet.beans.*;
import ma.projet.service.*;

public class TestEtatCivil {

    static Date d(String s) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(s);
    }

    public static void main(String[] args) throws Exception {
        HommeService hs = new HommeService();
        FemmeService fs = new FemmeService();
        MariageService ms = new MariageService();

        // 10 femmes et 5 hommes
        List<Femme> femmes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Femme f = new Femme("NOMF" + i, "Prenom" + i, "060000000" + (i % 10), "Adresse " + i, d((1960 + i) + "-01-01"));
            fs.create(f);
            femmes.add(f);
        }
        List<Homme> hommes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Homme h = new Homme("NOMH" + i, "Prenom" + i, "070000000" + i, "Adresse " + i, d((1955 + i) + "-01-01"));
            hs.create(h);
            hommes.add(h);
        }

        // Mariages
        Homme h1 = hommes.get(0);
        ms.create(new Mariage(h1, femmes.get(0), d("1990-09-03"), d("1995-09-03"), 4));
        ms.create(new Mariage(h1, femmes.get(1), d("1995-09-03"), null, 2));
        ms.create(new Mariage(h1, femmes.get(2), d("2000-02-02"), null, 3));
        ms.create(new Mariage(h1, femmes.get(3), d("2002-05-10"), null, 0));
        ms.create(new Mariage(hommes.get(1), femmes.get(0), d("1997-01-01"), null, 1));
        ms.create(new Mariage(hommes.get(2), femmes.get(4), d("1999-06-15"), d("2003-06-15"), 2));

        System.out.println("--- Liste des femmes ---");
        for (Femme f : fs.findAll()) {
            System.out.println(f.getNom() + " " + f.getPrenom());
        }

        System.out.println("--- Femme la plus âgée ---");
        Femme plusAgee = Collections.min(fs.findAll(), new Comparator<Femme>() {
            @Override
            public int compare(Femme a, Femme b) {
                return a.getDateNaissance().compareTo(b.getDateNaissance());
            }
        });
        System.out.println(plusAgee.getNom() + " " + plusAgee.getPrenom());

        System.out.println("--- Épouses d'un homme entre deux dates ---");
        hs.afficherEpousesEntreDates(h1, d("1990-01-01"), d("2001-01-01"));

        System.out.println("--- Nombre d'enfants d'une femme entre deux dates ---");
        System.out.println(fs.nbEnfantsEntreDates(femmes.get(0), d("1990-01-01"), d("2000-01-01")));

        System.out.println("--- Femmes mariées deux fois ou plus ---");
        for (Femme f : fs.femmesMarieesDeuxFoisOuPlus()) {
            System.out.println(f.getNom() + " " + f.getPrenom());
        }

        System.out.println("--- Hommes mariés à quatre femmes entre deux dates ---");
        System.out.println(ms.nbHommesMariesQuatreFemmes(d("1990-01-01"), d("2005-01-01")));

        System.out.println("--- Mariages d'un homme ---");
        ms.afficherMariages(h1);
    }
}
