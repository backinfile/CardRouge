module com.backinfile.cardRouge {
    requires com.almasb.fxgl.all;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires kotlin.stdlib;
//    requires slf4j.api;
    requires org.apache.logging.log4j;
    requires java.desktop;
    requires fastjson;

    opens com.backinfile.cardRouge to javafx.fxml;
    exports com.backinfile.cardRouge;
    exports com.backinfile.cardRouge.cardView;
}