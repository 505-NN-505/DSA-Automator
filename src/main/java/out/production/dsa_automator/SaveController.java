package out.production.dsa_automator;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

public class SaveController implements Initializable {
    @FXML
    public Button buttonSave;
    @FXML
    public ComboBox category;
    @FXML
    public TextField taskTitle;
    @FXML
    public TextField referenceLink;
    @FXML
    public HBox tagList;
    @FXML
    public AnchorPane savePane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("data structures", "dp",
                "graphs", "greedy", "trees");

        category.setItems(list);
    }

    TreeMap<String, Integer> markedCategory = new TreeMap<String, Integer>();
    ArrayList<String> selectedTag = new ArrayList<String>();

    @FXML
    void selectTag(ActionEvent event) throws SQLException, ClassNotFoundException {
        String s = category.getSelectionModel().getSelectedItem().toString();
        if (!markedCategory.containsKey(s)) {
            markedCategory.put(s, 1);
            Label label = new Label();
            label.setTextFill(Color.WHITE);
            label.setStyle(String.format("-fx-background-color: %s;", "#1c1c1c"));
            label.setText(s);
            tagList.getChildren().add(label);
            selectedTag.add(s);
        }
    }

    void populateCatTable(String handle, String taskName, String categoryName) throws SQLException, ClassNotFoundException {
        if (categoryName == "data structures") {
            int taskID = getTheLastTaskID(taskName);
            insertToCategoryTable("data_structures_table", taskID, handle);
        }
        else if (categoryName == "dp") {
            int taskID = getTheLastTaskID(taskName);
            insertToCategoryTable("dp_table", taskID, handle);
        }
        else if (categoryName == "graphs") {
            int taskID = getTheLastTaskID(taskName);
            insertToCategoryTable("graphs_table", taskID, handle);
        }
        else if (categoryName == "greedy") {
            int taskID = getTheLastTaskID(taskName);
            insertToCategoryTable("greedy_table", taskID, handle);
        }
        else if (categoryName == "trees") {
            int taskID = getTheLastTaskID(taskName);
            insertToCategoryTable("trees_table", taskID, handle);
        }
    }

    String getTaskTitle() {
        return taskTitle.getText();
    }
    String getReferenceLink() { return referenceLink.getText(); }

    public void insertToTaskTable(String handle, String taskTitle, String address) throws SQLException, ClassNotFoundException {
        Database database = new Database("dsa_automator", "root", "");
        String[] column = {"user_handle", "title", "source_link"};
        Object[] values = {handle, taskTitle, address};
        database.insert("task_table", column, values);
    }

    public void insertToCategoryTable(String tableName, int task_ID, String handle) throws SQLException, ClassNotFoundException {
        Database database = new Database("dsa_automator", "root", "");
        String[] column = {"task_id", "user_handle"};
        Object[] values = {task_ID, handle};
        database.insert(tableName, column, values);
    }

    public int getTheLastTaskID(String taskName) throws SQLException, ClassNotFoundException {
        Database database = new Database("dsa_automator", "root", "");

        String[] column = {"ID", "title"};
        Object[] values = {taskName};
        ResultSet rs = database.select("task_table", column, "title = ?", values);

        rs.next();

        int taskID = rs.getInt("ID");
        return taskID;
    }
}
