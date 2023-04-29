open module com.backinfile.cardRouge {
    requires com.almasb.fxgl.all;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    requires kotlin.stdlib;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires org.slf4j;
    requires java.desktop;
    requires org.reflections;
    requires kotlinx.coroutines.core.jvm;

    exports com.backinfile.cardRouge;
    exports com.backinfile.cardRouge.cardView;
}