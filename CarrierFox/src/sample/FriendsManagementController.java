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
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class FriendsManagementController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;

    @FXML
    private VBox friendsList;

    @FXML
    private TextField addFriendField;
    @FXML
    private Button addFriendButton;

    public Group initializeFriendsManagementPage(Stage primaryStage, UserData userData) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/FriendManagement.fxml"));
        HTTPRequest httpRequest = new HTTPRequest();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();
        AnchorPane friendsListAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        ScrollPane scrollPane = (ScrollPane) friendsListAnchorPane.getChildren().get(0);
        AnchorPane anchorPane = (AnchorPane) scrollPane.getContent();

        friendsList = (VBox) anchorPane.getChildren().get(0);

        AnchorPane addFriendAnchor = (AnchorPane) root.getChildrenUnmodifiable().get(1);
        addFriendField = (TextField) addFriendAnchor.getChildren().get(0);
        addFriendButton = (Button) addFriendAnchor.getChildren().get(1);

        List<Friend> friends = httpRequest.getFriends(userData.getUsername(), userData.get_id());

        if (friends != null)
        {
            updateFriendsList(friends);
        }

        this.primaryStage = primaryStage;
        this.userData = userData;

        return new Group(root);
    }

    private void updateFriendsList(List<Friend> friends) throws IOException
    {
        friendsList.getChildren().clear();
        for (int i = 0; i < friends.size(); i++)
        {
            FXMLLoader friendAnchorPaneLoader = new FXMLLoader(getClass().getResource("resources/friendAnchorPane.fxml"));

            AnchorPane friend = friendAnchorPaneLoader.load();
            friendsList.getChildren().add(friend);
            Button deleteFriend = (Button) friend.getChildren().get(1);
            Text friendName = (Text) friend.getChildren().get(0);

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
        HTTPRequest httpRequest = new HTTPRequest();

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
        HTTPRequest httpRequest = new HTTPRequest();

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
            scene = new Scene(defaultPageController.initializeDefaultPage(primaryStage, userData));
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
