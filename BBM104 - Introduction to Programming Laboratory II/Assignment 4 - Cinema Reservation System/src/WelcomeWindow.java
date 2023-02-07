import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Welcome window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class WelcomeWindow extends Application {

    private User loggedInUser = LoginWindow.getLoggedInUser();
    private String[] filmArray = BackupFile.getFilmHashMap().keySet().toArray(new String[0]);
    private ComboBox<String> comboBox = new ComboBox<>();
    private Label filmSuggestionLabel = new Label(" N/A ");
    private String previousFilmName;
    private static Film selectedFilm;

    /**
     * Creates welcome window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getWelcomeWindow(Stage primaryStage) {
        VBox labelVBox = new VBox();
        Label welcomeMessage1 = new Label();
        if (loggedInUser.isAdmin())
            welcomeMessage1.setText(String.format("Welcome %s (%s)!", loggedInUser.getUsername(), loggedInUser.isClubMember() ? "Admin - Club Member" : "Admin"));
        else
            welcomeMessage1.setText(String.format("Welcome %s%s!", loggedInUser.getUsername(), loggedInUser.isClubMember() ? " (Club Member)" : ""));
        Label welcomeMessage2 = new Label("Select a film and then click OK to continue.");
        Label welcomeMessage3 = new Label("You can click Suggest Film to get a film suggestion.");

        VBox suggestionVBox = new VBox();
        Button suggestFilmButton = new Button("Suggest Film");
        filmSuggestionLabel.setStyle("-fx-background-color: white");
        filmSuggestionLabel.setStyle("-fx-border-color: black");
        suggestFilmButton.setOnAction(e -> suggestRandomFilm());
        suggestionVBox.getChildren().addAll(suggestFilmButton, filmSuggestionLabel);
        suggestionVBox.setAlignment(Pos.CENTER);
        suggestionVBox.setSpacing(6);
        VBox.setMargin(suggestFilmButton, new Insets(4, 0, 0, 0));

        labelVBox.getChildren().addAll(welcomeMessage1, welcomeMessage2, welcomeMessage3);
        labelVBox.setAlignment(Pos.CENTER);

        HBox filmHBox = new HBox();
        ObservableList<String> filmList = FXCollections.observableArrayList(filmArray);
        comboBox.getItems().addAll(filmList);
        if (filmArray.length != 0)
            comboBox.setValue(filmArray[0]);
        Button okButton = new Button("OK");
        filmHBox.getChildren().addAll(comboBox, okButton);
        filmHBox.setAlignment(Pos.CENTER);
        filmHBox.setSpacing(8);

        HBox adminHBox = new HBox();
        Button addFilmButton = new Button("Add Film");
        Button removeFilmButton = new Button("Remove film");
        Button editUsersButton = new Button("Edit Users");
        adminHBox.getChildren().addAll(addFilmButton, removeFilmButton, editUsersButton);
        adminHBox.setSpacing(8);
        adminHBox.setAlignment(Pos.CENTER);

        HBox logoutHBox = new HBox();
        Button logoutButton = new Button("LOG OUT");
        logoutHBox.getChildren().add(logoutButton);
        logoutHBox.setAlignment(Pos.CENTER_RIGHT);

        VBox vBox = new VBox();
        if (loggedInUser.isAdmin()) {
            vBox.getChildren().addAll(labelVBox, suggestionVBox, filmHBox, adminHBox, logoutHBox);
            vBox.setSpacing(8);
        }
        else {
            vBox.getChildren().addAll(labelVBox, suggestionVBox, filmHBox, logoutHBox);
            VBox.setMargin(filmHBox, new Insets(8, 0, 15, 0));
        }
        vBox.setPadding(new Insets(15, 15, 15, 15));
        vBox.setAlignment(Pos.CENTER);

        okButton.setOnAction(e -> openFilmWindow(primaryStage));
        logoutButton.setOnAction(e -> openLoginWindow(primaryStage));
        addFilmButton.setOnAction(e -> openAddFilmWindow(primaryStage));
        removeFilmButton.setOnAction(e -> openRemoveFilmWindow(primaryStage));
        editUsersButton.setOnAction(e -> openEditUsersWindow(primaryStage));

        return vBox;
    }

    /**
     * This method is for the extra feature. It is used to suggest a random film from the existing films if the user can
     * not know what to watch. When it is invoked, a random film is suggested to the user. It makes sure that the
     * previous film is not suggested. It will give an error if there are not any films.
     */
    public void suggestRandomFilm() {
        if (filmArray.length > 1) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, filmArray.length);
            if (filmArray[randomIndex].equals(previousFilmName))
                suggestRandomFilm();
            else {
                String randomFilmName = filmArray[randomIndex];
                filmSuggestionLabel.setText(" " + randomFilmName + " ");
                previousFilmName = randomFilmName;
            }
        }
        else if (filmArray.length == 1)
            filmSuggestionLabel.setText(" " + filmArray[0] + " ");
        else {
            Media errorSound = new Media(new File("assets\\effects\\error.mp3").toURI().toString());
            MediaPlayer errorSoundPlayer = new MediaPlayer(errorSound);
            errorSoundPlayer.seek(Duration.ZERO);
            errorSoundPlayer.play();
            filmSuggestionLabel.setText(" ERROR: No films to suggest! ");
        }
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
        Scene scene = new Scene(getWelcomeWindow(primaryStage));

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * Opens film window of the selected film.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openFilmWindow(Stage primaryStage) {
        try {
            selectedFilm = BackupFile.getFilmHashMap().get(comboBox.getValue());
            FilmWindow filmWindow = new FilmWindow();
            filmWindow.start(primaryStage);
        } catch (Exception ignored) {}
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
     * Opens add film window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openAddFilmWindow(Stage primaryStage) {
        AddFilmWindow addFilmWindow = new AddFilmWindow();
        addFilmWindow.start(primaryStage);
    }

    /**
     * Opens remove film window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openRemoveFilmWindow(Stage primaryStage) {
        RemoveFilmWindow removeFilmWindow = new RemoveFilmWindow();
        removeFilmWindow.start(primaryStage);
    }

    /**
     * Opens edit users window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openEditUsersWindow(Stage primaryStage) {
        EditUsersWindow editUsersWindow = new EditUsersWindow();
        editUsersWindow.start(primaryStage);
    }

    /**
     * @return selected film in ComboBox.
     */
    public static Film getSelectedFilm() {
        return selectedFilm;
    }

}
