package edu.esprit.controllers;

import edu.esprit.entities.User;
import edu.esprit.service.ServiceUser;
import edu.esprit.service.TwilloService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Login {
    @FXML
    private PasswordField f2;
    ServiceUser e = new ServiceUser();
    @FXML
    public void forgetpassword(ActionEvent actionEvent) throws IOException {


        FXMLLoader load = new FXMLLoader(getClass().getResource("/sendtothisnumber.fxml"));
        Parent root =load.load();
        Sendtothisnumber c2=  load.getController();
        Scene ss= new Scene(root);
        Stage s= new Stage();
        s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(ss);
        s.show();

    }

    @FXML
    public void loginfaceid(ActionEvent actionEvent) throws IOException {
        f1.getText();
        User user =  e.getOneByEmail(f1.getText());
        System.out.println(user);
        FXMLLoader load = new FXMLLoader(getClass().getResource("/loginfaceid.fxml"));
        Parent root =load.load();
        Loginfaceid c2=  load.getController();
        c2.getuser(user);
        Scene ss= new Scene(root);
        Stage s= new Stage();
        s=(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        s.setScene(ss);
        s.show();
    }

    private static class LoginAttempt {
        int attempts;
        LocalDateTime lastAttemptTime;

        LoginAttempt() {
            this.attempts = 1;
            this.lastAttemptTime = LocalDateTime.now();
        }

        void incrementAttempts() {
            this.attempts++;
            this.lastAttemptTime = LocalDateTime.now();
        }

        boolean canAttempt() {
            return LocalDateTime.now().isAfter(this.lastAttemptTime.plusMinutes(15)) || this.attempts < 3;
        }

        void reset() {
            this.attempts = 0;
            this.lastAttemptTime = LocalDateTime.now();
        }
    }

    private Map<String, LoginAttempt> loginAttempts = new HashMap<>();

    @FXML
    public Label errormsg;
    @FXML
    private TextField f1;

    public Login() {
        loadLoginAttempts();
    }


    private void sendEmailWithOTP(String toEmail, String otp) {
        final String username = "connectartisan6@gmail.com"; // Your email
        final String password = "gmbm pdin iwvu mkgy"; // Your email password
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("connectartisan6@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Login OTP");
            message.setText("Your OTP for login is: " + otp);
            Transport.send(message);
            System.out.println("OTP email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void navigateafficher(ActionEvent event) {
        try {
            String mail = f1.getText();
            String pass = f2.getText();
            ServiceUser s = new ServiceUser();
            errormsg.setText("");

            if (pass.isEmpty() || mail.isEmpty()) {
                showAlert("ERROR !!", "PLEASE FILL ALL DATA");
                return;
            }

            // Chargement des tentatives précédentes (si l'application vient d'être lancée)
            loadLoginAttempts();

            // Vérification si l'utilisateur existe
            if (!s.userExists(mail)) {
                showAlert("Login Failed", "User does not exist.");
                return;
            }

            // Obtention ou création de l'objet LoginAttempt pour cet utilisateur
            LoginAttempt attempt = loginAttempts.getOrDefault(mail, new LoginAttempt());

            // Vérification si le compte est actuellement banni
            if (!attempt.canAttempt()) {
                showAlert("Account Locked", "Your account has been locked due to too many failed login attempts. Please try again in 15 minutes.");
                return;
            }

            // Tentative de connexion
            String accountType = s.login(mail, pass);
            if (accountType .equals("Nulle")) {
                // Échec de la connexion, gestion de la tentative échouée
                attempt.incrementAttempts();
                loginAttempts.put(mail, attempt);
                saveLoginAttempts();

                if (attempt.attempts >= 3) {
                    showAlert("Too Many Failed Attempts", "Your account has been locked for 15 minutes.");
                } else {
                    showAlert("Login Failed", "Incorrect password. Please try again.");
                }
            } else {
                // Réussite de la connexion, réinitialisation des tentatives
                attempt.reset();
                loginAttempts.put(mail, attempt);
                saveLoginAttempts();

                // Redirection vers la scène appropriée
                if ("Admin".equals(accountType)) {
                    loadScene("/menuAdmin.fxml", event);
                } else {
                    initiateOTPProcess(mail, accountType, event);
                }
                storeSession(s.getIdByEmail(mail));
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An unexpected error occurred.");
        }
    }


    private void resetLoginAttempt(String mail) {
        LoginAttempt attempt = loginAttempts.getOrDefault(mail, new LoginAttempt());
        attempt.reset();
        loginAttempts.put(mail, attempt);
        saveLoginAttempts();
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

// Assurez-vous que les méthodes `loadScene`, `initiateOTPProcess`, `saveLoginAttempts`, `storeSession` et `userExists` sont correctement implémentées.

    private String generateOTP() {
        int length = 6;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(rnd.nextInt(characters.length())));
        }
        return sb.toString();
    }

    @FXML
    public void navigatesinscrire(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/creeCompte.fxml"));
            f1.getScene().setRoot(root);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }
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
    private void loadScene(String fxmlPath, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveLoginAttempts() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/fichiers/loginAttempts.txt"))) {
            for (Map.Entry<String, LoginAttempt> entry : loginAttempts.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue().attempts + "," + entry.getValue().lastAttemptTime);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLoginAttempts() {
        loginAttempts.clear(); // Clear existing attempts
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/fichiers/loginAttempts.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String email = parts[0];
                    LoginAttempt attempt = new LoginAttempt();
                    attempt.attempts = Integer.parseInt(parts[1]);
                    attempt.lastAttemptTime = LocalDateTime.parse(parts[2]);
                    loginAttempts.put(email, attempt);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Login attempts file not found. Starting fresh.");
        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
        }
    }
    private void initiateOTPProcess(String mail, String accountType, ActionEvent event) throws IOException {
        // Générer un OTP. Cette méthode suppose que vous avez une manière de générer un OTP unique.
        String otp = generateOTP();

        // Simuler l'envoi de l'OTP à l'utilisateur. Dans une application réelle, vous enverriez l'OTP via un service d'email ou de SMS.
        sendEmailWithOTP(mail, otp);

        // Demander à l'utilisateur de saisir l'OTP envoyé.
        TextInputDialog otpDialog = new TextInputDialog();
        otpDialog.setTitle("Verification OTP");
        otpDialog.setHeaderText("Une vérification OTP est nécessaire pour continuer");
        otpDialog.setContentText("Veuillez entrer l'OTP envoyé à votre adresse email:");

        // Afficher la boîte de dialogue et attendre la saisie de l'utilisateur.
        Optional<String> result = otpDialog.showAndWait();

        // Vérifier si l'OTP saisi par l'utilisateur correspond à celui généré/envoyé.
        if (result.isPresent() && result.get().equals(otp)) {
            // L'OTP est correct, naviguer vers la scène appropriée en fonction du type de compte.
            String fxmlPath = accountType.equals("Admin") ? "/menuAdmin.fxml" : "/menuApplication.fxml";
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            f1.getScene().setRoot(root);
        } else {
            // L'OTP est incorrect, afficher un message d'erreur.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de vérification OTP");
            alert.setHeaderText(null);
            alert.setContentText("L'OTP saisi est incorrect. Veuillez réessayer.");
            alert.showAndWait();
        }
    }
    private void handleFailedLoginAttempt(String mail) {
        LoginAttempt attempt = loginAttempts.getOrDefault(mail, new LoginAttempt());
        attempt.incrementAttempts();
        loginAttempts.put(mail, attempt);
        saveLoginAttempts();

        if (attempt.attempts >= 3) {
            showAlert("Account Locked", "Your account has been locked due to too many failed login attempts. Please try again in 15 minutes.");
        } else {
            showAlert("Login Failed", "Incorrect password. Please try again.");
        }
    }

}
