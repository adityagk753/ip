package cody.gui;

import cody.Cody;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Cody cody;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/UserImage.png"));
    private Image codyImage = new Image(this.getClass().getResourceAsStream("/images/CodyImage.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Cody instance */
    public void setCody(Cody c) {
        cody = c;
    }

    public void setWelcomeMessage() {
        dialogContainer.getChildren().add(DialogBox.getCodyDialog(cody.getWelcomeMessage(), codyImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Cody's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = cody.handleCommand(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getCodyDialog(response, codyImage));
        userInput.clear();
        if (input.equals("bye")) {
            Platform.exit();
        }
    }
}
