package tn.esprit.controler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.models.Blog;
import tn.esprit.services.BlogService;
import javafx.scene.image.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;

public class AjouterBlog  implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField description;

    @FXML
    private TextField imageb;

    @FXML
    private TextField titre;


    @FXML
    private Button Blog_btn;
    @FXML
    private TableColumn<Blog, String> colcontenu;

    @FXML
    private TableColumn<Blog, Integer> colid;

    @FXML
    private TableColumn<Blog, String> colimageb;

    @FXML
    private TableColumn<Blog, String> coltitre;


    @FXML
    private TableView<Blog> table;
int id ;

    @FXML
    private Button btndelete;
    @FXML
    private TextField searchField;

    @FXML
    private Button btnupdate;
    @FXML
    private Button btnadd;
    @FXML
    private ImageView imagep;
    @FXML
    private TableColumn<Blog, Date> coldatde;
    @FXML
    private TableColumn<?, ?> details;

    @FXML
    private Button detailsButton;
    private Object SwingFXUtils;
    private ObservableList<Blog> blogs = FXCollections.observableArrayList();
    @FXML
    void search(ActionEvent event) {
        BlogService service=new BlogService();
        // Créez un FilteredList à partir de votre liste observable de blogs
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
        // Mettez à jour la table avec les blogs filtrés
        SortedList<Blog> sortedBlogs = new SortedList<>(filteredBlogs);
        sortedBlogs.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedBlogs);

    }
    private void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String imagePath = file.toURI().toString();
            Image image = new Image(imagePath);
            //imageviewFile.setImage(image);
            imageb.setText(file.getAbsolutePath());
        }
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
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TableView<Blog> tftableview;
   // @Override
    public void initialize(URL url, ResourceBundle rb) {
showblog();

        //BlogService service = new BlogService();
      //  ObservableList<Blog> list = service.getAll();
       // System.out.print(list);
       // coid.setCellValueFactory(new PropertyValueFactory<>("id"));
       // coltitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
       // colcontenu.setCellValueFactory(new PropertyValueFactory<>("content"));
        //colimage.setCellValueFactory(new PropertyValueFactory<>("imageb"));
       // tftableview.setItems(list);
    }

    @FXML
    void ajouterblog(ActionEvent event) {
        BlogService service = new BlogService();

        // Générer un ID aléatoire
        Random random = new Random();
        int ID = random.nextInt(Integer.MAX_VALUE) + 1;

        // Contrôle de saisie - début
        String file = imageb.getText();
       // if (!Files.exists(Paths.get(file))) {
        //    afficherErreur("Le fichier spécifié n'existe pas.");
        //    return;
      //  }

        String titreText = titre.getText();
        String descriptionText = description.getText();
        String imagebText = imageb.getText();

        // Vérifier si tous les champs obligatoires sont remplis
        if (titreText.isEmpty() || descriptionText.isEmpty() || imagebText.isEmpty()) {
            afficherErreur("Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier que la longueur de la description est suffisante
        if (descriptionText.length() < 5) {
            afficherErreur("La description doit avoir au moins 5 caractères.");
            return;
        }

        // Contrôle de saisie - fin

        // Créer un objet Blog avec les données saisies
        Date currentDate = new Date();
        Blog blog = new Blog(ID, titreText, descriptionText, imagebText, currentDate, true);
        blog.setFavoris(false);

        // Ajouter le blog à la base de données
        service.add(blog);

        // Afficher une boîte de dialogue d'information
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Le blog a été ajouté avec succès.");
        alert.show();





            //FXMLLoader loader= new FXMLLoader(getClass().getResource("/afficherblog.fxml"));
       // try {
          //  Parent root =loader.load();
       // Afficherblog Afficherblog=loader.getController();
       // Afficherblog.setTitre(titre.getText());
       // Afficherblog.setContenu(description.getText());
       // Afficherblog.setImageb(imageb);
       // titre.getScene().setRoot(root);
        //} catch (IOException e) {throw new RuntimeException(e);}
       // refreshtable();
    }
    private void afficherErreur(String message)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    public void showblog(){
        BlogService blogService = new BlogService();
        ObservableList<Blog> list= blogService.getAll();
        table.setItems(list);
        colid.setCellValueFactory(new PropertyValueFactory<Blog , Integer>("id"));
        coltitre.setCellValueFactory(new PropertyValueFactory<Blog , String>("titre"));
        colcontenu.setCellValueFactory(new PropertyValueFactory<Blog , String>("content"));
        colimageb.setCellValueFactory(new PropertyValueFactory<Blog , String>("imageb"));
        coldatde.setCellValueFactory(new PropertyValueFactory<Blog , Date>("date"));
}
    @FXML
    void getData(MouseEvent event) {
        Blog blog= table.getSelectionModel().getSelectedItem();
       id = blog.getId();
       titre.setText(blog.getTitre());
       description.setText(blog.getContent());
       imageb.setText(blog.getImageb());

       btnadd.setDisable(true);
    }
    @FXML
    void delete(ActionEvent event) {
        Blog b=table.getSelectionModel().getSelectedItem();
        BlogService service=new BlogService();
       // service.delete(b.getId());

      service.delete(b);
        showblog();

    }

    @FXML
    void update(ActionEvent event) {
     Blog b =table.getSelectionModel().getSelectedItem();
        BlogService blogService = new BlogService();
        b.setTitre(titre.getText());
        b.setContent(description.getText());
        b.setImageb(imageb.getText());
        blogService.update(b);
         showblog();



    }


    @FXML
    void initialize(){

    }
    @FXML
    void showDetails(ActionEvent event) {
        Blog selectedBlog = table.getSelectionModel().getSelectedItem();

        if (selectedBlog != null) {
            // Afficher le contenu du blog dans une boîte de dialogue
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Détails du Blog");
            alert.setHeaderText(selectedBlog.getTitre());
            alert.setContentText(selectedBlog.getContent());

            alert.showAndWait();
        } else {
            // Aucun blog sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun blog sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un blog pour afficher les détails.");
            alert.showAndWait();
        }
    }

    public void addphoto(ActionEvent actionEvent) {
    }
}
