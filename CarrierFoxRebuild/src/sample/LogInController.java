package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.HashMap;

public class LogInController extends AnchorPane
{
    private UserData userData;

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button submit;

    @FXML
    private Text invalidUserOrPass;

    @FXML
    private Stage primaryStage;

    public Group initializeLogInPage(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/LoginPage.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        ImageView carrierFoxImage = (ImageView) root.getChildrenUnmodifiable().get(0);
        invalidUserOrPass = (Text) root.getChildrenUnmodifiable().get(5);

        setUsername((TextField) root.getChildrenUnmodifiable().get(1));
        setPassword((PasswordField) root.getChildrenUnmodifiable().get(2));
        submit = (Button) root.getChildrenUnmodifiable().get(3);

        setPrimaryStage(primaryStage);

        invalidUserOrPass.setVisible(false);
        carrierFoxImage.setImage(new Image(new FileInputStream("src/sample/resources/CarrierFox128x1.png")));

        return new Group(root);
    }

    @FXML
    public void passwordSubmit()
    {
        submit.fire();
    }

    public void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public void setUsername(TextField username)
    {
        this.username = username;
    }

    public void setPassword(TextField password)
    {
        this.password = password;
    }

    public void loginAndLoadUserData() throws HTTPException
    {
        HTTPRequest httpRequest = new HTTPRequest();

        userData = httpRequest.login(username.getText(), password.getText());
    }

    @FXML
    protected void toggleMessageBoxMenu()
    {
        invalidUserOrPass.setVisible(false);

        try
        {
            loginAndLoadUserData();
        }
        catch (HTTPException e)
        {
            invalidUserOrPass.setVisible(true);
            return;
        }

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

        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {
                defaultPageController.updateMessageBox(defaultPageController.getCurrentThreadId());
            }
        });
    }

    @FXML
    protected void toggleCreateNewAccountMenu()
    {
        NewAccountController newAccountController = new NewAccountController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(newAccountController.initializeNewAccountPage(primaryStage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();
    }
}
