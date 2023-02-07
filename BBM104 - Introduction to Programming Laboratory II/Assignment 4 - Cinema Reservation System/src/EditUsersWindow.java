import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * Edit users window of HUCS Cinema Reservation System. This class extends Application to use the start method.
 */
public class EditUsersWindow extends Application {

    private HashMap<String, User> userHashMapWithoutLoggedInUser = LoginWindow.getUserHashMapWithoutLoggedInUser();
    private TableView<User> tableView = new TableView<>();
    private String[] userArray = userHashMapWithoutLoggedInUser.keySet().toArray(new String[0]);
    private User selectedUser;

    /**
     * Creates edit users window using various panes, labels and buttons.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     * @return An organized VBox to satisfy the graphical design.
     */
    public VBox getEditUsersWindow(Stage primaryStage) {
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        TableColumn<User, String> clubMemberColumn = new TableColumn<>("Club Member");
        clubMemberColumn.setCellValueFactory(new PropertyValueFactory<User, String>("clubMember"));
        TableColumn<User, String> adminColumn = new TableColumn<>("Admin");
        adminColumn.setCellValueFactory(new PropertyValueFactory<User, String>("admin"));

        tableView.getColumns().add(usernameColumn);
        tableView.getColumns().add(clubMemberColumn);
        tableView.getColumns().add(adminColumn);

        ObservableList<String> userList = FXCollections.observableArrayList(userArray);

        for (String username : userList)
            tableView.getItems().add(userHashMapWithoutLoggedInUser.get(username));

        tableView.getSelectionModel().selectFirst();
        selectedUser = tableView.getSelectionModel().getSelectedItem();

        tableView.setOnMouseClicked(e -> selectedUser = tableView.getSelectionModel().getSelectedItem());
        tableView.setPlaceholder(new Label("No user available in the database!"));

        HBox buttonHBox = new HBox();
        Button backButton = new Button("⯇ Back");
        backButton.setOnAction(e -> openWelcomeWindow(primaryStage));
        Button promoteDemoteClubMemberButton = new Button("Promote/Demote Club Member");
        promoteDemoteClubMemberButton.setOnAction(e -> promoteDemoteClubMember());
        Button promoteDemoteAdminButton = new Button("Promote/Demote Admin");
        promoteDemoteAdminButton.setOnAction(e -> promoteDemoteAdmin());
        buttonHBox.getChildren().addAll(backButton, promoteDemoteClubMemberButton, promoteDemoteAdminButton);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(8);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(tableView, buttonHBox);
        vBox.setSpacing(8);
        vBox.setPadding(new Insets(15, 15, 15, 15));

        return vBox;
    }

    /**
     * This method promotes to or demotes from club member for selected user. The tableView is updated afterwards.
     */
    public void promoteDemoteClubMember() {
        try {
            selectedUser.setClubMember(!selectedUser.isClubMember());
            tableView.refresh();
        } catch (Exception ignored) {}
    }

    /**
     * This method promotes to or demotes from admin for selected user. The tableView is updated afterwards.
     */
    public void promoteDemoteAdmin() {
        try {
            selectedUser.setAdmin(!selectedUser.isAdmin());
            tableView.refresh();
        } catch (Exception ignored) {}
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
        Scene scene = new Scene(getEditUsersWindow(primaryStage));

        primaryStage.setTitle(PropertiesFile.getPropertiesTable().getProperty("title"));
        primaryStage.getIcons().add(new Image("assets\\icons\\logo.png"));
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> BackupFile.updateBackupFile());
    }

    /**
     * Opens welcome window.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     */
    public void openWelcomeWindow(Stage primaryStage) {
        WelcomeWindow welcomeWindow = new WelcomeWindow();
        welcomeWindow.start(primaryStage);
    }

}
