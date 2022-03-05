module com.code.dsa.dsa_automator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens out.production.dsa_automator to javafx.fxml;
    exports out.production.dsa_automator;
}