package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        AnchorPane chatNameAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        Text chatName = (Text) chatNameAnchor.getChildren().get(0);

        updateChatName(chatName);

        AnchorPane messageBoxAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(3);
        messagesScrollPane = (ScrollPane) messageBoxAnchor.getChildren().get(0);
        messagesBox = (VBox) messagesScrollPane.getContent();

        messagingTextField = (TextField) root.getChildrenUnmodifiable().get(4);

        HBox buttonsHBox = (HBox) root.getChildrenUnmodifiable().get(6);

        Image friendsIcon = new Image(String.valueOf(this.getClass().getResource("resources/friendsx2.png")));
        Button friendsButton = (Button) buttonsHBox.getChildren().get(0);
        buildImageButton(friendsButton, friendsIcon);

        Image searchIcon = new Image(String.valueOf(this.getClass().getResource("resources/searchx2.png")));
        Button searchButton = (Button) buttonsHBox.getChildren().get(1);
        buildImageButton(searchButton, searchIcon);

        Image chatSettingsIcon = new Image(String.valueOf(this.getClass().getResource("resources/meatballMenux2.png")));
        Button chatSettingsButton = (Button) buttonsHBox.getChildren().get(2);
        buildImageButton(chatSettingsButton, chatSettingsIcon);

        Image settingsIcon = new Image(String.valueOf(this.getClass().getResource("resources/settingsx2.png")));
        Button settingsButton = (Button) buttonsHBox.getChildren().get(3);
        buildImageButton(settingsButton, settingsIcon);

        Text stringTextWidth = (Text) root.getChildrenUnmodifiable().get(7);

        syncToLanguage(root);

        if (this.currentThreadId != -1)
        {
            updateFriendsBox();

            httpRequest.openWebSocket(messagesScrollPane, stringTextWidth, userData, currentThreadId);
        }

        return new Group(root);
    }

    private void buildImageButton(Button button, Image image)
    {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20.0);
        imageView.setPreserveRatio(true);

        button.setText("");
        button.setGraphic(imageView);
    }

    private void changeThreadId(int newThreadId)
    {
        this.currentThreadId = newThreadId;

        httpRequest.setThreadId(newThreadId);
    }

    private void updateChatName(Text chatNameText)
    {
        if (!this.userData.getThreadList().isEmpty())
        {
            int newThreadId = this.userData.getThreadList().get(0).getThreadId();
            changeThreadId(newThreadId);

            Thread initialThread = this.userData.getThreadList().get(0);
            if (initialThread.getName().contains(this.userData.getUsername()))
            {
                initialThread.setName(initialThread.getName().replace(this.userData.getUsername() + ",", ""));
            }
            chatNameText.setText(initialThread.getName());
        }
        else
        {
            this.currentThreadId = -1;
            chatNameText.setText("");
            friendsBox.getChildren().clear();
        }
    }

    private void syncToLanguage(Parent root)
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle defaultResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/DefaultPage", locale);

        messagingTextField.setPromptText(defaultResourceBundle.getString("textPromtText"));

        HBox sendButtonHBox = (HBox) root.getChildrenUnmodifiable().get(5);
        Button sendButton = (Button) sendButtonHBox.getChildren().get(0);
        sendButton.setText(defaultResourceBundle.getString("send"));

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

    public String encryptMessage(String message)
    {
        Encyption encryptionHandler = new Encyption();

        try
        {
            return encryptionHandler.encrypt(message);
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
    public void initializeFriendsPage()
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
    private void initializeSearchingPage()
    {
        SearchingPageController searchingPageController = new SearchingPageController();

        Scene newScene = null;
        try
        {
            newScene = new Scene(searchingPageController.initializePage(primaryStage, userData, httpRequest, language));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        primaryStage.setScene(newScene);
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

        messagesScrollPane.vvalueProperty().bind(messagesBox.heightProperty());
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }
}
