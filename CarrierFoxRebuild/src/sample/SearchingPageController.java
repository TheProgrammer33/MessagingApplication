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

public class SearchingPageController extends AnchorPane
{
    private UserData userData;
    private HTTPRequest httpRequest;
    private Language language;

    private Stage primaryStage;

    private TextField messagingTextField;

    public Group initializePage(Stage primaryStage, UserData userData, HTTPRequest httpRequest, Language language) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/DefaultPageWithSearching.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        setPrimaryStage(primaryStage);
        setHttpRequest(httpRequest);
        setUserData(userData);
        setLanguage(language);

        AnchorPane messagesPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);
        httpRequest.updateMessageBox((ScrollPane) messagesPane.getChildren().get(0));

        setMessagingTextField((TextField) root.getChildrenUnmodifiable().get(2));

        AnchorPane usernamePane = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        Text username = (Text) usernamePane.getChildren().get(0);
        username.setText(userData.getUsername());

        httpRequest.refreshMessagesBox();

        syncLanguage(root);

        return new Group(root);
    }

    private void syncLanguage(Parent root)
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle defaultResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/DefaultPage", locale);

        messagingTextField.setPromptText(defaultResourceBundle.getString("textPromtText"));

        HBox sendButtonHBox = (HBox) root.getChildrenUnmodifiable().get(4);
        Button sendButton = (Button) sendButtonHBox.getChildren().get(0);
        sendButton.setText(defaultResourceBundle.getString("send"));

        HBox friendSettingsHBox = (HBox) root.getChildrenUnmodifiable().get(5);

        Button friendsButton = (Button) friendSettingsHBox.getChildren().get(0);
        friendsButton.setText(defaultResourceBundle.getString("friends"));

        Button settingsButton = (Button) friendSettingsHBox.getChildren().get(1);
        settingsButton.setText(defaultResourceBundle.getString("settings"));

        AnchorPane searchAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(3);

        HBox resultsHBox = (HBox) searchAnchorPane.getChildren().get(2);
        Text resultsText = (Text) resultsHBox.getChildren().get(0);
        resultsText.setText(defaultResourceBundle.getString("results"));
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

    private void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
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
