package tn.esprit.controler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tn.esprit.interfaces.MyListener;
import tn.esprit.models.Blog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import tn.esprit.interfaces.MyListener;
import tn.esprit.models.Blog;
import tn.esprit.services.BlogService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class BlogList implements Initializable {

    @FXML
    private Button favadd;
    @FXML
    private Button favoris;
    @FXML
    private Button blogEdit_btn;

    @FXML
    private ImageView blogimg;

    @FXML
    private VBox chosenblogCard;

    @FXML
    private Label date;

    @FXML
    private ImageView delete_button;

    @FXML
    private HBox goToAjouter;

    @FXML
    private GridPane grid;

    @FXML
    private TextField onsearchbloglabel;
    @FXML
    private ScrollPane scroll;
    @FXML
    private ImageView favorisbutton;
    @FXML
    private TextField searchTextField;
    @FXML
    private Label titre;
    private ObservableList<Blog> blogs = FXCollections.observableArrayList(getList_blogs());
    private ArrayList<Blog> getList_blogs(){
        BlogService sv = new BlogService();
        return sv.getAll();
    }
    private MyListener myListener;



    @FXML
    void deleteblog(MouseEvent event) {
        // Retrieve the chosen voyage name
        String name = titre.getText();

        Blog chosenblog = null; // Declare without initialization
        // Loop through the list of voyages to find the chosen voyage
        for (Blog unit : blogs) {
            if (unit.getTitre().equals(name)) {
                chosenblog = unit; // Assign the found voyage
                System.out.println("the thing worked");
                break; // Break out of the loop after finding the voyage
            }
        }
        // Check if the chosen voyage was found
        if (chosenblog != null) {
            // Pass the chosen voyage to the next page
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de la suppression");
            alert.setContentText("Etes-vous sûre de vouloir supprimer cet item ?");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                System.out.println("IM deleting");
               BlogService sv = new BlogService();
                sv.delete(chosenblog);

                // Update the observable list by removing the deleted voyage
               blogs.remove(chosenblog);
                // Update the UI by rebuilding the grid
                buildGrid();

            }
            else{
                alert.close();
            }
        } else {
            // Handle case where the chosen voyage is not found
            System.out.println("Chosen blog not found: " + name);
        }

    }
    @FXML
    void onblogedit(ActionEvent event) throws IOException {
        String name = titre.getText();
        Blog chosenblog = null;
        for (Blog unit : blogs) {
            if (unit.getTitre().equals(name)) {
                chosenblog = unit;
                System.out.println("modidffff avec succes ");
                break;
            }
        }
        if (chosenblog != null) {
            System.out.println("IM NAVIGATING");
            navigateToAjouterblog(chosenblog, event);

        } else {
            System.out.println("Chosen blog not found: " + name);
        }

    }

    @FXML
    void navigateToAjouterblog(MouseEvent event) {
        System.out.println("Je navigue");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/AjouterBlog.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }
    private boolean isGridEmpty(){
        ObservableList<Node> children = grid.getChildren();
        return children.isEmpty();
    }
    private void buildGrid() {
        if (!isGridEmpty()) {
            grid.getChildren().clear(); // Clear the grid
        }
        int column = 0;
        int row = 1;
        try {
            for (Blog blog : blogs) {    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cardblogs.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();


                Cardblogs controller = fxmlLoader.getController();
                controller.setData(blog, myListener); // Assurez-vous que BlogItem a une méthode setData appropriée
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child, column, row)

                // Définir la largeur de la grille
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                // Définir la hauteur de la grille
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int setChosenBlog(Blog blog) {
        titre.setText(blog.getTitre()); // Assurez-vous d'avoir une référence à l'élément correspondant dans votre FXML
        // Assurez-vous d'avoir une référence à l'élément correspondant dans votre FXML pour le prix ou toute autre information spécifique au blog
        File imageFile = new File(blog.getImageb()); // Assurez-vous d'avoir un attribut "image" dans votre classe Blog

        int ID = blog.getId(); // Assurez-vous d'avoir un attribut "id" dans votre classe Blog

        // Vérifiez si le fichier image existe
        if (imageFile.exists()) {
            // Chargez l'image depuis le fichier
            Image image = new Image(imageFile.toURI().toString());
          blogimg.setImage(image); // Assurez-vous d'avoir une référence à l'élément correspondant dans votre FXML pour afficher l'image
           Cardblogs.setStyle("-fx-background-color: #eef4f3" + ";\n" +
                    "    -fx-background-radius: 30;");
        } else {
           //  Gérez le cas où le fichier image n'est pas trouvé
            System.out.println("Image file not found: " + blog.getImageb());
            // Vous pouvez éventuellement définir une image par défaut ou gérer la situation différemment
        }
        return ID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BlogService blogService = new BlogService();
        blogs.addAll(blogService.getAll());
        if (!blogs.isEmpty()) {
           setChosenBlog(blogs.get(0)); // Définir le premier blog comme choisi, ou effectuer toute autre initialisation nécessaire
            myListener = new MyListener() {
                @Override
                public void onClickListener(Blog blog) {
                   setChosenBlog(blog); // Mettre à jour le blog choisi lorsqu'un élément est cliqué dans la grille
                }
            };
        }
        buildGrid(); // Construire la grille avec les blogs disponibles
    }




    private void navigateToAjouterblog(Blog chosenblog, ActionEvent event) {

        System.out.println("Je navigue");

        FXMLLoader loader;
        Parent root;
        try {
            loader = new FXMLLoader(getClass().getResource("/AjouterBlog.fxml"));
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    public ArrayList<Blog> findByNom_ouDestination(String nom) throws BlogService.ItemNotFoundException {
        ArrayList<Blog> liste_des_blogs = this.getList_blogs();
        ArrayList<Blog> found_items = new ArrayList<>();
        Iterator<Blog> itr = liste_des_blogs.iterator();
        while (itr.hasNext()) {
            Blog blog = itr.next();
            if (blog.getTitre().toLowerCase().contains(nom) ){
                found_items.add(blog);
            }
        }
        if (found_items.isEmpty()) {
            throw new BlogService.ItemNotFoundException("Le blog du nom ou destination " + nom + " n'existe pas.");
        }
        return found_items;
    }
    @FXML
    void onsearchvoyage(ActionEvent event) throws BlogService.ItemNotFoundException {
            String keyWord = onsearchbloglabel.getText();
           BlogService sv = new BlogService();
            ArrayList<Blog> foundItems = sv.searchByTitle(keyWord);
            blogs = FXCollections.observableArrayList(foundItems);
            buildGrid();

        }

    @FXML
    void onsearchfavoris(ActionEvent event) {
        BlogService sv = new BlogService();
        ArrayList<Blog> foundItems = sv.searchByFavoris(true); // Recherche des blogs avec favoris = true
        blogs = FXCollections.observableArrayList(foundItems);
        buildGrid();
    }

    @FXML
    void onfavadd(ActionEvent event) {

            String name = titre.getText();
            Blog chosenBlog = null;
            for (Blog unit : blogs) {
                if (unit.getTitre().equals(name)) {
                    chosenBlog = unit;
                    break;
                }
            }
            if (chosenBlog != null) {
                chosenBlog.setFavoris(true);
                BlogService blogService = new BlogService();
                blogService.addToFavorites(chosenBlog);

                buildGrid();
                // Update the blog in the database or wherever it's stored

                blogService.update(chosenBlog);

                // Optionally, provide feedback to the user
                System.out.println("Blog ajouté aux favoris avec succès !");
            } else {
                // Provide feedback to the user that no blog is chosen
                System.out.println("Aucun blog choisi.");
            }
        }
    }