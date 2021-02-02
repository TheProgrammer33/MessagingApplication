package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.util.concurrent.TimeUnit;

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

        if (this.currentThreadId != -1)
        {
            updateMessageBox(currentThreadId);
            updateFriendsBox();

            httpRequest.openWebSocket(messagesScrollPane, userData, currentThreadId);
        }

        return new Group(root);
    }

    private void closeWebSocket()
    {
        httpRequest.closeWebSocket();
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

        closeWebSocket();

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
        closeWebSocket();

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
        closeWebSocket();

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

        Stage secondaryWindowStage = new Stage();

        Scene scene = null;
        try {
            scene = new Scene(groupChatController.initializeGroupChatPage(secondaryWindowStage, userData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        secondaryWindowStage.setScene(scene);

        secondaryWindowStage.show();

        secondaryWindowStage.setAlwaysOnTop(true);
        secondaryWindowStage.setAlwaysOnTop(false);

        secondaryWindowStage.toFront();

        groupChatController.getCreateChatButton().setOnAction(event ->
        {
            groupChatController.returnToDefaultPage();
            System.out.println("Closed!");
            try
            {
                createNewChatFromMenu(groupChatController.getFriendsVbox());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    private void createNewChatFromMenu(VBox friendsVBox) throws IOException
    {
        List<String> usernames = new ArrayList<>();
        for (int i = 0; i < friendsVBox.getChildren().size(); i++)
        {
            AnchorPane friendAnchorPane = (AnchorPane) friendsVBox.getChildren().get(i);
            Text friendUsername = (Text) friendAnchorPane.getChildren().get(0);
            CheckBox checkBox = (CheckBox) friendAnchorPane.getChildren().get(1);

            if (checkBox.isSelected())
            {
                usernames.add(friendUsername.getText());
            }
        }

        this.httpRequest.createThread(usernames, userData.getUsername());

        try // TODO: 2/2/21 FIX issue where friends box does not update showing the new chat!
        {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        updateFriendsBox();
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
