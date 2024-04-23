package tn.esprit.controler;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import tn.esprit.models.Blog;

public class BlogListCell extends ListCell<Blog> {
    @Override
    protected void updateItem(Blog blog, boolean empty) {
        super.updateItem(blog, empty);
        if (empty || blog == null) {
            setText(null);
        } else {
            setText(blog.getTitre()); // Affichez le titre du blog dans la cellule
        }
    }
}
