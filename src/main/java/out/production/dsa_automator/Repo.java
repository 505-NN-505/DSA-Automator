package out.production.dsa_automator;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Repo implements Initializable {
    Integer ID;
    String handle;
    String title;
    String category;
    String viewLink;
    Button button;

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getViewLink() {
        return viewLink;
    }

    public void setViewLink(String viewLink) {
        this.viewLink = viewLink;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(String viewLink) {
        this.button = button;
    }


    public Repo(Integer ID, String handle, String title, String category, String viewLink) {
        setID(ID);
        setHandle((handle));
        setTitle(title);
        setCategory(category);
        setViewLink(viewLink);
        this.button = new Button("Details");
        this.button.setOnAction(eventSS -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SSLoader.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load());

                SSLoaderController controller = fxmlLoader.getController();

                FileInputStream inputstream = new FileInputStream(viewLink);
                Image image = new Image(inputstream);
                controller.ssTask.setImage(image);

                Stage stage = new Stage();
                stage.setTitle(title + "_" + handle);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
