package out.production.dsa_automator;

import database.Database;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthenticationController implements Initializable {
    @FXML
    private AnchorPane layer1;
    @FXML
    private AnchorPane layer2;
    @FXML
    private Button buttonSignIn;
    @FXML
    private Button slideSignUp;
    @FXML
    private TextField signInHandle;
    @FXML
    private Label signInMsg;
    @FXML
    private TextField signInPassword;

    @FXML
    private Button buttonSignUp;
    @FXML
    private Button slideSignIn;
    @FXML
    private TextField signUpEmail;
    @FXML
    private TextField signUpHandle;
    @FXML
    private Label signUpMsg;
    @FXML
    private TextField signUpPassword;

    public boolean initSignIn = true;
    public boolean isSignInActive;
    public boolean isSignUpActive;

    @FXML
    private Label authenticationMsg;

    public void showAuthUi(boolean isSignInPressed) {
        if (isSignInPressed) {
            buttonSignIn.setVisible(true);
            slideSignUp.setVisible(true);
            signInHandle.setVisible(true);
            signInMsg.setVisible(true);
            signInPassword.setVisible(true);

            slideSignIn.setVisible(false);
            buttonSignUp.setVisible(false);
            signUpEmail.setVisible(false);
            signUpHandle.setVisible(false);
            signUpMsg.setVisible(false);
            signUpPassword.setVisible(false);
        }
        else {
            buttonSignIn.setVisible(false);
            slideSignUp.setVisible(false);
            signInHandle.setVisible(false);
            signInMsg.setVisible(false);
            signInPassword.setVisible(false);

            slideSignIn.setVisible(true);
            buttonSignUp.setVisible(true);
            signUpEmail.setVisible(true);
            signUpHandle.setVisible(true);
            signUpMsg.setVisible(true);
            signUpPassword.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        slideSignUp.setOnAction(e -> slideToSignUp());
        slideSignIn.setOnAction(e -> slideToSignIn());
        buttonSignIn.setOnAction(e -> {
            try {
                verifyLogIn();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        buttonSignUp.setOnAction(e -> {
            try {
                insertInfo();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void slideToSignUp() {
        isSignUpActive = true;
        isSignInActive = false;
        authenticationMsg.setVisible(false);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.3));

        slide.setNode(layer2);
        if (initSignIn) slide.setToX(600);
        else slide.setToX(0);
        slide.play();

        if (initSignIn) layer1.setTranslateX(-280);
        else layer1.setTranslateX(0);
        slide.setOnFinished(e -> { });

        showAuthUi(false);
    }
    public void slideToSignIn() {
        isSignInActive = true;
        isSignUpActive = false;
        authenticationMsg.setVisible(false);

        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.3));

        slide.setNode(layer2);
        if (initSignIn) slide.setToX(0);
        else slide.setToX(600);
        slide.play();

        if (initSignIn) layer1.setTranslateX(0);
        else layer1.setTranslateX(-280);

        slide.setOnFinished(e -> { });
        showAuthUi(true);
    }

    public void verifyLogIn() throws SQLException, ClassNotFoundException {
        Database database = new Database("dsa_automator", "root", "");

        String[] column = {"Handle", "Password"};
        Object[] values = {signInHandle.getText()};
        ResultSet rs = database.select("user_table", column, "Handle = ?", values);

        if (!rs.next()) {
            authenticationMsg.setText("Handle doesn't exist");
            authenticationMsg.setTextFill(Color.RED);
            authenticationMsg.setVisible(true);
        }
        else {
            boolean matched = rs.getString("Password").equals(signInPassword.getText());
            if (!matched) {
                authenticationMsg.setText("Wrong password");
                authenticationMsg.setTextFill(Color.RED);
            }
            authenticationMsg.setVisible(true);
        }
    }

    public void insertInfo() throws SQLException, ClassNotFoundException {
        Database database = new Database("dsa_automator", "root", "");
        Object[] values = {signUpEmail.getText(), signUpHandle.getText(), signUpPassword.getText()};
        database.insert("user_table", values);

        authenticationMsg.setText("You're registered!");
        authenticationMsg.setTextFill(Color.LIGHTGREEN);
        authenticationMsg.setVisible(true);
    }
}
