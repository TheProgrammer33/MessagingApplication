package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

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
