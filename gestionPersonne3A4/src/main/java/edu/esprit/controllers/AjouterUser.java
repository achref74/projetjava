package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.sql.Date;
import java.io.IOException;

public class AjouterUser {
    @FXML
    private TextField fxfichier;
    @FXML
    private Label fxerrormail;

    @FXML
    private Label fxerrornum;
    @FXML
    public TextField fxdispo;
    @FXML
    public RadioButton fxf;
    @FXML
    public RadioButton fxc;
    @FXML
    public Text fxcvv;
    @FXML
    public Text fxna;
    @FXML
    public Text fxspe;
    @FXML
    public Text fxns;
    @FXML
    public Text fxdd;
    @FXML
    private DatePicker fxdate;
    @FXML
    private TextField fxadresse;
    @FXML
    private TextField fxnumtel;
    @FXML
    private PasswordField fxpwd;
    @FXML
    private ComboBox fxniveauscolaire;
    @FXML
    private ComboBox fxspecialite;
    @FXML
    private ComboBox fxniveauacademique;
    @FXML
    private Button fxcv;
    @FXML
    private TextField fxemail;
    @FXML
    private TextField fxprenom;
    @FXML
    private TextField fxnom;

    @FXML
    private Text fxpwd2;
    private final ServiceUser sp = new ServiceUser();
    String userType = null;

    public void initialize() {
        // Masquer les attributs spécifiques aux clients et formateurs par défaut
        fxniveauacademique.setVisible(false);
        fxniveauscolaire.setVisible(false);
        fxspecialite.setVisible(false);
        fxdispo.setVisible(false);
        fxcv.setVisible(false);
        fxspe.setVisible(false);
        fxcvv.setVisible(false);
        fxns.setVisible(false);
        fxna.setVisible(false);
        fxdd.setVisible(false);
        fxfichier.setVisible(false);
        fxpwd.setVisible(false);
        fxpwd2.setVisible(false);

    }


    public void clickFormateur(ActionEvent actionEvent) {
        fxniveauacademique.setVisible(true);
        ObservableList<String> list= FXCollections.observableArrayList("bac+1","bac+2","bac+3","bac+4","bac+5");
        fxniveauacademique.setItems(list);
        fxniveauscolaire.setVisible(false);
        fxspecialite.setVisible(true);
        ObservableList<String> list2= FXCollections.observableArrayList("patisserie","menuiserie","agricole","cuisine","forgeron");
        fxspecialite.setItems(list2);
        fxdispo.setVisible(true);
        fxcv.setVisible(true);
        fxspe.setVisible(true);
        fxcvv.setVisible(true);
        fxna.setVisible(true);
        fxdd.setVisible(true);
        fxns.setVisible(false);
        fxc.setSelected(false);
        fxfichier.setVisible(true);
        fxpwd.setVisible(true);
        fxpwd2.setVisible(true);



    }

    public void clickClient(ActionEvent actionEvent) {
        fxniveauacademique.setVisible(false);
        fxniveauscolaire.setVisible(true);
        ObservableList<String> list= FXCollections.observableArrayList("ecole","college","lycee");
        fxniveauscolaire.setItems(list);
        fxspecialite.setVisible(false);
        fxdispo.setVisible(false);
        fxcv.setVisible(false);
        fxns.setVisible(true);

        fxspe.setVisible(false);
        fxcvv.setVisible(false);
        fxna.setVisible(false);
        fxdd.setVisible(false);
        fxf.setSelected(false);
        fxfichier.setVisible(false);
        fxpwd.setVisible(true);
        fxpwd2.setVisible(true);


    }


    public void createAccount(ActionEvent actionEvent) {
        try {
            if (!sp.isValidEmail(fxemail.getText())) {

                fxerrormail.setVisible(true);
                fxerrornum.setVisible(false);

            } else if (!(sp.isValidPhoneNumber(Integer.parseInt(fxnumtel.getText())))){
                fxerrornum.setVisible(true);
                fxerrormail.setVisible(false);
            } else if (!(sp.isValidPhoneNumber(Integer.parseInt(fxnumtel.getText()))) && !sp.isValidEmail(fxemail.getText())) {

                fxerrornum.setVisible(true);
                fxerrormail.setVisible(true);

            }else{
                fxerrornum.setVisible(false);
                fxerrormail.setVisible(false);
                if (fxc.isSelected()) {
                    userType = "client";
                } else if (fxf.isSelected()) {
                    userType = "formateur";
                }


                if (userType != null) {
                    switch (userType) {

                        case "client":
                            String a=fxniveauscolaire.getSelectionModel().getSelectedItem().toString();
                            int b= Integer.parseInt(fxnumtel.getText());
                            Date d =Date.valueOf(fxdate.getValue());


                            Client  c = new Client(fxnom.getText(), fxprenom.getText(), fxemail.getText(),d, fxadresse.getText(),b, fxpwd.getText(),a);
                            sp.ajouter(c);
                            break;
                        case "formateur":
                            Date d1 =Date.valueOf(fxdate.getValue());
                            int b1= Integer.parseInt(fxnumtel.getText());
                            String a1=fxspecialite.getSelectionModel().getSelectedItem().toString();
                            String a11=fxniveauacademique.getSelectionModel().getSelectedItem().toString();
                            int a111= Integer.parseInt(fxdispo.getText());

                            Formateur  f = new Formateur(fxnom.getText(), fxprenom.getText(), fxemail.getText(), d1, fxadresse.getText(),b1, fxpwd.getText(),a1,a11,a111,fxfichier.getText());
                            sp.ajouter(f);
                            break;


                    }
                }Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("GG");
                alert.show();}
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }
    public void navigatetoAfficherPersonneAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            fxnom.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    public void ajoutercv(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un cv");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null)
        { String cvpath = selectedFile.getAbsolutePath();
            fxfichier.setText(cvpath);

        }
    }
}
