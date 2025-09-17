package cody.gui;

import java.io.IOException;

import cody.Cody;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Cody using FXML.
 */
public class Main extends Application {

    private Cody cody;

    @Override
    public void start(Stage stage) {
        try {
            cody = new Cody();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setCody(cody); // inject the Cody instance
            fxmlLoader.<MainWindow>getController().setWelcomeMessage();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
