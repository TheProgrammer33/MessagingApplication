package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        BorderPane borderPane = getMessageBoxLayout();
        AnchorPane anchorPane = (AnchorPane) borderPane.getChildren().get(0);
        Button sendButton = (Button) anchorPane.getChildren().get(1);

        Group root = new Group(borderPane);

        initialize(primaryStage, root);

        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                AnchorPane anchorPane = (AnchorPane) borderPane.getChildren().get(0);
                TextField textField = (TextField) anchorPane.getChildren().get(0);

                if (textField.getText().isEmpty())
                    return;

                BorderPane newBoarderPane = getMessageBoxLayout();

                AnchorPane anchorPane1 = getTextMessageLayout();

                TextArea textArea = (TextArea) anchorPane1.getChildren().get(1);

                textArea.setDisable(true);
                textArea.setStyle("-fx-opacity: 1; -fx-font-alignment: center;");

                textArea.setText(textField.getText());

                newBoarderPane.setRight(anchorPane1);

                Group newGroup = new Group(newBoarderPane);

                initialize(primaryStage, newGroup);

                //makeHTTPRequest(textField.getText());
            }
        };

        sendButton.setOnAction(eventHandler);
    }

    public void initialize(Stage primaryStage, Group root)
    {
        primaryStage.close();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public void makeHTTPRequest(String input)
    {
        String url = "http://localhost:3000/messages/add?message=" + input + "&user=jack";

        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream response = connection.getInputStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BorderPane getMessageBoxLayout()
    {
        FXMLLoader loader = new FXMLLoader();

        BorderPane borderPane = new BorderPane();
        String fxmlDocPath = "../MessageBox.fxml";
        try {
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

            borderPane = (BorderPane) loader.load(fxmlStream);
            AnchorPane anchorPane = (AnchorPane) borderPane.getChildren().get(0);
            TextField textField = (TextField) anchorPane.getChildren().get(0);
            Button sendButton = (Button) anchorPane.getChildren().get(1);

            textField.setStyle("-fx-text-fill: #FFFFFF; -fx-background-color: #3A3A3A;");
            textField.setPromptText("Type your message here");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return borderPane;
    }

    public AnchorPane getTextMessageLayout()
    {
        AnchorPane textMessageAnchor = new AnchorPane();
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("resources/textMessage.fxml"));

            textMessageAnchor = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textMessageAnchor;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

