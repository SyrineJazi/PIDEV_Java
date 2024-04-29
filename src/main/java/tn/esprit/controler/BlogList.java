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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import tn.esprit.interfaces.MyListener;
import tn.esprit.models.Blog;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BlogList implements Initializable {

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
    private ScrollPane scroll;

    @FXML
    private Label titre;
    private ObservableList<Blog> blogs = FXCollections.observableArrayList();

    private MyListener myListener;
    @FXML
    void deleteblog(MouseEvent event) {

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
            for (Blog blog : blogs) {   FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/cardblogs.fxml")); // Assurez-vous que le chemin est correct
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
           //  image = new Image(imageFile.toURI().toString());
           // image.setImage(image); // Assurez-vous d'avoir une référence à l'élément correspondant dans votre FXML pour afficher l'image
           // chosenBlogCard.setStyle("-fx-background-color: #eef4f3" + ";\n" +
                  //  "    -fx-background-radius: 30;");
        } else {
            // Gérez le cas où le fichier image n'est pas trouvé
           // System.out.println("Image file not found: " + blog.getImageb());
            // Vous pouvez éventuellement définir une image par défaut ou gérer la situation différemment
        }
        return ID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    @FXML
    void onblogedit(ActionEvent event) {

    }

}
