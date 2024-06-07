package hangman;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicBoolean;

public class MenuController {
    private DatabaseManager db;
    private User currentUser;
    // Menu Parts
    @FXML
    public VBox menuArea;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label nameLabel;
    // Login Parts
    @FXML
    public VBox loginArea;
    @FXML
    public HBox nameInputBox;
    @FXML
    public TextField usernameInput;
    @FXML
    public TextField nameInput;
    @FXML
    public TextField passwordInput;
    @FXML
    public Label toastText;
    @FXML
    public Button sendDataBtn;
    @FXML
    public void initialize(){
        // Hide The Name Input Box To First Determine If The user Has been already registered or not.
        nameInputBox.setVisible(false);
        nameInputBox.setManaged(false);

        // Get The Database
        db = DatabaseManager.getDb();

        // Init The Toast Message
        toastText.setOpacity(0);

        // Hide The Menu At first
        menuArea.setVisible(false);
        menuArea.setManaged(false);
    }

    public void showToast(String text, Color textColor){
        toastText.setText(text);
        toastText.setTextFill(textColor);

        Timeline fadeInTimeline = new Timeline();
        KeyFrame fadeInKey1 = new KeyFrame(Duration.millis(500), new KeyValue(toastText.opacityProperty(), 1));
        fadeInTimeline.getKeyFrames().add(fadeInKey1);
        fadeInTimeline.setOnFinished((ae) ->
        {
            new Thread(() -> {
                try {
                    Thread.sleep(3500);
                }
                catch (InterruptedException ignored) {}

                Timeline fadeOutTimeline = new Timeline();
                KeyFrame fadeOutKey1 = new KeyFrame(Duration.millis(500), new KeyValue (toastText.opacityProperty(), 0));
                fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                fadeOutTimeline.setOnFinished((aeb) -> toastText.setOpacity(0));
                fadeOutTimeline.play();
            }).start();
        });
        fadeInTimeline.play();
    }
    @FXML
    public void searchUsername(ActionEvent actionEvent) {
        doesUsernameExists(usernameInput.getText());
    }

    private boolean doesUsernameExists(String username){
        if(db.getUser(username) == null ){
            sendDataBtn.setText("Sing Up");
            nameInputBox.setVisible(true);
            nameInputBox.setManaged(true);
            return false;
        } else {
            sendDataBtn.setText("Sing in");
            nameInputBox.setVisible(false);
            nameInputBox.setManaged(false);
            return true;
        }
    }

    @FXML
    public void sendUserData(ActionEvent actionEvent) {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String name = nameInput.getText();

        User user = db.getUser(username);

        if (doesUsernameExists(username)) {
            // Sign in
            if (user.getPassword().equals(password)) {
                currentUser = user;
                showToast("Successfully Signed in! Welcome Back!", Color.GREEN);
                switchToMenu();
            } else {
                showToast("Wrong Password!", Color.RED);
            }
        } else {
            // Sign up
            if (!name.isEmpty() && !password.isEmpty() && !username.isEmpty()) {

                if (showConfirmationBox()) {
                    // Register The New User.
                    User newUser = new User(username, name, password);
                    if (db.registerUser(name, username, password)) {
                        currentUser = newUser;
                        showToast("Successfully Signed up! Welcome!", Color.GREEN);
                        switchToMenu();
                    } else showToast("Failed to Sign up! Please Try again later.", Color.RED);

                // Doesn't work! (requestFocus)
                } else nameInputBox.requestFocus();
            } else showToast("Please fill the inputs.", Color.YELLOWGREEN);

        }
    }
    private boolean showConfirmationBox() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Confirmation Needed");
        alert.setContentText("Are you sure you want to Create a new Account?");

        // Block input to other windows
        alert.initModality(Modality.APPLICATION_MODAL);

        // Show the dialog and wait for the user to respond
        AtomicBoolean result = new AtomicBoolean(false);
        alert.showAndWait().ifPresent(response -> {
            result.set(response == ButtonType.OK);
        });

        return result.getPlain();
    }

    private void switchToMenu(){
        // Clean up Login/Register Page And Show the main menu.
        loginArea.setVisible(false);
        loginArea.setManaged(false);
        menuArea.setVisible(true);
        menuArea.setManaged(true);

        // Show The Current User info.
        usernameLabel.setText("Username: " + currentUser.getUsername());
        nameLabel.setText("Name: " + currentUser.getName());
    }

}
