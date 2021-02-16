package sample;

import java.net.URI;
import java.net.URISyntaxException;
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

    public WebSocketController(URI serverUri, ScrollPane messagesScrollPane, UserData userData, int threadId) {
        super(serverUri);
        this.messagesScrollPane = messagesScrollPane;
        this.messagesBox = (VBox) messagesScrollPane.getContent();
        this.userData = userData;
        this.threadId = threadId;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");

    }

    @Override
    public void onMessage(String message) {
        Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                updateMessageBox();
                messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
            }
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

        Platform.runLater(new Runnable() {
            @Override
            public void run()
            {
                updateMessageBox();
                messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
            }
        });
    }

    private void updateMessageBox()
    {
        HTTPRequest httpRequest = new HTTPRequest();
        List<Message> messages = httpRequest.getMessages(this.threadId);

        this.messagesBox.getChildren().clear();

        if (messages == null)
            return;

        for (int i = 0; i < messages.size(); i++)
        {
            HBox hBox = new HBox();
            Button messageWrapper = new Button();

            messageWrapper.setTextFill(Color.WHITE);
            messageWrapper.setText(messages.get(i).getMessageBody());

            hBox.getChildren().add(messageWrapper);

            if (messages.get(i).getUser().compareTo(userData.getUsername()) == 0)
            {
                hBox.setAlignment(Pos.BASELINE_RIGHT);
                messageWrapper.setStyle("-fx-background-color: #4285F4");
            }
            else
            {
                hBox.setAlignment(Pos.BASELINE_LEFT);
                messageWrapper.setStyle("-fx-background-color: #DB4437");
            }

            this.messagesBox.setSpacing(10);
            this.messagesBox.getChildren().add(hBox);
        }
    }

}
