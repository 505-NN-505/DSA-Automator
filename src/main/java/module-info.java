module com.code.dsa.dsa_automator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.code.dsa.dsa_automator to javafx.fxml;
    exports com.code.dsa.dsa_automator;
}