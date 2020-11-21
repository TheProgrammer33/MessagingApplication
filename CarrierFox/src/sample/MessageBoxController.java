package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class MessageBoxController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    @FXML
    private TextField messageBox;
    @FXML
    private VBox messageStack;
    @FXML
    private ScrollPane messageScrollPane;

    public Group initializeMessageBoxPage(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/MessageBox.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        setPrimaryStage(primaryStage);

        Parent root = fxmlLoader.load();

        BorderPane borderPane = (BorderPane) root.getChildrenUnmodifiable().get(0);
        AnchorPane anchorPane = (AnchorPane) borderPane.getChildren().get(0);
        messageBox = (TextField) anchorPane.getChildrenUnmodifiable().get(0);

        AnchorPane messageAnchor = (AnchorPane) borderPane.getChildren().get(1);
        messageScrollPane = (ScrollPane) messageAnchor.getChildren().get(0);
        messageStack = (VBox) messageScrollPane.getContent();

        HTTPRequest httpRequest = new HTTPRequest();

        List<Message> messageList = httpRequest.getMessages();

        setMessageStackToList(messageList);

        messageScrollPane.vvalueProperty().bind(messageStack.heightProperty());

        return new Group(root);
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void sendMessage()
    {
        System.out.println(messageBox.getText());

        HTTPRequest httpRequest = new HTTPRequest();

        //httpRequest.sendMessage(messageBox.getText());

        List<Message> messages = httpRequest.getMessages();

        setMessageStackToList(messages);

        messageBox.setText("");
    }

    public void setMessageStackToList(List<Message> messageList)
    {
        messageStack.getChildren().clear();

        for (Message message : messageList)
        {
            if (message.getUser().compareTo("septri") == 0)
            {
                HBox hBox = new HBox();

                Text text = new Text();

                text.setText(message.getMessageBody());

                text.setFill(Color.WHITE);

                hBox.getChildren().add(text);

                hBox.setAlignment(Pos.BASELINE_RIGHT);

                messageStack.getChildren().add(hBox);
            }
            else
            {
                HBox hBox = new HBox();

                Text text = new Text();

                text.setText(message.getMessageBody());

                text.setFill(Color.WHITE);

                hBox.getChildren().add(text);

                hBox.setAlignment(Pos.BASELINE_LEFT);

                messageStack.getChildren().add(hBox);
            }
        }
    }

}
