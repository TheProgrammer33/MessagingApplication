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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
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

                String text = textField.getText();

                text = text.replaceAll("\\s+","");

                makeHTTPRequest(text);
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
        String url = "http://localhost:3000/api/thread/1234/message/add";

        try {
            URL connection = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();
            String urlParameters = "message=" + input + "&user=jack";
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);

            /*
            InputStream response = connection.getInputStream();

            Scanner scanner = new Scanner(response);
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
            */

            /*
            Map<String, String> postData = new HashMap<>();
            postData.put("message", input);
            postData.put("user", "jack");

            StringBuilder requestData = new StringBuilder();

            for (Map.Entry<String, String> param : postData.entrySet())
            {
                if (requestData.length() != 0) {
                    requestData.append('&');
                }
                // Encode the parameter based on the parameter map we've defined
                // and append the values from the map to form a single parameter
                requestData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                requestData.append('=');
                requestData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }*/

            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpURLConnection.setRequestProperty("Content-Length", Integer.toString(postData.length));

            httpURLConnection.connect();

            try (DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream()))
            {
                dataOutputStream.write(postData);

                dataOutputStream.flush();
                dataOutputStream.close();
            }



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

