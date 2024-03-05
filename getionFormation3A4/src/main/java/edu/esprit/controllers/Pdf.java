

package edu.esprit.controllers;

        import edu.esprit.entities.Certificat;
        import org.apache.pdfbox.pdmodel.PDDocument;
        import org.apache.pdfbox.pdmodel.PDPage;
        import org.apache.pdfbox.pdmodel.common.PDRectangle;
        import org.apache.pdfbox.pdmodel.PDPageContentStream;
        import org.apache.pdfbox.pdmodel.font.PDType1Font;
        import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Pdf {

    public void createPdfWithPDFBox(String filename, Certificat certificat) {
        try (PDDocument document = new PDDocument()) {
            // Créez une page avec une taille personnalisée. Les dimensions sont en points.
            // Par exemple, un certificat standard pourrait être de 8.5 x 11 pouces.
            // Pour le convertir en points (1 pouce = 72 points), vous pouvez faire 8.5 * 72 par 11 * 72.
            PDRectangle rect = new PDRectangle(800, 600); // Taille d'une page de certificat en mode portrait
            PDPage page = new PDPage(rect);
            document.addPage(page);

            // Le reste de votre code pour dessiner sur la page
            PDImageXObject pdImage = PDImageXObject.createFromFile(
                    "C:\\Users\\DELL GAMING\\Desktop\\PI\\getionFormation3A4\\src\\main\\resources\\images\\certificat.png", document);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            contentStream.drawImage(pdImage, 0, 0, rect.getWidth(), rect.getHeight());
            // Vous pouvez maintenant ajouter votre texte et d'autres éléments ici

            // Assurez-vous que vos positions x et y correspondent à la taille de la page.
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 26);
            String nom = certificat.getTitre().replaceAll("\\p{C}", "");
            String description = certificat.getDescription().replaceAll("\\p{C}", "");
            String date = certificat.getDateObtention().toString().replaceAll("\\p{C}", "");
            String nbrcours = String.valueOf(certificat.getNbrCours()).replaceAll("\\p{C}", "");

            // Ajustez les valeurs x et y ci-dessous pour aligner le texte comme dans votre certificat
            contentStream.newLineAtOffset(120, 400);
            contentStream.showText("Nom: " + nom);

            contentStream.newLineAtOffset(0, -50);
            contentStream.showText("Description: " + description);

            contentStream.newLineAtOffset(0, -50);
            contentStream.showText("Date: " + date);

            contentStream.newLineAtOffset(0, -50);
            contentStream.showText("Nombre de cours: " + nbrcours);

            contentStream.endText();

            contentStream.close();

            document.save(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

