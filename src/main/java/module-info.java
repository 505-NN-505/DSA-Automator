module com.code.dsa.dsa_automator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;


    opens out.production.dsa_automator to javafx.fxml;
    exports out.production.dsa_automator;
}