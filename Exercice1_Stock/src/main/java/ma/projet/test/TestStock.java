package ma.projet.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import ma.projet.classes.*;
import ma.projet.service.*;

public class TestStock {

    static Date d(String s) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(s);
    }

    public static void main(String[] args) throws Exception {
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService ls = new LigneCommandeService();

        Categorie c1 = new Categorie("C1", "Ordinateurs");
        Categorie c2 = new Categorie("C2", "Accessoires");
        cs.create(c1);
        cs.create(c2);

        Produit p1 = new Produit("ES12", 120, c1);
        Produit p2 = new Produit("ZR85", 100, c1);
        Produit p3 = new Produit("EE85", 200, c2);
        Produit p4 = new Produit("AB10", 50, c2);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);
        ps.create(p4);

        Commande cmd1 = new Commande(d("2013-03-14"));
        Commande cmd2 = new Commande(d("2013-05-20"));
        cms.create(cmd1);
        cms.create(cmd2);

        ls.create(new LigneCommandeProduit(p1, cmd1, 7));
        ls.create(new LigneCommandeProduit(p2, cmd1, 14));
        ls.create(new LigneCommandeProduit(p3, cmd1, 5));
        ls.create(new LigneCommandeProduit(p4, cmd2, 3));

        System.out.println("--- Produits par catégorie ---");
        ps.afficherProduitsParCategorie(c1);

        System.out.println("--- Produits commandés entre deux dates ---");
        ps.afficherProduitsEntreDates(d("2013-03-01"), d("2013-04-01"));

        System.out.println("--- Produits d'une commande ---");
        ps.afficherProduitsParCommande(cmd1);

        System.out.println("--- Produits dont le prix > 100 DH ---");
        ps.afficherProduitsPrixSup100();
    }
}
