package sample;

import com.sun.javafx.iio.ImageLoader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.getIcons().add(new Image(new FileInputStream("src/sample/resources/CarrierFox128x1.png")));

        LogInController logInController = new LogInController();

        Group root = logInController.initializeLogInPage(primaryStage);

        primaryStage.setTitle("CarrierFox");

        initializeApplication(primaryStage, root);

        /*
        submitButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                if (isEmpty(username) || isEmpty(password))
                {
                    invalidUserOrPass.setVisible(true);
                    return;
                }
                else
                {
                    invalidUserOrPass.setVisible(false);
                }

                try
                {
                    BorderPane messagingScene = (BorderPane) FXMLLoader.load(getClass().getResource("resources/MessageBox.fxml"));

                    Group newRoot = new Group(messagingScene);

                    initializeApplication(primaryStage, newRoot);

                    AnchorPane messageAnchor = (AnchorPane) messagingScene.getChildren().get(0);
                    Button sendMessage = (Button) messageAnchor.getChildren().get(1);

                    sendMessage.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            makeHTTPRequest("SEND MESSAGE");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        createNewAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try
                {
                    AnchorPane newAccountScene = (AnchorPane) FXMLLoader.load(getClass().getResource("resources/NewAccount.fxml"));

                    Group newRoot = new Group(newAccountScene);

                    initializeApplication(primaryStage, newRoot);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

            }
        });
        */
    }

    private Boolean isEmpty(TextField textField)
    {
        if (textField.getText().isEmpty())
            return true;
        else
            return false;
    }

    private Boolean isEmpty(PasswordField passwordField)
    {
        if (passwordField.getText().isEmpty())
            return true;
        else
            return false;
    }

    public void makeHTTPRequest(String input)
    {
        String url = "https://catherinegallaher.com/api/thread/1234/message/add";

        String parameters = "messageBody=" + input + "&user=septri";
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

        try {
            URL connection = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            try( DataOutputStream wr = new DataOutputStream( httpURLConnection.getOutputStream())) {
                wr.write( postData );
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeApplication(Stage primaryStage, Group root)
    {
        primaryStage.close();

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
