package tn.esprit.controler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import tn.esprit.interfaces.MyListener;
import tn.esprit.models.Blog;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class Cardblogs {
    @FXML
    private ResourceBundle resources;

    @FXML
    private Label datelabel;
    @FXML
    private URL location;
    @FXML
    private ImageView imageFrame;

    @FXML
    private Label titrelabel;


    private MyListener myListener;

    private Blog blog;

    public void setData(Blog blog, MyListener myListener) {
        this.blog = blog;
        this.myListener = myListener;
       // datelabel.setText(blog.getDate());
        titrelabel.setText(blog.getTitre());

        File imageFile = new File(blog.getImageb()); // Assurez-vous d'avoir importé java.io.File
        if (imageFile.exists()) {
            Image image = new Image(imageFile.toURI().toString());
            imageFrame.setImage(image);
        } else {
            System.out.println("Fichier image introuvable: " + blog.getImageb());
            // Vous pouvez gérer cette situation comme vous le souhaitez, par exemple, en définissant une image par défaut.
        }
    }

    @FXML
    void click(MouseEvent event) {
        myListener.onClickListener(blog);
    }

}
