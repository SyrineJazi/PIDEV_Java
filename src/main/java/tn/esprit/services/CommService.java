package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.utils.MyDataBase;
import tn.esprit.utils.MyDataBase;
import tn.esprit.models.comm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.sql.*;

public class CommService {
    private static Connection cnx;

    public CommService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public List<comm> getAllComments() {
        List<comm> comments = new ArrayList<>();
        String query = "SELECT * FROM comm";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int blogId = resultSet.getInt("blog_id");
                String contenu = resultSet.getString("contenu");
                // Vous devez adapter cette partie en fonction de vos besoins
                // J'imagine que created_at est une colonne de type Date dans votre table
                // Vous pouvez utiliser resultSet.getDate() pour récupérer une date
                // et créer un objet Date approprié
                // Je vous laisse le soin de compléter cette partie selon votre structure de base de données
                // Date createdAt = resultSet.getDate("created_at");
                // Comment créer un objet Date dépend du format stocké dans la base de données
                // Par exemple, vous pouvez utiliser SimpleDateFormat pour un formatage personnalisé
                // Veuillez adapter cette partie en fonction de votre base de données
                comm comm = new comm(id, blogId, contenu, null); // Remplacer null par createdAt
                comments.add(comm);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }
    public void addComment(comm comm) {
        String query = "INSERT INTO comm (blog_id, contenu) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, comm.getBlog_id());
            preparedStatement.setString(2, comm.getContenu());
            preparedStatement.executeUpdate();
            System.out.println("commentaire ajouté avec succès !");//

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateComment(comm comment) {
        String query = "UPDATE comm SET contenu = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setString(1, comment.getContenu());
            preparedStatement.setInt(2, comment.getId());
            preparedStatement.executeUpdate();
            System.out.println("Commentaire mis à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions ou les erreurs de connexion à la base de données
        }
    }
    public ObservableList<comm> getAll() {
        ObservableList<comm> comments = FXCollections.observableArrayList();
        String query = "SELECT * FROM comm";
        try (Statement statement = cnx.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int blog_id = resultSet.getInt("blog_id");
                String contenu = resultSet.getString("contenu");
                Date created_at = resultSet.getDate("created_at");

                comm comment = new comm(id, blog_id, contenu, created_at);
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des commentaires : " + e.getMessage());
        }
        return comments;
    }


    // Méthode pour récupérer une ObservableList de commentaires
    public ObservableList<comm> getObservableList() {
        return FXCollections.observableArrayList(getAll());
    }
    public void deleteComment(int commentId) {
        String query = "DELETE FROM comm WHERE id = ?";
        try {
            PreparedStatement preparedStatement = cnx.prepareStatement(query);
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
            System.out.println("Commentaire supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions ou les erreurs de connexion à la base de données
        }
    }

}
