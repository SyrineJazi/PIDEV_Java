module workshopReady {
    requires javafx.fxml;
    requires javafx.controls;

    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;

    requires AnimateFX;

    opens tn.esprit.app to javafx.graphics, javafx.fxml;

}