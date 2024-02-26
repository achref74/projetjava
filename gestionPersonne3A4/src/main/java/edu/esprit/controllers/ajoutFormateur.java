package edu.esprit.controllers;

import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.service.ServiceUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.security.*;

public class ajoutFormateur implements Initializable {
    @FXML
    private Button fxcv;

    @FXML
    private Text fxcvv;

    @FXML
    private Text fxdd;

    @FXML
    private TextField fxdispo;

    @FXML
    private ComboBox<String> fxgenre;

    @FXML
    private Text fxna;

    @FXML
    private ComboBox<String> fxniveauacademique;

    @FXML
    private PasswordField fxpwd;

    @FXML
    private Text fxpwd2;

    @FXML
    private Text fxspe;

    @FXML
    private ComboBox<String> fxspecialite;
    private String CVPath;
    private String imagePath;
    ServiceUser sp =new ServiceUser();

    @FXML
    void ajoutercv(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner un cv");
        File selectedFile = fileChooser.showOpenDialog(null);
        String cvpath=null;
        if (selectedFile != null)
        {  cvpath = selectedFile.getAbsolutePath();
            CVPath=cvpath;

        }

    }

    @FXML
    void ajouterphpto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sélectionner une photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        String imagepath=null;
        if (selectedFile != null)
            imagepath = selectedFile.getAbsolutePath();

        imagePath=imagepath;

    }

    @FXML
    void createAccount(ActionEvent event) throws NoSuchAlgorithmException {
        String[] data = getData();


        String a=fxniveauacademique.getSelectionModel().getSelectedItem().toString();
        String g=fxgenre.getSelectionModel().getSelectedItem().toString();
        String s=fxspecialite.getSelectionModel().getSelectedItem().toString();

        Formateur f = new Formateur(data[0],data[1],data[2], Date.valueOf(data[3]),data[4],Integer.parseInt(data[5]),hashof(fxpwd.getText()),imagePath,g,s,a,Integer.parseInt(fxdispo.getText()),CVPath);

        sp.ajouter(f);
        if (ServiceUser.codeAjout == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR !!");
            alert.setContentText("Email ou mot de passe est utilisé déja !");
            alert.showAndWait();
            ServiceUser.codeAjout = 0;
        }else{
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/menuApplication.fxml"));
                fxspe.getScene().setRoot(root);
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Sorry");
                alert.setTitle("Error");
                alert.show();
            }
            storeSession(sp.getIdByEmail(data[2]));
        }

    }



    private String[] getData() {
        String[] data=null;
        try {
            File file = new File("src/main/resources/fichiers/cache.txt");
            Scanner myReader = new Scanner(file);
            if (myReader.hasNextLine()) {
                String filecontent = myReader.nextLine();
                data =filecontent.split("&&");
            }

            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return data;
    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list= FXCollections.observableArrayList("bac+1","bac+2","bac+3","bac+4","bac+5");
        fxniveauacademique.setItems(list);
        ObservableList<String> list2= FXCollections.observableArrayList("Homme","Femme","Autre");
        fxgenre.setItems(list2);
        ObservableList<String> list3= FXCollections.observableArrayList("patisserie","menuiserie","agricole","cuisine","forgeron");
        fxspecialite.setItems(list3);

    }

    private String hashof(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] hash = md.digest();
        return new String(hash);
    }

    public void storeSession(int id){
        try {
            FileWriter myWriter = new FileWriter("src/main/resources/fichiers/session.txt");
            myWriter.write(String.valueOf(id));
            myWriter.close();
            System.out.println("Successfully wrote to the session file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

}
