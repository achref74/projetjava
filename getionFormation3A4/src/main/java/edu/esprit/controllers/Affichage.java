package edu.esprit.controllers;
import edu.esprit.entities.Formation;
import edu.esprit.entities.Offre;
import edu.esprit.services.ServiceFormation;
import edu.esprit.services.ServiceOffre;
import edu.esprit.tests.MyListenerF;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Affichage implements Initializable {
    @FXML
    private TextField idFO;
    @FXML
    private Button ajouterF;
    @FXML
    private Button afficheF;
    @FXML
    private VBox chosenFruitCard;
    @FXML
    private TextField prixF;
    @FXML
    private TextField dateD;

    @FXML
    private TextField dateF;

    @FXML
    private TextArea descripF;

    @FXML
    private ImageView fruitImg;

    @FXML
    private Button retour;
    @FXML
    private TextField nomF;
    @FXML
    private Tab tabOffre;

    @FXML
    private GridPane grid;

    @FXML
    private Button modifierF;

    @FXML
    private TextField nbrCours;
    @FXML
    private TextField nomFO;
    @FXML
    private TextField ressource;

    @FXML
    private Button ajouterOffre;

    @FXML
    private Button btnCours1;

    @FXML
    private Button btnFormation;

    @FXML
    private Button btnForum1;

    @FXML
    private Button btnOutils1;

    @FXML
    private Button btnReclamation1;

    @FXML
    private Button btnSignout1;

    @FXML
    private Button btnUser1;


    @FXML
    private DatePicker dateDO;

    @FXML
    private Button offreF;

    @FXML
    private DatePicker dateFO;

    @FXML
    private TextArea descripO;


    @FXML
    private TextField idF;


    @FXML
    private Button ok1;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Tab tabF;

    @FXML
    private TextField prixO;


    @FXML
    private ScrollPane scroll;

    @FXML
    private Button supprimerF;

    @FXML
    private TabPane tabPane;
    @FXML
    public Label fxerrorprix1;
    @FXML
    public Label fxerrordate1;

    @FXML
    public Label fxerrorprix2;
    @FXML
    public Label fxerrordate2;

    @FXML
    public Button offre;

    @FXML
    private VBox chosenFruitCard1;

    @FXML
    private TextField dateDO1;

    @FXML
    private TextField dateFO1;

    @FXML
    private TextArea descripO1;

    @FXML
    private javax.swing.text.html.ImageView fruitImg1;

    @FXML
    private Label fxerrordate11;

    @FXML
    private Label fxerrorprix11;

    @FXML
    private GridPane grid1;

    @FXML
    private TextField idF1;

    @FXML
    private Button modifierO;


    @FXML
    private Label fxerrordate12;

private String selectedNomF;
    @FXML
    private Label fxerrorprix12;
    @FXML
    private TextField prixFO1;
    @FXML
    private Tab tab3;
    @FXML
    private Button retour1;

    @FXML
    private ScrollPane scroll1;

    @FXML
    private Button supprimerO;

    @FXML
    void navigatetoFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AffichageF_Formateur.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void navigatetoOffreAction(ActionEvent event) {
        tabPane.getSelectionModel().select(tabOffre);

        nomFO.setText(selectedNomF);
        System.out.println(selectedNomF);
    }

    @FXML
    void navigatetoAjouterFormationAction(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterFormation.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

    @FXML
    void navigatetoOffre(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherOffre.fxml"));
            retour.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
    }

 @FXML
    void navigatetoFormation(ActionEvent event) {
        // Sélectionner l'onglet Formation (tab index 0)
        tabPane.getSelectionModel().select(0);
    }

    private Set<Formation> listF = new HashSet<>();
    private MyListenerF myListener;
    private String selectedIdF1;
    private String selectedIdF;

    private Set<Formation> getData() {
        Set<Formation> listF = new HashSet<>();
        ServiceFormation sf = new ServiceFormation();

        try {
            listF = sf.formationsExistForUser(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listF;
    }

    private void setChosenFormation(Formation formation) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

        String dateDString = sdf.format(formation.getDateDebut());
        String dateFString = sdf.format(formation.getDateFin());
        idF.setText(String.valueOf(formation.getIdFormation()));
        dateD.setText(dateDString);
        dateF.setText(dateFString);
        nomF.setText(formation.getNom());
        nbrCours.setText(String.valueOf(formation.getNbrCours()));
        descripF.setText(formation.getDescription());
        prixF.setText(String.valueOf(formation.getPrix()));
selectedNomF= String.valueOf(formation.getNom());
        selectedIdF = String.valueOf(formation.getIdFormation());
        //fruitPriceLabel.setText(MainFx.CURRENCY + cours.getDuree());


        List<String> colorPalette = new ArrayList<>();
        colorPalette.add("#D4A5A5");
        colorPalette.add("#A0522D");
        colorPalette.add("#8B4513");
        colorPalette.add("#CD853F");
        colorPalette.add("#D2B48C");
        colorPalette.add("#BC8F8F");
        colorPalette.add("#F4A460");
        colorPalette.add("#DAA520");
        colorPalette.add("#8B4513");
        colorPalette.add("#46637F");
        colorPalette.add("#44505E");
        colorPalette.add("#7D5147");
        colorPalette.add("#7F5A45");
        colorPalette.add("#7E8C6B");
        Random random = new Random();


        String color = colorPalette.get(random.nextInt(colorPalette.size()));

        chosenFruitCard.setStyle("-fx-background-color: " + color + ";\n" +
                "    -fx-background-radius: 30;");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxerrorprix1.setVisible(false);
        fxerrorprix2.setVisible(false);
        fxerrordate1.setVisible(false);
        fxerrordate2.setVisible(false);
        idFO.setVisible(false);

        idF.setVisible(true);
        //  tabPane.getSelectionModel().select(afficheF);

        listF.addAll(getData());
        if (!listF.isEmpty()) {
            Iterator<Formation> iterator = listF.iterator();
            Formation firstFormation = iterator.next();
            setChosenFormation(firstFormation);

            myListener = new MyListenerF() {
                @Override
                public void onClickListener(Formation formation) {
                    setChosenFormation(formation);
                }

                @Override
                public void onClickListener1(Offre var2) {

                }
            };
        }
        int column = 0;
        int row = 1;
        try {


            int i = 0;
            for (Formation formation : listF) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemF.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemF itemF = fxmlLoader.getController();


                if (itemF != null) {

                    itemF.setData(formation, myListener);
                } else System.err.println("itemController est null");
                i++;
                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        modifierF.setOnAction(event -> modifierFormation());
        supprimerF.setOnAction(event -> supprimerFormations());
        ajouterOffre.setOnAction(event -> AjouterOffre());


    }

    @FXML
    private void supprimerFormations() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedIdF != null && !selectedIdF.isEmpty()) {
            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedIdF);

            // Appelez la méthode de service pour supprimer le cours avec l'ID sélectionné
            ServiceFormation sf = new ServiceFormation();
            try {
                sf.supprimer(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Mettez à jour la liste des cours après la suppression
            listF.clear();
            listF.addAll(getData());

            // Rafraîchissez l'affichage après la suppression
            grid.getChildren().clear(); // Effacez le contenu actuel du grid

            int column = 0;
            int row = 1;

            // Réinitialisez l'affichage avec la nouvelle liste de cours
            try {
                for (Formation formation : listF) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("/ItemF.fxml"));
                    AnchorPane anchorPane = fxmlLoader.load();

                    ItemF itemF = fxmlLoader.getController();

                    if (itemF != null) {
                        itemF.setData(formation, myListener);
                    } else {
                        System.err.println("itemController est null");
                    }
                    column++;
                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.add(anchorPane, column, row); //(child,column,row)
                    GridPane.setMargin(anchorPane, new Insets(10));
                    String selecteIdO = String.valueOf(idF);
                    idFO.setText(selecteIdO);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Effacez les champs de la carte chosenFruitCard
            clearChosenCours();
        }
    }

    private void clearChosenCours() {
        idF.clear();
        nomF.clear();
        dateF.clear();
        dateD.clear();
        descripF.clear();
        prixF.clear();
        nbrCours.clear();
        nomF.setText("");
    }


    private void modifierFormation() {
        // Vérifiez si l'ID du cours sélectionné n'est pas vide
        if (selectedIdF != null && !selectedIdF.isEmpty()) {

            // Convertissez l'ID en entier si nécessaire
            int id = Integer.parseInt(selectedIdF);

            // Obtenez les nouvelles valeurs des champs de l'interface utilisateur
            String newName = nomF.getText();
            String newDateD = dateD.getText();
            String newDescription = descripF.getText();
            String newDateF = dateF.getText();
            String newPrix = prixF.getText();
            String newNbrCours = nbrCours.getText();
            ServiceFormation sp = new ServiceFormation();

            if (!sp.isValidPrix(Double.valueOf(newPrix)) && !sp.isValidDate(java.sql.Date.valueOf(newDateD), java.sql.Date.valueOf(newDateF))) {
                fxerrorprix1.setVisible(true);
                fxerrordate1.setVisible(true);
            } else if (!sp.isValidPrix(Double.parseDouble(prixF.getText()))) {
                fxerrorprix1.setVisible(true);
                fxerrordate1.setVisible(false);
            } else if (!sp.isValidDate(java.sql.Date.valueOf(newDateD), java.sql.Date.valueOf(newDateF))) {
                fxerrorprix1.setVisible(false);
                fxerrordate1.setVisible(true);
            } else {
                fxerrorprix1.setVisible(false);
                fxerrordate1.setVisible(false);
                try {
                    // Créez un nouvel objet Cours avec les valeurs mises à jour
                    Formation formation = new Formation();
                    formation.setIdFormation(id);
                    formation.setNom(newName);
                    formation.setDateDebut(java.sql.Date.valueOf(newDateD));
                    formation.setDateFin(java.sql.Date.valueOf(newDateF)); // Conversion String vers java.sql.Date
                    formation.setDescription(newDescription);
                    formation.setPrix(Double.parseDouble(newPrix));
                    formation.setNbrCours(Integer.parseInt(newNbrCours));


                    // Appelez la méthode de service pour mettre à jour le cours
                    sp.modifier(formation);

                    // Mettez à jour l'affichage global
                    listF.clear();
                    listF.addAll(getData());

                    grid.getChildren().clear(); // Effacez le contenu actuel du grid
                    initialize(null, null); // Réinitialisez l'affichage

                } catch (Exception e) {
                    e.printStackTrace();
                    // Gérez les erreurs ici
                }
            }
        }
    }

    public void AjouterOffre() {

        // Vérifiez que les DatePicker ont des valeurs sélectionnées
        if (dateDO.getValue() != null && dateFO.getValue() != null) {


            java.sql.Date dateDebut = java.sql.Date.valueOf(dateDO.getValue());
            java.sql.Date dateFin = java.sql.Date.valueOf(dateFO.getValue());
            ServiceOffre sp = new ServiceOffre();
            double nouveauPrix = Double.parseDouble(prixO.getText()); // Obtenez le nouveau prix de l'offre
            refreshDisplayAfterOffer(nouveauPrix); // Rafraîchissez l'affichage avec le nouveau prix
            if (!sp.isValidPrix(Double.parseDouble(prixO.getText())) && !sp.isValidDate(dateDebut, dateFin)) {
                fxerrorprix2.setVisible(true);
                fxerrordate2.setVisible(true);
            } else if (!sp.isValidPrix(Double.parseDouble(prixO.getText()))) {
                fxerrorprix2.setVisible(true);
                fxerrordate2.setVisible(false);
            } else if (!sp.isValidDate(dateDebut, dateFin)) {
                fxerrorprix2.setVisible(false);
                fxerrordate2.setVisible(true);
            } else {
                fxerrorprix2.setVisible(false);
                fxerrordate2.setVisible(false);
                try {
                    // Créez une nouvelle offre et ajoutez-la via votre service
                    Offre offre = new Offre(
                            Double.parseDouble(prixO.getText()),
                            descripO.getText(),
                            dateDebut,
                            dateFin,
                            Integer.parseInt(selectedIdF)// Assurez-vous que idFO a une valeur valide
                    );

                    sp.ajouter(offre);
                    // Mise à jour de l'interface utilisateur pour refléter le nouveau prix
                    // Affichez un message de succès
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setContentText("Offre ajoutée avec succès !");
                    alert.show();

                    tabPane.getSelectionModel().select(tabF);

                } catch (SQLException e) {
                    // Gérez les exceptions SQL ici
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur SQL");
                    alert.setContentText("Une erreur est survenue : " + e.getMessage());
                    alert.showAndWait();
                }
            }
        }
    }

    private void refreshDisplayAfterOffer(double nouveauPrix) {
        // Clear existing items
        grid.getChildren().clear();

        // Reload data
        listF = getData(); // Suppose this fetches the updated list of formations

        // Recreate display items for each formation
        int column = 0, row = 1;
        for (Formation formation : listF) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ItemF.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemF itemController = fxmlLoader.getController();
                if (itemController != null) {
                    itemController.setData(formation, myListener);
                    // Update display with new price if needed
                    itemController.updatePrixDisplay(nouveauPrix); // This assumes you want to update all displayed formations
                }

                grid.add(anchorPane, column++, row);
                GridPane.setMargin(anchorPane, new Insets(10));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}






