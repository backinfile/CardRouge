module CardRouge.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires kotlin.stdlib;
    requires com.almasb.fxgl.all;
    requires slf4j.api;
    requires org.apache.logging.log4j;

    opens com.backinfile.cardRouge to javafx.fxml;
    exports com.backinfile.cardRouge;
}