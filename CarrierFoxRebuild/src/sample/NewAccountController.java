package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class NewAccountController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    @FXML
    private TextField username;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Text passwordError;
    @FXML
    private Text usernameError;
    @FXML
    private Text emailError;
    @FXML
    private ImageView carrierFoxImage;

    public Group initializeNewAccountPage(Stage primaryStage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/NewAccount.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        setPrimaryStage(primaryStage);

        Parent root = fxmlLoader.load();

        carrierFoxImage = (ImageView) root.getChildrenUnmodifiable().get(5);

        username = (TextField) root.getChildrenUnmodifiable().get(0);
        email = (TextField) root.getChildrenUnmodifiable().get(1);
        password = (PasswordField) root.getChildrenUnmodifiable().get(2);
        confirmPassword = (PasswordField) root.getChildrenUnmodifiable().get(3);

        usernameError = (Text) root.getChildrenUnmodifiable().get(6);
        emailError = (Text) root.getChildrenUnmodifiable().get(7);
        passwordError = (Text) root.getChildrenUnmodifiable().get(8);

        carrierFoxImage.setImage(new Image(new FileInputStream("src/sample/resources/CarrierFox128x1.png")));

        return new Group(root);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    private Boolean isEmpty(PasswordField passwordField)
    {
        if (passwordField.getText().isEmpty())
            return true;
        else
            return false;
    }

    @FXML
    public void validatePasswords()
    {
        if (isEmpty(password) || isEmpty(confirmPassword))
        {
            return;
        }

        if (password.getText().equals(confirmPassword.getText()))
        {
            passwordError.setVisible(false);
        }
        else
        {
            passwordError.setVisible(true);
        }
    }

    @FXML
    public void toggleLogInMenu()
    {
        HTTPRequest httpRequest = new HTTPRequest();

        usernameError.setVisible(false);
        emailError.setVisible(false);

        try
        {
            httpRequest.createAccount(email.getText(), username.getText(), password.getText());
        }
        catch (HTTPException e)
        {
            if (e.getMessage().compareTo("Username already exists") == 0)
                usernameError.setVisible(true);
            else if (e.getMessage().compareTo("Email already exists") == 0)
                emailError.setVisible(true);
            else
                e.printStackTrace();

            return;
        }

        LogInController logInController = new LogInController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(logInController.initializeLogInPage(primaryStage));
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
