package out.production.dsa_automator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ListController {
    @FXML
    private HBox box;
    @FXML
    private Label valueList;

    public void setData(Integer value) {
        valueList.setText(value.toString());
    }
}
