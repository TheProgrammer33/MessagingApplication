package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
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
import java.util.Locale;
import java.util.ResourceBundle;

public class DefaultPageController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;
    private HTTPRequest httpRequest;
    private int currentThreadId;
    private Language language;

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

    public Group initializeDefaultPage(Stage primaryStage, UserData userData, HTTPRequest httpRequest, Language language) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/DefaultPageRefactor.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        this.primaryStage = primaryStage;
        this.userData = userData;
        this.httpRequest = httpRequest;
        this.language = language;

        AnchorPane friendsListAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        ScrollPane friendsScrollPane = (ScrollPane) friendsListAnchor.getChildren().get(0);
        friendsBox = (VBox) friendsScrollPane.getContent();

        chatNameAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        Text chatName = (Text) chatNameAnchor.getChildren().get(0);

        if (!this.userData.getThreadList().isEmpty())
        {
            this.currentThreadId = this.userData.getThreadList().get(0).getThreadId();
            httpRequest.setThreadId(currentThreadId);

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

        syncToLanguage(root);

        if (this.currentThreadId != -1)
        {
            updateMessageBox(currentThreadId);
            updateFriendsBox();

            httpRequest.openWebSocket(messagesScrollPane, userData, currentThreadId);
        }

        return new Group(root);
    }

    private void syncToLanguage(Parent root)
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle defaultResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/DefaultPage", locale);

        messagingTextField.setPromptText(defaultResourceBundle.getString("textPromtText"));

        HBox sendButtonHBox = (HBox) root.getChildrenUnmodifiable().get(5);
        Button sendButton = (Button) sendButtonHBox.getChildren().get(0);
        sendButton.setText(defaultResourceBundle.getString("send"));

        HBox friendSettingsHBox = (HBox) root.getChildrenUnmodifiable().get(6);

        Button friendsButton = (Button) friendSettingsHBox.getChildren().get(0);
        friendsButton.setText(defaultResourceBundle.getString("friends"));

        Button settingsButton = (Button) friendSettingsHBox.getChildren().get(1);
        settingsButton.setText(defaultResourceBundle.getString("settings"));

        AnchorPane addChatAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);
        Text addChatText = (Text) addChatAnchorPane.getChildren().get(1);
        addChatText.setText(defaultResourceBundle.getString("addChat"));
    }

    public int getCurrentThreadId()
    {
        return currentThreadId;
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
                httpRequest.changeThread(thread.getThreadId());
            });
        }
    }

    public void updateMessageBox(int threadId)
    {
        List<Message> messages = this.httpRequest.getMessages(threadId);

        messagesBox.getChildren().clear();

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

            messagesBox.setSpacing(10);
            messagesBox.getChildren().add(hBox);
        }
    }

    public String encryptMessage(String message)
    {
        Encyption encyptionHandler = new Encyption();

        try
        {
            return encyptionHandler.encrypt(message);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    @FXML
    public void initializeSettingsPage()
    {
        SettingsPageController settingsPageController = new SettingsPageController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(settingsPageController.initializeSettingsPage(primaryStage, userData, httpRequest, language));
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
    public void initializeFriendsPage() //FIXME: Loses websocket thread when switching back to thread
    {
        FriendsManagementController friendsManagementController = new FriendsManagementController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(friendsManagementController.initializeFriendsManagementPage(primaryStage, userData, httpRequest, language));
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
    public void initializeChatSettings() throws IOException
    {
        MenuPageController menuPageController = new MenuPageController();

        Scene newScene = new Scene(menuPageController.initializePage(primaryStage, userData, httpRequest, language));

        primaryStage.setScene(newScene);
    }

    @FXML
    public void initializeGroupChatPage()
    {
        NewChatController newChatController = new NewChatController();

        Stage secondaryWindowStage = new Stage();

        Scene scene = null;
        try {
            scene = new Scene(newChatController.initializeGroupChatPage(secondaryWindowStage, userData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        secondaryWindowStage.setScene(scene);

        secondaryWindowStage.show();

        secondaryWindowStage.setAlwaysOnTop(true);
        secondaryWindowStage.setAlwaysOnTop(false);

        secondaryWindowStage.toFront();

        newChatController.getCreateChatButton().setOnAction(event ->
        {
            newChatController.returnToDefaultPage();
            System.out.println("Closed!");
            try
            {
                createNewChatFromMenu(newChatController.getFriendsVbox());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    private void updateThreadList(Thread newThread)
    {
        this.userData.addThread(newThread);
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

        Thread newthread = this.httpRequest.createThread(usernames, userData.getUsername());

        updateThreadList(newthread);
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

        String encryptedMessage = encryptMessage(messagingTextField.getText());

        this.httpRequest.sendMessage(encryptedMessage, String.valueOf(this.currentThreadId), userData.getUsername());

        messagingTextField.setText("");

        updateMessageBox(this.currentThreadId);

        messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }
}
