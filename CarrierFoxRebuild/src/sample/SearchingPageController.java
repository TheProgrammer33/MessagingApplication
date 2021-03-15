package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchingPageController extends AnchorPane
{
    private UserData userData;
    private HTTPRequest httpRequest;

    private Stage primaryStage;

    private TextField searchBox;
    private TextField messagingTextField;

    public Group initializePage(Stage primaryStage, UserData userData, HTTPRequest httpRequest) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/DefaultPageWithSearching.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        setPrimaryStage(primaryStage);
        setHttpRequest(httpRequest);
        setUserData(userData);

        AnchorPane messagesPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);
        httpRequest.updateMessageBox((ScrollPane) messagesPane.getChildren().get(0));

        setMessagingTextField((TextField) root.getChildrenUnmodifiable().get(2));

        AnchorPane searchPane = (AnchorPane) root.getChildrenUnmodifiable().get(6);
        setSearchBox((TextField) searchPane.getChildren().get(0));

        AnchorPane usernamePane = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        Text username = (Text) usernamePane.getChildren().get(0);
        username.setText(userData.getUsername());

        httpRequest.refreshMessagesBox();

        return new Group(root);
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

    public TextField getSearchBox()
    {
        return searchBox;
    }

    public void setSearchBox(TextField searchBox)
    {
        this.searchBox = searchBox;
    }
}
