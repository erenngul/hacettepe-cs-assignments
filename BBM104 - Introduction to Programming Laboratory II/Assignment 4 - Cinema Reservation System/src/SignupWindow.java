import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;

/**
 * Signup window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class SignupWindow extends Application {

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField passwordField2 = new PasswordField();
    private VBox vBox = new VBox();

    /**
     * Creates signup window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getSignupWindow(Stage primaryStage) {
        VBox labelVBox = new VBox();
        Label signupMessage1 = new Label("Welcome to the HUCS Cinema Reservation System!");
        Label signupMessage2 = new Label("Fill the form below to create a new account.");
        Label signupMessage3 = new Label("You can go to Log In page by clicking LOG IN Button.");
        labelVBox.getChildren().addAll(signupMessage1, signupMessage2, signupMessage3);
        labelVBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        Label username = new Label("Username:");
        Label password = new Label("Password:");
        Label password2 = new Label("Password:");
        Button signupButton = new Button("SIGN UP");
        Button loginButton = new Button("LOG IN");
        gridPane.addRow(0, username, usernameField);
        gridPane.addRow(1, password, passwordField);
        gridPane.addRow(2, password2, passwordField2);
        gridPane.add(signupButton, 1, 3);
        gridPane.add(loginButton, 0, 3);
        GridPane.setHalignment(signupButton, HPos.RIGHT);
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);

        Label errorLabel = new Label("");

        vBox.getChildren().addAll(labelVBox, gridPane, errorLabel);
        vBox.setAlignment(Pos.CENTER);
        VBox.setMargin(gridPane, new Insets(15, 0, 8, 0));

        loginButton.setOnAction(e -> openLoginWindow(primaryStage));
        signupButton.setOnAction(e -> createAccount());

        return vBox;
    }

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(getSignupWindow(primaryStage), 390, 240);

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * This method takes the username input and password input twice (hashed later) and checks if inputs are suitable to
     * be able to sign up for application. If credential already exists in backup.dat file, user has to try different
     * inputs to sign up. If the inputs are unique the user can successfully sign up.
     */
    public void createAccount() {
        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();
        String passwordInput2 = passwordField2.getText();
        String hashedPassword = hashPassword(passwordInput);
        HashMap<String, User> userHashMap = BackupFile.getUserHashMap();

        Media errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
        MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);
        if (userHashMap.containsKey(usernameInput)) {
            Label errorMessage1 = new Label("ERROR: This username already exists!");
            vBox.getChildren().remove(2);
            vBox.getChildren().add(errorMessage1);
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
        }
        else if (usernameInput.equals("")) {
            Label errorMessage2 = new Label("ERROR: Username cannot be empty!");
            vBox.getChildren().remove(2);
            vBox.getChildren().add(errorMessage2);
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
        }
        else if (passwordInput.equals("") && passwordInput2.equals("")) {
            Label errorMessage3 = new Label("ERROR: Password cannot be empty!");
            vBox.getChildren().remove(2);
            vBox.getChildren().add(errorMessage3);
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
        }
        else if (!(passwordInput.equals(passwordInput2))) {
            Label errorMessage4 = new Label("ERROR: Passwords do not match!");
            vBox.getChildren().remove(2);
            vBox.getChildren().add(errorMessage4);
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
        }
        else {
            Label successMessage = new Label("SUCCESS: You have successfully registered with your new credentials!");
            vBox.getChildren().remove(2);
            vBox.getChildren().add(successMessage);

            userHashMap.put(usernameInput, new User(usernameInput, hashedPassword, false, false));
        }
    }

    /**
     * Opens login window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openLoginWindow(Stage primaryStage) {
        LoginWindow loginWindow = new LoginWindow();
        loginWindow.start(primaryStage);
    }

    /**
     * Returns Base64 encoded version of MD5 hashed version of the given password.
     *
     * @param password Password to be hashed.
     * @return Base64 encoded version of MD5 hashed version of password.
     */
    private static String hashPassword(String password) {
        byte[] bytesOfPassword = password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest = new byte[0];
        try {
            md5Digest = MessageDigest.getInstance("MD5").digest(bytesOfPassword);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

}
