package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class MenuPageController extends AnchorPane
{
    private UserData userData;
    private HTTPRequest httpRequest;
    private Language language;

    private Stage primaryStage;

    private TextField searchBox;
    private TextField messagingTextField;

    public Group initializePage(Stage primaryStage, UserData userData, HTTPRequest httpRequest, Language language) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/DefaultPageWithMenu.fxml"));

        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        Parent root = fxmlLoader.load();

        setPrimaryStage(primaryStage);
        setHttpRequest(httpRequest);
        setUserData(userData);
        setLanguage(language);

        AnchorPane messagesPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);
        httpRequest.updateMessageBox((ScrollPane) messagesPane.getChildren().get(0));

        setMessagingTextField((TextField) root.getChildrenUnmodifiable().get(2));

        AnchorPane searchPane = (AnchorPane) root.getChildrenUnmodifiable().get(3);
        HBox searchHBox = (HBox) searchPane.getChildren().get(3);
        setSearchBox((TextField) searchHBox.getChildren().get(0));

        AnchorPane usernamePane = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        Text username = (Text) usernamePane.getChildren().get(0);
        username.setText(userData.getUsername());

        initializeSearchingPage();

        httpRequest.refreshMessagesBox();

        syncToLanguage(root);

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

        HBox friendsSettingsHBox = (HBox) root.getChildrenUnmodifiable().get(4);

        Button friendsButton = (Button) friendsSettingsHBox.getChildren().get(0);
        friendsButton.setText(defaultResourceBundle.getString("friends"));

        Button settingsButton = (Button) friendsSettingsHBox.getChildren().get(1);
        settingsButton.setText(defaultResourceBundle.getString("settings"));

        AnchorPane menuPane = (AnchorPane) root.getChildrenUnmodifiable().get(3);

        double centerPosition = menuPane.getWidth() / 2;

        HBox searchHBox = (HBox) menuPane.getChildren().get(3);
        TextField searchBox = (TextField) searchHBox.getChildren().get(0);
        searchBox.setPromptText(defaultResourceBundle.getString("search"));
        searchBox.setLayoutX(centerPosition);

        HBox chatSettingsTextHBox = (HBox) menuPane.getChildren().get(2);
        Text chatSettingsText = (Text) chatSettingsTextHBox.getChildren().get(0);
        chatSettingsText.setText(defaultResourceBundle.getString("chatSettings"));
        chatSettingsText.setLayoutX(centerPosition);

        HBox clearConversationHBox = (HBox) menuPane.getChildren().get(0);
        Button clearConversationButton = (Button) clearConversationHBox.getChildren().get(0);
        clearConversationButton.setText(defaultResourceBundle.getString("clearConversation"));

        HBox muteConversationHBox = (HBox) menuPane.getChildren().get(1);
        Button muteConversationButton = (Button) muteConversationHBox.getChildren().get(0);
        muteConversationButton.setText(defaultResourceBundle.getString("muteConversation"));

        HBox systemSettingsTextHBox = (HBox) menuPane.getChildren().get(4);
        Text systemSettingsText = (Text) systemSettingsTextHBox.getChildren().get(0);
        systemSettingsText.setText(defaultResourceBundle.getString("systemSettings"));

        HBox systemSettingsHBox = (HBox) menuPane.getChildren().get(5);
        Button systemSettingsButton = (Button) systemSettingsHBox.getChildren().get(0);
        systemSettingsButton.setText(defaultResourceBundle.getString("systemSettings"));
    }

    @FXML
    public void sendMessageOnClick()
    {
        int currentThreadId = httpRequest.getThreadId();

        if (messagingTextField.getText().compareTo("") == 0)
        {
            return;
        }

        if (currentThreadId == -1)
            return;

        String encryptedMessage = encryptMessage(messagingTextField.getText());

        this.httpRequest.sendMessage(encryptedMessage, String.valueOf(currentThreadId), userData.getUsername());

        messagingTextField.setText("");

        httpRequest.refreshMessagesBox();
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
    public void initializeSettingsPage() //FIXME: Loses websocket thread when switching back to thread
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
    public void initializeFriendsPage() //FIXME: Loses websocket thread when switching back to thread
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

    private void initializeSearchingPage() throws IOException
    {
        searchBox.setOnAction(event ->
        {
            SearchingPageController searchingPageController = new SearchingPageController();

            Scene newScene = null;
            try
            {
                newScene = new Scene(searchingPageController.initializePage(primaryStage, userData, httpRequest));
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            primaryStage.setScene(newScene);
        });
    }

    private void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public void setSearchBox(TextField searchBox)
    {
        this.searchBox = searchBox;
    }

    public void setUserData(UserData userData)
    {
        this.userData = userData;
    }

    public void setHttpRequest(HTTPRequest httpRequest)
    {
        this.httpRequest = httpRequest;
    }

    public void setMessagingTextField(TextField messagingTextField)
    {
        this.messagingTextField = messagingTextField;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }
}
