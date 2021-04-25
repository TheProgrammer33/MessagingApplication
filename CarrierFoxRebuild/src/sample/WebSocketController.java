package sample;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
    private Text stringTextWidth;
    private UserData userData;
    private int threadId;

    private List<Message> decryptedMessages;

    public WebSocketController(URI serverUri, ScrollPane messagesScrollPane, Text stringTextWidth, UserData userData, int threadId) {
        super(serverUri);
        this.messagesScrollPane = messagesScrollPane;
        this.messagesBox = (VBox) messagesScrollPane.getContent();
        this.stringTextWidth = stringTextWidth;
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
            try
            {
                updateMessageBox();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
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
            try
            {
                updateMessageBox();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
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

    public void updateMessageBox() throws IOException
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
            if (message.getMessageBody().contains("\n"))
            {
                message.setMessageBody(message.getMessageBody().replace("\n", ""));
            }
            else if (message.getMessageBody().isEmpty())
            {
                continue;
            }

            message.setMessageBody(decryptMessage(message.getMessageBody()));

            decryptedMessages.add(message);

            Parent messageTemplate = normalWrapping(message); //noWrapping(message); //longWrapping(message);

            this.messagesBox.setSpacing(10);
            this.messagesBox.getChildren().add(messageTemplate);
        }

        messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
    }

    private Parent normalWrapping(Message message) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/smallMessageTemplate.fxml"));
        AnchorPane messageTemplate = fxmlLoader.load();
        HBox messageHBoxWrapper = new HBox();
        Button messageWrapper = (Button) messageTemplate.getChildrenUnmodifiable().get(0);
        VBox buttonNewLineGraphic = (VBox) messageWrapper.getGraphic();
        Text messageText = (Text) messageTemplate.getChildrenUnmodifiable().get(1);

        this.stringTextWidth.setText(message.getMessageBody());
        this.stringTextWidth.applyCss();
        double stringWidth = Math.ceil(this.stringTextWidth.getLayoutBounds().getWidth()) + 5;

        if (stringWidth > 200)
        {
            messageHBoxWrapper.setPrefWidth(200);
            messageTemplate.setPrefWidth(200);
            messageWrapper.setPrefWidth(200);
            messageText.setWrappingWidth(200);
        }
        else
        {
            messageHBoxWrapper.setPrefWidth(stringWidth);
            messageTemplate.setPrefWidth(stringWidth);
            messageWrapper.setPrefWidth(stringWidth);
            messageText.setWrappingWidth(stringWidth);
        }

        int numberOfLines = (int) Math.round(stringWidth / 200) + 1;

        for (int i = 0; i < numberOfLines; i++)
        {
            Label newLineLabel = new Label("");

            buttonNewLineGraphic.getChildren().add(newLineLabel);
        }

        if (message.getUser().compareTo(userData.getUsername()) == 0)
        {
            messageHBoxWrapper.setAlignment(Pos.BASELINE_RIGHT);
            messageWrapper.setStyle("-fx-background-color: #4285F4");
        } else
        {
            messageHBoxWrapper.setAlignment(Pos.BASELINE_LEFT);
            messageWrapper.setStyle("-fx-background-color: #DB4437");
        }

        messageText.setText(message.getMessageBody());

        messageHBoxWrapper.getChildren().add(messageTemplate);

        return messageHBoxWrapper;
    }

    private Parent noWrapping(Message message)
    {
        HBox messageTemplate = new HBox();
        Button messageWrapper = new Button();

        messageWrapper.setTextFill(Color.WHITE);
        messageWrapper.setText(message.getMessageBody());

        messageTemplate.getChildren().add(messageWrapper);

        if (message.getUser().compareTo(userData.getUsername()) == 0)
        {
            messageTemplate.setAlignment(Pos.BASELINE_RIGHT);
            messageWrapper.setStyle("-fx-background-color: #4285F4");
        } else
        {
            messageTemplate.setAlignment(Pos.BASELINE_LEFT);
            messageWrapper.setStyle("-fx-background-color: #DB4437");
        }

        return messageTemplate;
    }

    private Parent longWrapping(Message message) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/messageTemplate.fxml"));
        Parent messageTemplate = fxmlLoader.load();
        Button messageWrapper = (Button) messageTemplate.getChildrenUnmodifiable().get(0);
        VBox buttonNewLineGraphic = (VBox) messageWrapper.getGraphic();
        Text messageText = (Text) messageTemplate.getChildrenUnmodifiable().get(1);

        int numberOfLines = (message.getMessageBody().length() / 63) + 1;

        for (int i = 0; i < numberOfLines; i++)
        {
            Label newLineLabel = new Label("");

            buttonNewLineGraphic.getChildren().add(newLineLabel);
        }

        if (message.getUser().compareTo(userData.getUsername()) == 0)
        {
            messageWrapper.setStyle("-fx-background-color: #4285F4");
        } else
        {
            messageWrapper.setStyle("-fx-background-color: #DB4437");
        }

        messageText.setText(message.getMessageBody());

        return messageTemplate;
    }

    public List<Message> getDecryptedMessages()
    {
        return decryptedMessages;
    }
}
