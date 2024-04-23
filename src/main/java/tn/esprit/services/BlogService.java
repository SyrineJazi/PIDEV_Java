package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import tn.esprit.models.Blog;
import tn.esprit.utils.MyDataBase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.sql.Connection;
public class BlogService {

    private static Connection cnx;

    public BlogService() {
        cnx = MyDataBase.getInstance().getCnx();
    }

    public boolean add(Blog b) {

        boolean success = false;
        String insert = "INSERT INTO blog (titre, content, imageb, date,favoris) VALUES (?, ?, ?, ?,?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(insert);
            pstm.setString(1, b.getTitre());
            pstm.setString(2, b.getContent());
            pstm.setString(3, b.getImageb());
            pstm.setDate(4, new java.sql.Date(b.getDate().getTime()));
            pstm.setBoolean(5, b.isFavoris());

            int rowsAffected = pstm.executeUpdate();
             if (rowsAffected > 0) {
                System.out.println("Post ajouté avec succès !");//
                success = true;
            } else {
                System.out.println("Échec de l'ajout du post.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
        return success;
    }


    public ObservableList<Blog> getAll() {
        ObservableList<Blog> blogs = FXCollections.observableArrayList();
        String qry = "SELECT * FROM blog";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(qry)) {
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setTitre(rs.getString("titre"));
                blog.setDate(rs.getDate("date"));
                blog.setContent(rs.getString("content"));
                blog.setImageb(rs.getString("imageb"));
                blog.setDate(rs.getDate("date"));
                blogs.add(blog);
                //System.out.print(blogs);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des blogs : " + e.getMessage());
        }
        return blogs;
    }


    public List<Blog> getAllBlogs() {
        ArrayList<Blog> blogs = new ArrayList<>();
        String query = "SELECT * FROM blog";
        try {
            Statement stm = cnx.createStatement();
            ResultSet rs = stm.executeQuery(query);
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setTitre(rs.getString("titre"));
                blog.setContent(rs.getString("content"));
                blog.setImageP(rs.getString("imageb"));
                blog.setDate(rs.getDate("date"));
                blog.setFavoris(rs.getBoolean("favoris"));
                blogs.add(blog);

            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des blogs : " + e.getMessage());
        }
        return blogs;
    }

    public void update(Blog b) {
        try {
            String req = "update blog  set titre =? , content =? ,imageb=? where id=?";
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setString(1, b.getTitre());
            pstm.setString(2, b.getContent());
            pstm.setString(3, b.getImageb());
            pstm.setInt(4, b.getId());
            //pstm.executeUpdate();


            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Post modifie avec succès !");//
            } else {
               System.out.println("Échec de modif  du post.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage()); }

    }
    public void delete(Blog b) {

            String delete = "delete from blog  where id = ?";
        try {

            PreparedStatement pstm = cnx.prepareStatement(delete);
pstm.setInt(1,b.getId());
pstm.executeUpdate();
            System.out.println("Post delete avec succès !");//

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}