package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GroupChatController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;
    private HTTPRequest httpRequest;

    public Group initializeGroupChatPage(Stage primaryStage, UserData userData) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/groupFriendPage.fxml"));
        HTTPRequest httpRequest = new HTTPRequest();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        this.primaryStage = primaryStage;
        this.userData = userData;
        this.httpRequest = httpRequest;

        return new Group(root);
    }

    @FXML
    public void initializeDefaultPage()
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
