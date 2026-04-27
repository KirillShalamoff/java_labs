module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    
    opens org.openjfx to javafx.fxml;
    exports org.openjfx;

    // ВОТ ЭТОЙ СТРОЧКИ НЕ ХВАТАЛО:
    // Разрешаем FXMLLoader видеть твой контроллер
    opens org.openjfx.Controller to javafx.fxml;

    exports org.openjfx.Shapes;
    opens org.openjfx.Shapes to javafx.fxml;
}
