module com.backinfile.cardRouge {
    requires com.almasb.fxgl.all;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires kotlin.stdlib;
    requires org.slf4j;
    requires java.desktop;
    requires kotlinx.coroutines.core.jvm;
    requires kotlinx.coroutines.jdk8;
    requires org.reflections;

    opens com.backinfile.cardRouge to javafx.fxml;
    exports com.backinfile.cardRouge;
    exports com.backinfile.cardRouge.cardView;
}