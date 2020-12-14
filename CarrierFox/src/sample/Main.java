package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;

import java.security.NoSuchAlgorithmException;

import java.io.FileInputStream;

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
    }

    private void decryptMessage() throws NoSuchAlgorithmException, IOException
    {
        Encryption encryption = new Encryption();

        FileOutputStream fileOutputStream = new FileOutputStream("src/sample/resources/encrypted.txt");

        String message = "Secwet message\n";

        fileOutputStream.write(encryption.encryptMessage(message).getBytes());

        fileOutputStream.close();
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

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
