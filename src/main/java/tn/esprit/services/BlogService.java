package tn.esprit.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tn.esprit.controler.Blogs;
import tn.esprit.interfaces.IService;
import tn.esprit.models.Blog;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Scanner;

public class BlogService implements IService<Blog> {
    private ObservableList<Blogs>blogs = FXCollections.observableArrayList();

    private static Connection cnx;

    public BlogService() {
        cnx = MyDataBase.getInstance().getCnx();
    }
    public Blog saisie() {
        String titre, content, imageb;
        Date date;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Titre :");
        titre = scanner.nextLine();
        System.out.println("Contenu :");
        content = scanner.nextLine();
        System.out.println("Image :");
        imageb = scanner.nextLine();
        System.out.println("Date (YYYY-MM-DD) :");
        String dateString = scanner.nextLine();
        date = Date.valueOf(dateString);

        return new Blog(titre, content, imageb, date);
    }
    public ArrayList<Blog> searchByTitle(String title) {
        ArrayList<Blog> searchResults = new ArrayList<>();
        String query = "SELECT * FROM blog WHERE titre LIKE ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(query);
            pstm.setString(1, "%" + title + "%");
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setId(rs.getInt("id"));
                blog.setTitre(rs.getString("titre"));
                blog.setDate(rs.getDate("date"));
                blog.setContent(rs.getString("content"));
                blog.setImageb(rs.getString("imageb"));
                searchResults.add(blog);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche par titre : " + e.getMessage());
        }
        return searchResults;
    }


    public void add(Blog b) {
        String insert = "INSERT INTO blog (titre, content, imageb, date, favoris) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pstm = cnx.prepareStatement(insert);
            pstm.setString(1, b.getTitre());
            pstm.setString(2, b.getContent());
            pstm.setString(3, b.getImageb());
            pstm.setDate(4, new java.sql.Date(b.getDate().getTime()));
            pstm.setBoolean(5, b.isFavoris());
            pstm.executeUpdate();
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Post ajouté avec succès !");
            } else {
                System.out.println("Échec de l'ajout du post.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Blog> getAll() {
        ArrayList<Blog> blogs = new ArrayList<>();
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
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des blogs : " + e.getMessage());
        }
        return blogs;
    }
    @Override
    public void displayAll(ArrayList<Blog> items) {
        for (Blog b : items) {
            System.out.println("ID: " + b.getId());
            System.out.println("Titre: " + b.getTitre());
            System.out.println("Date: " + b.getDate());
            System.out.println("Contenu: " + b.getContent());
            System.out.println("Image: " + b.getImageb());
            System.out.println();
        }
    }
    public static class ItemNotFoundException extends Exception {
        public ItemNotFoundException(String message) {
            super(message);
        }
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
    public boolean delete(Blog b) {

            String delete = "delete from blog  where id = ?";
        try {

            PreparedStatement pstm = cnx.prepareStatement(delete);
pstm.setInt(1,b.getId());
pstm.executeUpdate();
            System.out.println("Post delete avec succès !");//

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}