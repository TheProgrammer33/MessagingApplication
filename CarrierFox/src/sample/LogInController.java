package sample;

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

public class LogInController extends AnchorPane
{
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Stage primaryStage;

    public Group initializeLogInPage(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/LoginPage.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        ImageView carrierFoxImage = (ImageView) root.getChildrenUnmodifiable().get(0);
        Text invalidUserOrPass = (Text) root.getChildrenUnmodifiable().get(5);

        setUsername((TextField) root.getChildrenUnmodifiable().get(1));
        setPassword((PasswordField) root.getChildrenUnmodifiable().get(2));

        setPrimaryStage(primaryStage);

        invalidUserOrPass.setVisible(false);
        carrierFoxImage.setImage(new Image(new FileInputStream("src/sample/resources/CarrierFox128x1.png")));

        return new Group(root);
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

    public boolean checkUserAndPass()
    {
        HTTPRequest httpRequest = new HTTPRequest();

        return httpRequest.login(username.getText(), password.getText());
    }

    @FXML
    protected void toggleMessageBoxMenu()
    {
        if (!checkUserAndPass())
        {
            return;
        }

        MessageBoxController messageBoxController = new MessageBoxController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(messageBoxController.initializeMessageBoxPage(primaryStage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();
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
