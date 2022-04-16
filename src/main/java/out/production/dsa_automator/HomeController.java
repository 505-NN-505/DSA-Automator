package out.production.dsa_automator;

import database.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    private TableView<Repo> RepoTable;

    @FXML
    private ScrollPane RepoTablePane;

    @FXML
    private Button buttonDashboard;

    @FXML
    private Button buttonMyWorks;

    @FXML
    private Button buttonRepository;

    @FXML
    private Button buttonSignOut;

    @FXML
    private Pane paneHome;

    @FXML
    private TableColumn<Repo, String> tableColumnCategory;

    @FXML
    private TableColumn<Repo, String> tableColumnHandle;

    @FXML
    private TableColumn<Repo, Integer> tableColumnID;

    @FXML
    private TableColumn<Repo, String> tableColumnTitle;

    @FXML
    private TableColumn<Repo, String> tableColumnView;

    ObservableList<Repo> repoList = FXCollections.observableArrayList();

    Database database;
    ResultSet rs = null;
    PreparedStatement ps = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            database = new Database("dsa_automator", "root", "");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        tableColumnID.setCellValueFactory(new PropertyValueFactory<Repo, Integer>("ID"));
        tableColumnHandle.setCellValueFactory(new PropertyValueFactory<Repo, String>("Handle"));
        tableColumnTitle.setCellValueFactory(new PropertyValueFactory<Repo, String>("Title"));
        tableColumnCategory.setCellValueFactory(new PropertyValueFactory<Repo, String>("Category"));
        tableColumnView.setCellValueFactory(new PropertyValueFactory<Repo, String>("viewLink"));

        try {
            getRepoInfo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void getRepoInfo() throws SQLException, ClassNotFoundException {
        repoList.clear();
        try {
            String query = "SELECT task_table.ID, task_table.user_handle, task_table.title, data_structures_table.category, dp_table.category, graphs_table.category, greedy_table.category, trees_table.category, task_table.source_link FROM task_table LEFT JOIN data_structures_table ON task_table.ID = data_structures_table.task_id LEFT JOIN dp_table ON task_table.ID = dp_table.task_id LEFT JOIN graphs_table ON task_table.ID = graphs_table.task_id LEFT JOIN greedy_table ON task_table.ID = greedy_table.task_id LEFT JOIN trees_table ON task_table.ID = trees_table.task_id;";
            PreparedStatement ps = database.connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Integer repoID = rs.getInt(1);
                String handle = rs.getString(2);
                String title = rs.getString(3);
                String data_structures_cat = rs.getString(4);
                String dp_cat = rs.getString(5);
                String graphs_cat = rs.getString(6);
                String greedy_cat = rs.getString(7);
                String trees_cat = rs.getString(8);
                String view = rs.getString(9);

                String catergory = "";

                if (data_structures_cat != null) {
                    if (catergory.isEmpty()) catergory += data_structures_cat;
                    else catergory += ", " + data_structures_cat;
                }
                if (dp_cat != null) {
                    if (catergory.isEmpty()) catergory += dp_cat;
                    else catergory += ", " + dp_cat;
                }
                if (graphs_cat != null) {
                    if (catergory.isEmpty()) catergory += graphs_cat;
                    else catergory += ", " + graphs_cat;
                }
                if (greedy_cat != null) {
                    if (catergory.isEmpty()) catergory += greedy_cat;
                    else catergory += ", " + greedy_cat;
                }
                if (trees_cat != null) {
                    if (catergory.isEmpty()) catergory += trees_cat;
                    else catergory += ", " + trees_cat;
                }

                repoList.add(new Repo(repoID, handle, title, catergory, view));
                RepoTable.setItems(repoList);
            }
        }
        catch (Exception e) {

        }
    }
}
