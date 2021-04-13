package sample;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
public class WebSocketController extends WebSocketClient {

    private ScrollPane messagesScrollPane;
    private VBox messagesBox;
    private UserData userData;
    private int threadId;

    private List<Message> decryptedMessages;

    public WebSocketController(URI serverUri, ScrollPane messagesScrollPane, UserData userData, int threadId) {
        super(serverUri);
        this.messagesScrollPane = messagesScrollPane;
        this.messagesBox = (VBox) messagesScrollPane.getContent();
        this.userData = userData;
        this.threadId = threadId;
        this.decryptedMessages = new ArrayList<Message>();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");
    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(() ->
        {
            updateMessageBox();
            messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
        });
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public void changeThread(int threadId)
    {
        this.threadId = threadId;

        Platform.runLater(() ->
        {
            updateMessageBox();
            messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
        });
    }

    private String decryptMessage(String cipherText)
    {
        Encyption encryptionHandler = new Encyption();

        try
        {
            String decryptedMessage = encryptionHandler.decrypt(cipherText);

            if (!decryptedMessage.equals(""))
                return decryptedMessage;
        } catch (Exception e)
        {
            System.out.println("Message not encrypted!");
        }

        return cipherText;
    }

    public void setMessagesScrollPane(ScrollPane messagesScrollPane)
    {
        this.messagesScrollPane = messagesScrollPane;
    }

    public void updateMessageBox()
    {
        HTTPRequest httpRequest = new HTTPRequest();
        List<Message> messages = httpRequest.getMessages(this.threadId);
        this.messagesBox = (VBox) messagesScrollPane.getContent();

        this.messagesBox.getChildren().clear();
        decryptedMessages.clear();

        if (messages == null)
            return;

        for (Message message : messages)
        {
            Message decryptedMessage = message;
            HBox hBox = new HBox();
            Button messageWrapper = new Button();

            decryptedMessage.setMessageBody(decryptMessage(message.getMessageBody()));

            messageWrapper.setTextFill(Color.WHITE);
            messageWrapper.setText(decryptedMessage.getMessageBody());

            hBox.getChildren().add(messageWrapper);

            if (message.getUser().compareTo(userData.getUsername()) == 0)
            {
                hBox.setAlignment(Pos.BASELINE_RIGHT);
                messageWrapper.setStyle("-fx-background-color: #4285F4");
            } else
            {
                hBox.setAlignment(Pos.BASELINE_LEFT);
                messageWrapper.setStyle("-fx-background-color: #DB4437");
            }

            decryptedMessages.add(decryptedMessage);

            this.messagesBox.setSpacing(10);
            this.messagesBox.getChildren().add(hBox);
        }

        messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
    }

    public List<Message> getDecryptedMessages()
    {
        return decryptedMessages;
    }
}
