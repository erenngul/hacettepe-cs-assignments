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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Login window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class LoginWindow extends Application {

    private TextField usernameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private VBox vBox = new VBox();
    private int blockTime = Integer.parseInt(PropertiesFile.getPropertiesTable().getProperty("block-time"));
    private boolean banned = false;
    private static User loggedInUser;
    private static HashMap<String, User> userHashMapWithoutLoggedInUser;
    private static int maximumErrorWithoutGettingBlockedCount = Integer.parseInt(PropertiesFile.getPropertiesTable().getProperty("maximum-error-without-getting-blocked"));

    /**
     * Creates login window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getLoginWindow(Stage primaryStage) {
        VBox labelVBox = new VBox();
        Label loginMessage1 = new Label("Welcome to the HUCS Cinema Reservation System!");
        Label loginMessage2 = new Label("Please enter your credentials below and click LOGIN.");
        Label loginMessage3 = new Label("You can create a new account by clicking SIGN UP button.");
        labelVBox.getChildren().addAll(loginMessage1, loginMessage2, loginMessage3);
        labelVBox.setAlignment(Pos.CENTER);

        GridPane gridPane = new GridPane();
        Label username = new Label("Username:");
        Label password = new Label("Password:");
        Button signupButton = new Button("SIGN UP");
        Button loginButton = new Button("LOG IN");
        gridPane.addRow(0, username, usernameField);
        gridPane.addRow(1, password, passwordField);
        gridPane.add(signupButton, 0, 2);
        gridPane.add(loginButton, 1, 2);
        GridPane.setHalignment(loginButton, HPos.RIGHT);
        gridPane.setHgap(8);
        gridPane.setVgap(8);
        gridPane.setAlignment(Pos.CENTER);

        Label errorLabel = new Label("");

        vBox.getChildren().addAll(labelVBox, gridPane, errorLabel);
        vBox.setAlignment(Pos.CENTER);
        VBox.setMargin(gridPane, new Insets(15, 0, 8, 0));

        loginButton.setOnAction(e -> checkCredentials(primaryStage));
        signupButton.setOnAction(e -> openSignupWindow(primaryStage));

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
        Scene scene = new Scene(getLoginWindow(primaryStage),390, 200);

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * This method takes the username and password (hashed later) inputs and checks if inputs are suitable to be able
     * to log in application. If credentials exist in backup.dat, user may log in. If credentials do not exist, user has
     * to try again in order to login before they reach maximum error count without getting blocked. They are banned
     * for provided ban time afterwards if count is exceeded.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void checkCredentials(Stage primaryStage) {
        String usernameInput = usernameField.getText();
        String passwordInput = passwordField.getText();
        String hashedPassword = hashPassword(passwordInput);
        HashMap<String, User> userHashMap = BackupFile.getUserHashMap();

        if (userHashMap.containsKey(usernameInput) && userHashMap.get(usernameInput).getPassword().equals(hashedPassword)) {
            if (!banned) {
                loggedInUser = userHashMap.get(usernameInput);
                userHashMapWithoutLoggedInUser = new HashMap<>(userHashMap); // will be used later in EditUsersWindow class
                userHashMapWithoutLoggedInUser.remove(usernameInput);
                openWelcomeWindow(primaryStage);
            }
            else {
                Media errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
                MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);
                errorSoundPlayer.seek(Duration.ZERO);
                errorSoundPlayer.play();
                Label errorMessage3 = new Label("ERROR: Please wait until end of the " + blockTime + " seconds to make a new operation!");
                vBox.getChildren().remove(2);
                vBox.getChildren().add(errorMessage3);
            }
        }
        else {
            Media errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
            MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
            Label errorMessage1 = new Label("ERROR: There is no such a credential!");
            vBox.getChildren().remove(2);
            vBox.getChildren().add(errorMessage1);
            maximumErrorWithoutGettingBlockedCount--;
            if (maximumErrorWithoutGettingBlockedCount <= 0) {
                if (banned) {
                    Label errorMessage3 = new Label("ERROR: Please wait until end of the " + blockTime + " seconds to make a new operation!");
                    vBox.getChildren().remove(2);
                    vBox.getChildren().add(errorMessage3);
                }
                else {
                    Label errorMessage2 = new Label("ERROR: Please wait for " + blockTime + " seconds to make a new operation!");
                    vBox.getChildren().remove(2);
                    vBox.getChildren().add(errorMessage2);
                    banned = true;
                    setTimer();
                }
            }
        }

    }

    /**
     * The timer method for banning users if they exceed the maximum error count. After the ban time their ban is lifted
     * and count is reset.
     */
    public void setTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                banned = false;
                maximumErrorWithoutGettingBlockedCount = 5;
            }
        }, (long) blockTime*1000);
    }

    /**
     * Opens welcome window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openWelcomeWindow(Stage primaryStage) {
        maximumErrorWithoutGettingBlockedCount = 5;
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        welcomeWindow.start(primaryStage);
    }

    /**
     * Opens signup window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openSignupWindow(Stage primaryStage) {
        SignupWindow signupWindow = new SignupWindow();
        signupWindow.start(primaryStage);
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

    /**
     * @return logged-in user whose credentials are correct.
     */
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    /**
     * @return user HashMap without logged-in user.
     */
    public static HashMap<String, User> getUserHashMapWithoutLoggedInUser() {
        return userHashMapWithoutLoggedInUser;
    }

}
