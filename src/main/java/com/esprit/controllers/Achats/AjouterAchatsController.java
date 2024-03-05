package com.esprit.controllers.Achats;

import com.esprit.models.Achat;
import com.esprit.models.Formation;
import com.esprit.models.outil;
import com.esprit.services.AchatService2;
import com.esprit.services.OutilsService2;
import com.esprit.services.ServiceFormation;
import com.itextpdf.text.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;

public class AjouterAchatsController {

    @FXML
    private ComboBox<outil> cbOutils;

    @FXML
    private ComboBox<Formation> cbFormation;

    @FXML
    private TextField tfTotalePrice;

    @FXML
    private DatePicker datePicker;
    @FXML
    private StackPane mainContentPane;
    private ServiceFormation FormationService;
    private OutilsService2 outilsService;
    private AchatService2 serviceAchat ;

    private static Font titleFont = new Font(FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font smallBold = new Font(FontFamily.HELVETICA, 12, Font.BOLD);
    private static Font normalFont = new Font(FontFamily.HELVETICA, 12);
    @FXML
    public void initialize() {
        FormationService = new ServiceFormation();
        outilsService = new OutilsService2();
        serviceAchat = new AchatService2();
        cbOutils.getItems().addAll(outilsService.getAll());
       cbFormation.getItems().addAll(FormationService.getAll());


        cbFormation.setConverter(new StringConverter<Formation>() {

                @Override
                public String toString(Formation formation) {
                    // Affichez le nom et prénom de l'utilisateur dans le ChoiceBox
                    return formation != null ? formation.getNom() : " " ;
                }

                @Override
                public Formation fromString(String string) {
                    // Vous n'avez probablement pas besoin d'implémenter cette méthode pour un ChoiceBox
                    return null;
                }
            });


        }


    public void handleAjouterAchats(ActionEvent actionEvent) throws DocumentException, IOException {
        // Check if ComboBoxes have selected items and DatePicker has a selected date
        if (cbOutils.getValue() == null || cbFormation.getValue() == null || datePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez sélectionner une formation, un outil et une date.");
            return;
        }

        double totalPrice;
        try {
            totalPrice = Double.parseDouble(tfTotalePrice.getText().trim());
            if (totalPrice <= 0) {
                throw new NumberFormatException("Le prix total doit être supérieur à 0.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le prix total doit être un nombre valide et supérieur à 0.");
            return;
        }

        try
        {
            LocalDate localDate = LocalDate.now();
            if(localDate.isAfter(datePicker.getValue())||localDate.isBefore(datePicker.getValue()))
                throw new Exception();

        }catch (Exception e)
        {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Date doit être date d'aujourd'hui ");
            return;
        }

        // If all validations pass, proceed to add the purchase
        Achat newAchat = new Achat(cbFormation.getValue(), cbOutils.getValue(), totalPrice, datePicker.getValue());
        serviceAchat.ajouter(newAchat);
        //showAlert(Alert.AlertType.INFORMATION, "Achat Ajoutée", "Achat ajouté avec succès!");



        Notifications.create()
                .styleClass(
                        "-fx-background-color: #28a745; " + // Couleur de fond
                                "-fx-text-fill: white; " + // Couleur du texte
                                "-fx-background-radius: 5px; " + // Bord arrondi
                                "-fx-border-color: #ffffff; " + // Couleur de la bordure
                                "-fx-border-width: 2px;" // Largeur de la bordure
                )
                .title("achat Ajouté avec succès")
                .position(Pos.TOP_RIGHT) // Modifier la position ici
                .hideAfter(Duration.seconds(20))
                .show();




        generateInvoice(newAchat);

        // Load the view to display purchases
        loadAfficherAchatsView();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadAfficherAchatsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Achats/AfficherAchats.fxml"));
            Parent afficherAchatView = loader.load();

            AfficherAchatsController afficherAchatsController = loader.getController();
            afficherAchatsController.setMainContent(mainContentPane);

            mainContentPane.getChildren().setAll(afficherAchatView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateInvoice(Achat achat) throws IOException, DocumentException {
        Document document = new Document();
        String fileName = "Invoice_" + achat.getIdAchat() + ".pdf";
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();


         Image companyLogo = Image.getInstance("src/main/resources/images/logo.png");
         document.add(companyLogo);


        Paragraph title = new Paragraph("Facture", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);


        document.add(new Paragraph(" "));


        Paragraph dateParagraph = new Paragraph("Date: " + achat.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), smallBold);
        dateParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(dateParagraph);

        document.add(new Paragraph("Achat ID: " + achat.getIdAchat(), smallBold));
        document.add(new Paragraph(" "));

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[] {1, 5, 2, 2});


        table.addCell(new Phrase("Qty", smallBold));
        table.addCell(new Phrase("Description", smallBold));
        table.addCell(new Phrase("Unit Price", smallBold));
        table.addCell(new Phrase("Total", smallBold));


        table.addCell(new Phrase("1", normalFont));
        table.addCell(new Phrase(achat.getOutil().getNom(), normalFont));
        table.addCell(new Phrase(String.valueOf(achat.getOutil().getPrix()), normalFont));
        table.addCell(new Phrase(String.valueOf(achat.getTotal()), normalFont));

        document.add(table);


        document.add(new Paragraph(" "));


        Paragraph totalParagraph = new Paragraph("Total: " + achat.getTotal(), titleFont);
        totalParagraph.setAlignment(Element.ALIGN_RIGHT);
        document.add(totalParagraph);


         Paragraph footer = new Paragraph("Thank you for your business.", smallBold);
         footer.setAlignment(Element.ALIGN_CENTER);
         document.add(footer);

        document.close();


        if (Desktop.isDesktopSupported()) {
            try {
                File pdfFile = new File(fileName);
                Desktop.getDesktop().open(pdfFile);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }
}



