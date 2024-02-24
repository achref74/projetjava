package com.esprit.controllers;

import com.esprit.models.Categorie;
import com.esprit.services.CategorieService2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;

public class AjouterCategorieController {

        @FXML
        private TextField tfDescription;

        @FXML
        private TextField tfNom;


        @FXML
        void addCategorie(ActionEvent event) throws IOException {
                CategorieService2 cs = new CategorieService2();
                cs.ajouter(new Categorie(tfNom.getText(), tfDescription.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Categorie Ajouter");
                alert.setContentText("Categorie Ajouter !");
                alert.show();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherCategorie.fxml"));
                Parent root = loader.load();
                tfNom.getScene().setRoot(root);

                AfficherCategorieController apc = loader.getController();
                apc.setLbNom(tfNom.getText());
                apc.setLbdescription(tfDescription.getText());

//                // Code pour imprimer la liste des catégories en PDF
//                // Création du document PDF
//                Document document = new Document ();
//                PdfWriter writer = PdfWriter.getInstance (document, new FileOutputStream ("liste_categories.pdf"));
//                document.open ();
//
//                // Ajout du titre et du paragraphe
//                document.add (new Paragraph ("Liste des catégories"));
//                document.add (new Paragraph ("Ce document contient la liste des catégories ajoutées par l'utilisateur."));
//
//                // Création de la liste ordonnée
//                List list = new List (List.ORDERED);
//
//                // Récupération de la liste des catégories depuis le service
//                List<Categorie> categories = cs.getAll ();
//
//                // Ajout de chaque catégorie comme un élément de la liste
//                for (Categorie c : categories) {
//                        list.add (new ListItem (c.getNom () + " : " + c.getDescription ()));
//                }
//
//                // Ajout de la liste au document PDF
//                document.add (list);
//
//                // Fermeture du document et du writer
//                document.close ();
//                writer.close ();
        }
}




