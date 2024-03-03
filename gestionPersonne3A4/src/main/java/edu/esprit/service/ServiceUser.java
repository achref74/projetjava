package edu.esprit.service;

import edu.esprit.entities.Admin;
import edu.esprit.entities.Client;
import edu.esprit.entities.Formateur;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public  class ServiceUser implements IServiceUser<User> {
    Connection cnx = DataSource.getInstance().getCnx();
    List<User> lu = getAll();
    public static int codeAjout = 0;
    @Override
    public void ajouter(User user) {
        List<User> l1 = getAll().stream().filter(x->x.getMdp().equals(user.getMdp()) || x.getEmail().equals(user.getEmail())).collect(Collectors.toList());
        if(l1.isEmpty()){
            if (user instanceof Client c) {
                String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`imageProfil`,`genre`,`mdp`,`role`,`niveau_scolaire`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps = cnx.prepareStatement(req);

                    ps.setString(1, c.getNom());
                    ps.setString(2, c.getPrenom());
                    ps.setString(3, c.getEmail());
                    ps.setDate(4, c.getDateNaissance());
                    ps.setString(5, c.getAdresse());
                    if (!isValidPhoneNumber(c.getNumtel())) {
                        System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                        return;
                    }

                    ps.setInt(6, c.getNumtel());
                    ps.setString(7, c.getImg());
                    ps.setString(8, c.getGenre());

                    ps.setString(9, c.getMdp());
                    ps.setInt(10, 0);
                    ps.setString(11, c.getNiveau_scolaire());

                    ps.executeUpdate();
                    System.out.println("Client added !");
                    lu = getAll();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else if (user instanceof Formateur f) {
                String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`imageProfil`,`genre`,`mdp`,`role`,`specialite`,`niveauAcademique`,`disponiblite`,`cv`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps = cnx.prepareStatement(req);

                    ps.setString(1, f.getNom());
                    ps.setString(2, f.getPrenom());
                    ps.setString(3, f.getEmail());
                    ps.setDate(4, f.getDateNaissance());
                    ps.setString(5, f.getAdresse());
                    if (!isValidPhoneNumber(f.getNumtel())) {
                        System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                        return;
                    }
                    ps.setInt(6, f.getNumtel());
                    ps.setString(7, f.getImg());
                    ps.setString(8, f.getGenre());
                    ps.setString(9, f.getMdp());
                    ps.setInt(10, 1);
                    ps.setString(11, f.getSpecialite());
                    ps.setString(12, f.getNiveauAcademique());
                    ps.setInt(13, f.getDisponibilite());
                    ps.setString(14, f.getCv());

                    ps.executeUpdate();
                    System.out.println("formateur added !");
                    lu = getAll();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            } else if (user instanceof Admin a) {
                String req = "INSERT INTO `User`(`nom`,`prenom`,`email`,`dateNaissance`,`adresse`,`numtel`,`imageProfil`,`genre`,`mdp`,`role`) VALUES (?,?,?,?,?,?,?,?,?,?)";
                try {
                    PreparedStatement ps = cnx.prepareStatement(req);

                    ps.setString(1, a.getNom());
                    ps.setString(2, a.getPrenom());
                    ps.setString(3, a.getEmail());
                    ps.setDate(4, a.getDateNaissance());
                    ps.setString(5, a.getAdresse());
                    ps.setInt(6, a.getNumtel());
                    ps.setString(7, a.getImg());
                    ps.setString(8, a.getGenre());
                    ps.setString(9, a.getMdp());
                    ps.setInt(10, 2);


                    ps.executeUpdate();
                    System.out.println("admin added !");
                    lu = getAll();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }else codeAjout = -1;

    }

    @Override
    public void modifier(User user) {
        if (user instanceof Client c) {
            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,imageProfil=?,genre=?,mdp=?,niveau_scolaire=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1, c.getNom());
                ps.setString(2, c.getPrenom());
                if (!isValidEmail(c.getEmail())) {
                    System.out.println("Adresse e-mail invalide !");
                    return;
                }
                ps.setString(3, c.getEmail());
                ps.setDate(4, c.getDateNaissance());
                ps.setString(5, c.getAdresse());
                if (!isValidPhoneNumber(c.getNumtel())) {
                    System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                    return;
                }
                ps.setInt(6, c.getNumtel());
                ps.setString(7, c.getImg());
                ps.setString(8, c.getGenre());
                ps.setString(9, c.getMdp());
                ps.setString(10, c.getNiveau_scolaire());
                ps.setInt(11, c.getId());
                ps.executeUpdate();
                System.out.println("Client updated !");
                lu = getAll();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (user instanceof Formateur f) {
            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,imageProfil=?,genre=?,mdp=?,specialite=?,niveauAcademique=?,disponiblite=?,cv=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1, f.getNom());
                ps.setString(2, f.getPrenom());
                if (!isValidEmail(f.getEmail())) {
                    System.out.println("Adresse e-mail invalide !");
                    return;
                }
                ps.setString(3, f.getEmail());
                ps.setDate(4, f.getDateNaissance());
                ps.setString(5, f.getAdresse());
                if (!isValidPhoneNumber(f.getNumtel())) {
                    System.out.println("Le numéro de téléphone doit contenir exactement 8 chiffres !");
                    return;
                }
                ps.setInt(6, f.getNumtel());
                ps.setString(7, f.getImg());
                ps.setString(8, f.getGenre());
                ps.setString(9, f.getMdp());
                ps.setString(10, f.getSpecialite());
                ps.setString(11, f.getNiveauAcademique());
                ps.setInt(12, f.getDisponibilite());
                ps.setString(13, f.getCv());
                ps.setInt(14, f.getId());
                ps.executeUpdate();
                System.out.println("formateur updated !");
                lu = getAll();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (user instanceof Admin a) {

            String req = "UPDATE  `User`set nom=?,prenom=?,email=?,dateNaissance=?,adresse=?,numtel=?,imageProfil=?,genre=?,mdp=? where idUser=?";
            try {
                PreparedStatement ps = cnx.prepareStatement(req);

                ps.setString(1, a.getNom());
                ps.setString(2, a.getPrenom());
                ps.setString(3, a.getEmail());
                ps.setDate(4, a.getDateNaissance());
                ps.setString(5, a.getAdresse());
                ps.setInt(6, a.getNumtel());
                ps.setString(7, a.getImg());
                ps.setString(8, a.getGenre());
                ps.setString(9, a.getMdp());
                ps.setInt(10, a.getId());
                ps.executeUpdate();
                System.out.println("admin updated !");
                lu = getAll();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM `User` WHERE idUser=?";
        String req1 = "SELECT * FROM User WHERE idUser=" + id;
        try {


            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req1);
            if (res.next()) {
                int role = res.getInt("role");
                if (role == 0)
                    System.out.println("Client with id= " + id + " deleted !");
                else if (role == 1)
                    System.out.println("Formateur with id= " + id + " deleted !");
                else if (role == 2)
                    System.out.println("Admin with id= " + id + " deleted !");
            } else {
                System.out.println("No user found with id= " + id);
                return;
            }
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            lu = getAll();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public User getOneById(int id) {

        String req = "SELECT *  from `User` where idUser=" + id;
        Client c = null;
        Formateur f = null;
        Admin a = null;
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            if (res.next()) {
                int idUser = res.getInt("idUser");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String email = res.getString("email");
                Date dateNaissance = res.getDate("dateNaissance");
                String adresse = res.getString("adresse");
                int numtel = res.getInt("numtel");
                String img = res.getString("imageProfil");
                String genre = res.getString("genre");
                String mdp = res.getString("mdp");
                int role = res.getInt("role");
                if (role == 1) {
                    String specialite = res.getString("specialite");
                    String niveau_Academique = res.getString("niveauAcademique");
                    int disonibilite = res.getInt("disponiblite");
                    String cv = res.getString("cv");
                    f = new Formateur(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp,img,genre, specialite, niveau_Academique, disonibilite, cv);
                    return f;
                } else if (role == 0) {
                    String niveau_scolaire = res.getString("niveau_scolaire");
                    c = new Client(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp,img,genre, niveau_scolaire);
                    return c;
                } else if (role == 2) {
                    a = new Admin(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp,img,genre);
                    return a;
                }
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return f;
    }

    public List<User> getAll() {


        List<User> users = new ArrayList<>();

        String req = "Select * from User ";
        try {
            Statement st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);
            while (res.next()) {
                int role = res.getInt("role");

                int idUser = res.getInt("idUser");
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                String email = res.getString("email");
                Date dateNaissance = res.getDate("dateNaissance");
                String adresse = res.getString("adresse");
                int numtel = res.getInt("numtel");
                String img = res.getString("imageProfil");
                String genre = res.getString("genre");
                String mdp = res.getString("mdp");
                User u = null;
                if (role == 0) {
                    String niveau_scolaire = res.getString("niveau_scolaire");

                    u = new Client(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp,img,genre, niveau_scolaire);
                } else if (role == 1) {
                    String specialite = res.getString("specialite");
                    String niveauAcademique = res.getString("niveauAcademique");
                    int disponibilite = res.getInt("disponiblite");
                    String cv = res.getString("cv");
                    u = new Formateur(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp, img,genre,specialite, niveauAcademique, disponibilite, cv);
                } else if (role == 2) {
                    u = new Admin(idUser, nom, prenom, email, dateNaissance, adresse, numtel, mdp,img,genre);
                }
                users.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return users;
    }

    public String login(String email, String pwd) throws NoSuchAlgorithmException {
        List<User> l = getAll();
        for (User u :l) System.out.println(u);
        String hashpwd = hashof(pwd);
        List<User> l1 = l.stream().filter(x -> (x.getEmail().equals(email)&& x.getMdp().equals(hashpwd)) ||
                (x.getEmail().equals(email)&& x.getMdp().equals(pwd)) ).collect(Collectors.toList());
        System.out.println(l1);
        if (!l1.isEmpty()) {
            User u = l1.get(0);

            if (u instanceof Client)
                return "client";
            else if (u instanceof Formateur)
                return "Formateur";
            else if (u instanceof Admin) return "Admin";
            else {
                System.out.println("!!!!!!!!!!!!!!!!");
                return "";
            }
        }
        else return "Nulle";
    }
    public boolean isValidPhoneNumber(int numTel) {
        // Conversion du numéro de téléphone en une chaîne pour obtenir sa longueur
        String numTelStr = String.valueOf(numTel);
        return numTelStr.length() == 8;
    }
    public boolean isValidEmail(String email) {
        // Utilisation d'une expression régulière pour vérifier le format de l'e-mail
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public int getIdByEmail(String mail) {
        return getAll().stream().filter(x->x.getEmail().equals(mail)).collect(Collectors.toList()).get(0).getId();
    }

    public List<User> rechercher(String text, String role) {
        if (role.equals("client")) return lu.stream().filter(x->x instanceof Client).filter(x->x.getNom().contains(text) || x.getPrenom().contains(text)).collect(Collectors.toList());
        else if (role.equals("formateur")) return lu.stream().filter(x->x instanceof Formateur).filter(x->x.getNom().contains(text) || x.getPrenom().contains(text)).collect(Collectors.toList());
        else if(role.equals("all")) return lu.stream().filter(x->x instanceof Formateur || x instanceof Client).filter(x-> (x.getNom().contains(text) || x.getPrenom().contains(text)) ).collect(Collectors.toList());
        return null;
    }
    private String hashof(String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte[] hash = md.digest();
        return new String(hash);
    }
    public boolean userExists(String email) {
        // Utilise la liste des utilisateurs récupérés par getAll() pour vérifier si l'email existe.
        return getAll().stream().anyMatch(user -> user.getEmail().equals(email));
    }

}




