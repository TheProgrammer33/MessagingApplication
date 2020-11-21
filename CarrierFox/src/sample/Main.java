package sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketAdapter;
import org.java_websocket.WebSocketFactory;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.List;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import org.slf4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.LoggerFactory;

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

        //connect();

        HTTPRequest httpRequest = new HTTPRequest();

        //httpRequest.sendMessage("Hello", "2");
    }

    public void connect()
    {
        try
        {
            ExampleClient chatclient = new ExampleClient(new URI("wss://catherinegallaher.com/"));

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            chatclient.setSocketFactory(factory);

            chatclient.connectBlocking();

            chatclient.send("{\"update\": \"Hello\",\"threadId\":2}");

            Thread t1 = new Thread(new Runnable() { //Switch to end when user switches threads
                public void run()
                {
                    try
                    {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        while (true)
                        {
                            String line = reader.readLine();
                            if (line.equals("close"))
                            {
                                chatclient.closeBlocking();
                            } else if (line.equals("open"))
                            {
                                chatclient.reconnect();
                            } else
                            {
                                chatclient.send(line);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }});
            t1.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
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
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
