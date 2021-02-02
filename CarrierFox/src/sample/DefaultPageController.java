package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DefaultPageController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;
    private HTTPRequest httpRequest;
    private int currentThreadId;

    @FXML
    private AnchorPane chatNameAnchor;
    @FXML
    private TextField messagingTextField;
    @FXML
    private VBox messagesBox;
    @FXML
    private ScrollPane messagesScrollPane;

    @FXML
    private VBox friendsBox;
    @FXML
    private ComboBox<String> addChatBox;

    public Group initializeDefaultPage(Stage primaryStage, UserData userData) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/DefaultPageRefactor.fxml"));
        HTTPRequest httpRequest = new HTTPRequest();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        this.primaryStage = primaryStage;
        this.userData = userData;
        this.httpRequest = httpRequest;

        AnchorPane friendsListAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        ScrollPane friendsScrollPane = (ScrollPane) friendsListAnchor.getChildren().get(0);
        friendsBox = (VBox) friendsScrollPane.getContent();

        chatNameAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        Text chatName = (Text) chatNameAnchor.getChildren().get(0);

        if (!this.userData.getThreadList().isEmpty())
        {
            this.currentThreadId = this.userData.getThreadList().get(0).getThreadId();

            Thread initialThread = this.userData.getThreadList().get(0);
            if (initialThread.getName().contains(this.userData.getUsername()))
            {
                initialThread.setName(initialThread.getName().replace(this.userData.getUsername() + ",", ""));
            }
            chatName.setText(initialThread.getName());
        }
        else
        {
            this.currentThreadId = -1;
            chatName.setText("");
            friendsBox.getChildren().clear();
        }
        AnchorPane messageBoxAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(3);
        messagesScrollPane = (ScrollPane) messageBoxAnchor.getChildren().get(0);
        messagesBox = (VBox) messagesScrollPane.getContent();

        messagingTextField = (TextField) root.getChildrenUnmodifiable().get(4);

        AnchorPane addChatAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(1);

        /*
        ComboBox<String> addChatBox = new ComboBox<>();

        addChatBox.setStyle("-fx-background-color: #646467;");
        addChatBox.setOpacity(0.0);
        addChatBox.setLayoutX(10.0);
        addChatBox.setLayoutY(4.0);
        addChatBox.setPrefWidth(150.0);

        addChatBox.getItems().addAll("item1", "item2", "item3");

        addChatAnchor.getChildren().add(addChatBox);

        this.addChatBox = addChatBox;

        ObservableList<String> friendNames = FXCollections.observableArrayList();
        List<Friend> friendsList = this.userData.getFriendList();
        for (int i = 0; i < friendsList.size(); i++)
        {
            friendNames.add(i, friendsList.get(i).getUsername());
        }

        addChatBox.setItems(friendNames);

        addChatBox.setPromptText("click me");

        EventHandler<ActionEvent> addChatEvent = new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                List<String> users = new ArrayList<>();

                users.add(userData.getUsername());
                users.add(addChatBox.getValue());

                userData.addThread(httpRequest.createThread(users));

                try
                {
                    updateFriendsBox();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };

        addChatBox.setOnAction(addChatEvent);*/

        if (this.currentThreadId != -1)
        {
            updateMessageBox(currentThreadId);
            updateFriendsBox();

            httpRequest.openWebSocket(messagesScrollPane, userData, currentThreadId);
        }

        return new Group(root);
    }

    public void updateFriendsBox() throws IOException
    {
        List<Thread> threadList = this.userData.getThreadList();
        friendsBox.getChildren().clear();

        for (Thread thread : threadList)
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/friendTemplateDefaultPage.fxml"));
            AnchorPane friendThreadAnchor = loader.load();

            if (thread.getName().contains(this.userData.getUsername()))
            {
                thread.setName(thread.getName().replace(this.userData.getUsername() + ",", ""));
            }

            Button friendButton = (Button) friendThreadAnchor.getChildren().get(2);
            HBox friendHBox = (HBox) friendThreadAnchor.getChildren().get(1);
            Text friendUser = (Text) friendHBox.getChildren().get(0);

            friendUser.setText(thread.getName());

            friendUser.setTextAlignment(TextAlignment.CENTER);
            friendHBox.setAlignment(Pos.CENTER);

            friendsBox.getChildren().add(friendThreadAnchor);

            friendButton.setOnAction(e ->
            {
                switchThreads(thread);
            });
        }
    }

    public void switchThreads(Thread thread)
    {
        this.currentThreadId = thread.getThreadId();

        Text threadName = (Text) chatNameAnchor.getChildren().get(0);
        threadName.setText(thread.getName());

        httpRequest.closeWebSocket();

        httpRequest.openWebSocket(messagesScrollPane, userData, thread.getThreadId());
    }

    public void updateMessageBox(int threadId)
    {
        List<Message> messages = this.httpRequest.getMessages(threadId);

        messagesBox.getChildren().clear();

        for (int i = 0; i < messages.size(); i++)
        {
            HBox hBox = new HBox();
            Button messageWrapper = new Button();

            messageWrapper.setTextFill(Color.WHITE);
            messageWrapper.setText(messages.get(i).getMessageBody());

            messageWrapper.wrapTextProperty().setValue(true);

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

            messagesBox.setSpacing(10);
            messagesBox.getChildren().add(hBox);
        }
    }

    @FXML
    public void initializeSettingsPage()
    {
        SettingsPageController settingsPageController = new SettingsPageController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(settingsPageController.initializeSettingsPage(primaryStage, userData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        primaryStage.toFront();
    }

    @FXML
    public void initializeFriendsPage()
    {
        FriendsManagementController friendsManagementController = new FriendsManagementController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(friendsManagementController.initializeFriendsManagementPage(primaryStage, userData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        primaryStage.toFront();
    }

    @FXML
    public void initializeGroupChatPage()
    {
        GroupChatController groupChatController = new GroupChatController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(groupChatController.initializeGroupChatPage(primaryStage, userData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        primaryStage.toFront();
    }

    @FXML
    public void sendMessageOnClick()
    {
        if (messagingTextField.getText().compareTo("") == 0)
        {
            return;
        }

        if (this.currentThreadId == -1)
            return;

        this.httpRequest.sendMessage(messagingTextField.getText(), String.valueOf(this.currentThreadId), userData.getUsername());

        messagingTextField.setText("");

        updateMessageBox(this.currentThreadId);

        messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
    }
}
