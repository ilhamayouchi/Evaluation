package ma.projet.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;

public class ProduitService extends AbstractFacade<Produit> {

    public ProduitService() {
        super(Produit.class);
    }

    // Afficher la liste des produits par catégorie
    public void afficherProduitsParCategorie(Categorie c) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Produit> liste = s.createQuery("from Produit p where p.categorie.id = :id", Produit.class)
                    .setParameter("id", c.getId()).list();
            System.out.println("Catégorie : " + c.getLibelle());
            for (Produit p : liste) {
                System.out.println(p.getReference() + "  " + p.getPrix() + " DH");
            }
        }
    }

    // Afficher les produits commandés entre deux dates
    public void afficherProduitsEntreDates(Date d1, Date d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Produit> liste = s.createQuery(
                    "select distinct l.produit from LigneCommandeProduit l where l.commande.date between :d1 and :d2",
                    Produit.class).setParameter("d1", d1).setParameter("d2", d2).list();
            for (Produit p : liste) {
                System.out.println(p.getReference() + "  " + p.getPrix() + " DH");
            }
        }
    }

    // Afficher les produits commandés dans une commande donnée
    public void afficherProduitsParCommande(Commande c) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<LigneCommandeProduit> lignes = s.createQuery(
                    "from LigneCommandeProduit l where l.commande.id = :id", LigneCommandeProduit.class)
                    .setParameter("id", c.getId()).list();
            System.out.println("Commande : " + c.getId() + "   Date : " + new SimpleDateFormat("dd MMMM yyyy").format(c.getDate()));
            System.out.println("Liste des produits :");
            System.out.println("Référence   Prix   Quantité");
            for (LigneCommandeProduit l : lignes) {
                System.out.println(l.getProduit().getReference() + "   " + l.getProduit().getPrix() + " DH   " + l.getQuantite());
            }
        }
    }

    // Produits dont le prix est supérieur à 100 DH (requête nommée)
    public void afficherProduitsPrixSup100() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            List<Produit> liste = s.getNamedQuery("Produit.prixSup100").list();
            for (Produit p : liste) {
                System.out.println(p.getReference() + "  " + p.getPrix() + " DH");
            }
        }
    }
}
