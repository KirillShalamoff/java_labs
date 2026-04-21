module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
    exports org.openjfx.Shapes;
    opens org.openjfx.Shapes to javafx.fxml;
}
