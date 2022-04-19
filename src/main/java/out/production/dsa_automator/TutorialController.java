package out.production.dsa_automator;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;


public class TutorialController implements Initializable {
    @FXML
    private Label tutorial1;
    @FXML
    private Label tutorial2;
    @FXML
    private TextArea tutorial;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //tutorial1.setVisible(true);
    }
}
