package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader();

            String fxmlDocPath = "../MessageBox.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

            BorderPane root = (BorderPane) loader.load(fxmlStream);
            AnchorPane anchorPane = (AnchorPane) root.getChildren().get(0);
            TextField textField = (TextField) anchorPane.getChildren().get(0);
            Button sendButton = (Button) anchorPane.getChildren().get(1);

            textField.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #3A3A3A;");
            textField.setPromptText("Type your message here");

            Scene scene = new Scene(root);

            primaryStage.setScene(scene);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}

