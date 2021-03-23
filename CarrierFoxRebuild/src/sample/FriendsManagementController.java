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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class FriendsManagementController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;
    private Language language;
    private HTTPRequest httpRequest;

    @FXML
    private VBox friendsList;

    @FXML
    private TextField addFriendField;
    @FXML
    private Button addFriendButton;

    public Group initializeFriendsManagementPage(Stage primaryStage, UserData userData, HTTPRequest httpRequest, Language language) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/FriendManagement.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        AnchorPane friendsListAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        ScrollPane scrollPane = (ScrollPane) friendsListAnchorPane.getChildren().get(0);
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();

        friendsList = (VBox) anchorPane.getChildren().get(0);

        this.primaryStage = primaryStage;
        this.userData = userData;
        this.httpRequest = httpRequest;
        this.language = language;

        List<Friend> friends = httpRequest.getFriends(userData.getUsername(), userData.get_id());

        if (friends != null)
        {
            updateFriendsList(friends);
        }

        syncLanguage(root);

        return new Group(root);
    }

    private void syncLanguage(Parent root)
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle friendsResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/FriendManagement", locale);

        AnchorPane topBarAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(0);

        HBox friendsLabelHBox = (HBox) topBarAnchorPane.getChildren().get(1);
        Text friendsLabel = (Text) friendsLabelHBox.getChildren().get(0);
        friendsLabel.setText(friendsResourceBundle.getString("friends"));

        HBox backButtonHBox = (HBox) topBarAnchorPane.getChildren().get(0);
        Button backButton = (Button) backButtonHBox.getChildren().get(0);
        backButton.setText(friendsResourceBundle.getString("back"));

        AnchorPane bottomBarAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);

        HBox addFriendsHBox = (HBox) bottomBarAnchorPane.getChildren().get(0);
        Button addFriendButton = (Button) addFriendsHBox.getChildren().get(0);
        addFriendButton.setText(friendsResourceBundle.getString("addFriend"));

        HBox searchBarHBox = (HBox) bottomBarAnchorPane.getChildren().get(1);

        TextField searchBarTextField = (TextField) searchBarHBox.getChildren().get(0);
        searchBarTextField.setPromptText(friendsResourceBundle.getString("promptText"));

        Button sendButton = (Button) searchBarHBox.getChildren().get(1);
        sendButton.setText(friendsResourceBundle.getString("add"));
    }

    private void updateFriendsList(List<Friend> friends) throws IOException
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle friendsResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/FriendManagement", locale);

        friendsList.getChildren().clear();
        for (int i = 0; i < friends.size(); i++)
        {
            FXMLLoader friendAnchorPaneLoader = new FXMLLoader(getClass().getResource("resources/friendAnchorPane.fxml"));

            AnchorPane friendAnchorPane = friendAnchorPaneLoader.load();

            friendsList.getChildren().add(friendAnchorPane);

            HBox friendUsernameHBox = (HBox) friendAnchorPane.getChildren().get(0);
            Text friendName = (Text) friendUsernameHBox.getChildren().get(0);

            HBox deleteButtonHBox = (HBox) friendAnchorPane.getChildren().get(1);
            Button deleteFriend = (Button) deleteButtonHBox.getChildren().get(0);
            deleteFriend.setText(friendsResourceBundle.getString("delete"));

            friendName.setText(friends.get(i).getUsername());

            deleteFriend.setOnAction(e ->
            {
                try
                {
                    deleteFriend(friendName.getText());
                } catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            });
        }
    }

    @FXML
    public void deleteFriend(String friendUsername) throws IOException
    {
        if (friendUsername.compareTo("") == 0)
        {
            return;
        }
        else if (!removeFriend(friendUsername))
        {
            return;
        }
        HTTPRequest httpRequest = new HTTPRequest();
        List<Friend> friends = httpRequest.getFriends(userData.getUsername(), userData.get_id());

        updateFriendsList(friends);
    }

    @FXML
    public void submitAddFriendRequest()
    {
        if (addFriendField.getText().compareTo("") == 0)
        {
            // FIXME: 11/23/20 Add error on screen for when having invalid username or empty text
        }
        else if (!addFriendRequest(addFriendField.getText()))
        {
            // FIXME: 11/23/20 Add error on screen for when having invalid username or empty text
        }

        addFriendField.setText("");
    }

    private boolean removeFriend(String friendUsername)
    {
        try
        {
            httpRequest.removeFriend(friendUsername, userData.get_id());
        }
        catch (HTTPException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean addFriendRequest(String friendUsername)
    {
        try
        {
            httpRequest.addFriend(friendUsername, userData.get_id());

            List<Friend> friends = httpRequest.getFriends(userData.getUsername(), userData.get_id());
            updateFriendsList(friends);
        }
        catch (HTTPException | IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @FXML
    public void showAddFriendMenu()
    {
        addFriendButton.setVisible(!addFriendButton.isVisible());
        addFriendField.setVisible(!addFriendField.isVisible());
    }

    @FXML
    public void returnToDefaultPage()
    {
        DefaultPageController defaultPageController = new DefaultPageController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(defaultPageController.initializeDefaultPage(primaryStage, userData, httpRequest, language));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        primaryStage.toFront();
    }
}
