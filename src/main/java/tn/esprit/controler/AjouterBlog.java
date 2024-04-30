package tn.esprit.controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Blog;
import tn.esprit.services.BlogService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;

public class AjouterBlog {

    @FXML
    private TextField description;

    @FXML
    private TextField imageb;

    @FXML
    private TextField titre;

    @FXML
    private Button Blog_btn;

    @FXML
    private TableView<Blog> table;

    @FXML
    private Button btndelete;

    @FXML
    private TextField searchField;

    @FXML
    private Button btnupdate;

    @FXML
    private Button btnadd;

    private ObservableList<Blog> blogs = FXCollections.observableArrayList();

   @FXML
    void choisirImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            imageb.setText(file.getAbsolutePath());

        }
    }
    @FXML
    void search(ActionEvent event) {
        BlogService service = new BlogService();
        FilteredList<Blog> filteredBlogs = new FilteredList<>(blogs, b -> true);
        searchField.textProperty().addListener((observableValue, oldvalue, newvalue) -> {
            filteredBlogs.setPredicate(blog -> {
                if (newvalue == null || newvalue.isEmpty()) {
                    return true;
                }
                String lowercaseFilter = newvalue.toLowerCase();
                return blog.getTitre().toLowerCase().contains(lowercaseFilter);
            });
        });
        SortedList<Blog> sortedBlogs = new SortedList<>(filteredBlogs);
        sortedBlogs.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedBlogs);
    }



    @FXML
    void navigateToListBlogs(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/BlogList.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre actuelle
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initialize() {
        showBlog();
    }



    private void afficherErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    public void showBlog() {
        BlogService blogService = new BlogService();
        blogs.clear();
        blogs.addAll(blogService.getAll());
        table.setItems(blogs);
    }

    @FXML
    void getData(MouseEvent event) {
        Blog blog = table.getSelectionModel().getSelectedItem();
        titre.setText(blog.getTitre());
        description.setText(blog.getContent());
        imageb.setText(blog.getImageb());
        btnadd.setDisable(true);
    }

    @FXML
    void delete(ActionEvent event) {
        Blog b = table.getSelectionModel().getSelectedItem();
        BlogService service = new BlogService();
        service.delete(b);
        showBlog();
    }

    @FXML
    void update(ActionEvent event) {
        Blog b = table.getSelectionModel().getSelectedItem();
        BlogService blogService = new BlogService();
        b.setTitre(titre.getText());
        b.setContent(description.getText());
        b.setImageb(imageb.getText());
        blogService.update(b);
        showBlog();
    }

    @FXML
    void showDetails(ActionEvent event) {
        Blog selectedBlog = table.getSelectionModel().getSelectedItem();
        if (selectedBlog != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Détails du Blog");
            alert.setHeaderText(selectedBlog.getTitre());
            alert.setContentText(selectedBlog.getContent());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun blog sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un blog pour afficher les détails.");
            alert.showAndWait();
        }
    }





    public void ajouterblog(ActionEvent actionEvent) {

            // Créer un ID aléatoire pour le nouveau blog
            Random random = new Random();
            int ID = random.nextInt(Integer.MAX_VALUE) + 1;


        String file = imageb.getText();

        if (!Files.exists(Paths.get(file))) {
            afficherErreur("Le fichier spécifié n'existe pas.");
            return;
        }
            // Récupérer les données du formulaire
            String titreText = titre.getText();
            String descriptionText = description.getText();
            String imagebText = imageb.getText();





            // Créer un objet Blog avec les données saisies
            Date currentDate = new Date();
            Blog blog = new Blog(ID, titreText, descriptionText, imagebText, currentDate, true);
            blog.setFavoris(false);

            // Ajouter le blog à la base de données
            try {
                BlogService service = new BlogService();
                service.add(blog);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Le blog a été ajouté avec succés.");
                alert.show();
                showBlog();
            } catch (Exception e) {
                afficherErreur("Une erreur s'est produite lors de l'ajout du blog : " + e.getMessage());
                e.printStackTrace();
            }

    }


}
