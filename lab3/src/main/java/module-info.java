module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires jdk.xml.dom;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;


    opens org.openjfx.Controller to javafx.fxml;

    exports org.openjfx.Shapes;
    opens org.openjfx.Shapes to javafx.fxml;
    opens org.openjfx.Managers to javafx.fxml;
}
