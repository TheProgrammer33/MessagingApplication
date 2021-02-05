package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class NewChatController extends AnchorPane
{
    @FXML
    private Stage secondaryWindowStage;

    private Parent root;
    private VBox friendsVbox;
    private Button createChatButton;

    private UserData userData;
    private HTTPRequest httpRequest;

    public Group initializeGroupChatPage(Stage secondaryWindowStage, UserData userData) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/groupFriendPage.fxml"));
        HTTPRequest httpRequest = new HTTPRequest();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        this.secondaryWindowStage = secondaryWindowStage;

        this.root = root;

        AnchorPane friendsAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(2);
        ScrollPane friendsScrollPane = (ScrollPane) friendsAnchorPane.getChildren().get(0);
        this.friendsVbox = (VBox) friendsScrollPane.getContent();

        this.createChatButton = (Button) root.getChildrenUnmodifiable().get(3);

        this.userData = userData;
        this.httpRequest = httpRequest;

        updateFriendsList();

        return new Group(root);
    }

    private void updateFriendsList() throws IOException
    {
        List<Friend> friendList = httpRequest.getFriends(userData.getUsername(), userData.get_id());

        friendsVbox.getChildren().clear();

        for (Friend friend: friendList)
        {
            FXMLLoader friendAnchorPaneLoader = new FXMLLoader(getClass().getResource("resources/groupFriendAddFriendBox.fxml"));

            AnchorPane friendAnchor = friendAnchorPaneLoader.load();
            friendsVbox.getChildren().add(friendAnchor);
            Text friendName = (Text) friendAnchor.getChildren().get(0);

            friendName.setText(friend.getUsername());
        }
    }

    @FXML
    public void returnToDefaultPage()
    {
        this.secondaryWindowStage.close();
    }

    public Parent getRoot()
    {
        return root;
    }

    public VBox getFriendsVbox()
    {
        return friendsVbox;
    }

    public Button getCreateChatButton()
    {
        return createChatButton;
    }
}
