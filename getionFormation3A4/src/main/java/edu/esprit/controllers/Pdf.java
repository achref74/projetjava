package edu.esprit.controllers;

import edu.esprit.entities.Certificat;
import javafx.fxml.FXML;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Pdf {

    public void createPdfWithPDFBox(String filename, Certificat certificat) {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            // Charger une image de fond pour le certificat
            PDImageXObject pdImage = PDImageXObject.createFromFile("C:\\Users\\DELL GAMING\\Desktop\\projetjava\\getionFormation3A4\\src\\main\\resources\\images\\certificat.png", document);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Dessiner l'image de fond
            contentStream.drawImage(pdImage, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());

            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 26); // Taille de police pour le titre

            // Titre du certificat
            contentStream.newLineAtOffset(220, 500);
            contentStream.showText(certificat.getTitre());

            contentStream.setFont(PDType1Font.HELVETICA, 12); // Taille de police pour le reste du texte

            // Nettoyer le texte avant de l'ajouter
            String nom = certificat.getTitre().replaceAll("\\p{C}", "");
            String description = certificat.getDescription().replaceAll("\\p{C}", "");
            String date = certificat.getDateObtention().toString().replaceAll("\\p{C}", "");
            String nbrcours = String.valueOf(certificat.getNbrCours()).replaceAll("\\p{C}", "");

            contentStream.newLineAtOffset(-120, -50);
            contentStream.showText("Nom: " + nom);

            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Description: " + description);

            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Date: " + date);

            contentStream.newLineAtOffset(0, -15);
            contentStream.showText("Nombre de cours: " + nbrcours);

            contentStream.endText();
            contentStream.close();

            document.save(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

