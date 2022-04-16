package out.production.dsa_automator;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class Repo implements Initializable {
    Integer ID;
    String handle;
    String title;
    String category;
    String viewLink;

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

    public Repo(Integer ID, String handle, String title, String category, String viewLink) {
        setID(ID);
        setHandle((handle));
        setTitle(title);
        setCategory(category);
        setViewLink(viewLink);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
