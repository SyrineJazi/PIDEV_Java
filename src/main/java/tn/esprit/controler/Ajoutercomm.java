package tn.esprit.controler;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import org.w3c.dom.Comment;
import tn.esprit.models.comm;
import tn.esprit.services.BlogService;
import tn.esprit.services.CommService;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class Ajoutercomm {


    private final CommService CommService;
    @FXML
    private Button ajoutcomm;
    @FXML
    private TableColumn<?, ?> contenu;
    @FXML
    private TableColumn<?, ?> id;

    @FXML
    private TableColumn<?, ?> idp;

    @FXML
    private Button modifiercomm;
    @FXML
    private ComboBox<?> combobox;

    @FXML
    private TextField comm;

    @FXML
    private Button deletecomm;

    @FXML
    private TableView<tn.esprit.models.comm> tablec;
    @FXML
    void choixblog(ActionEvent event) {
        comm selectedComment = (tn.esprit.models.comm) combobox.getValue();
        if (selectedComment != null) {
            comm.setText(selectedComment.getContenu());
        }
    }void choixComm(ActionEvent event) {
        comm selectedComment = (comm) combobox.getValue();
        if (selectedComment != null) {
            contenu.setText(selectedComment.getContenu());
        }
    }

    public Ajoutercomm() {
        CommService = new CommService();
    }
    public void updateComm(ActionEvent actionEvent) {
        CommService service = new CommService();
        int commentId = 16; // Remplacez 16 par l'ID du commentaire que vous souhaitez mettre à jour
        String newContenu = comm.getText(); // Obtenez le nouveau contenu à partir du TextField
        comm updatedComment = new comm(commentId, 0, newContenu, null); // Créez un nouvel objet comm avec les données mises à jour
        service.updateComment(updatedComment); // Appelez la méthode updateComment avec le commentaire mis à jour
        showcomm(); // Actualiser la liste des commentaires après la mise à jour
    }

    @FXML
    void modifiercomm(ActionEvent event) {
        comm selectedComment = (tn.esprit.models.comm) combobox.getValue();
        String newContent = comm.getText();
        if (selectedComment != null && !newContent.isEmpty()) {
            selectedComment.setContenu(newContent);
            CommService.updateComment(selectedComment);
            // Mise à jour réussie, vous pouvez afficher un message de succès si nécessaire
        } else {
            // Afficher un message d'erreur si aucun commentaire n'est sélectionné ou si le nouveau contenu est vide
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modification de commentaire");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un commentaire et fournir un nouveau contenu.");
            alert.showAndWait();
        }
    }

    @FXML
    void initialize() {
        showcomm();
    }


    public void ajoutcomm(ActionEvent actionEvent) {
        CommService service=new CommService();
        int blogId  =111;   String contenu = comm.getText();
        if (!contenu.isEmpty()) {
            comm newComment = new comm(0, blogId, contenu, null);
            CommService.addComment(newComment);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Champs obligatoires");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs obligatoires.");
 showcomm();
            // Afficher l'alerte
            alert.showAndWait();
          // service.updateComment(comm);
            // loadComments();
        }
    }

    public void deletecomm(ActionEvent actionEvent) {
        comm selectedComment = (tn.esprit.models.comm) combobox.getValue();
        if (selectedComment != null) {
            CommService.deleteComment(selectedComment.getId());
            // Suppression réussie, vous pouvez afficher un message de succès si nécessaire
        } else {
            // Afficher un message d'erreur si aucun commentaire n'est sélectionné
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression de commentaire");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un commentaire à supprimer.");
            alert.showAndWait();
        }
    }
    public void showcomm() {
        CommService commService = new CommService();
        ObservableList<comm> commentList = FXCollections.observableArrayList(commService.getAll());
        tablec.setItems(commentList);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        idp.setCellValueFactory(new PropertyValueFactory<>("blog_id"));
        contenu.setCellValueFactory(new PropertyValueFactory<>("contenu"));
        //colCreatedAt.setCellValueFactory(new PropertyValueFactory<>("created_at"));
    }
}