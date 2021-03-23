package sample;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

public class LogInController extends AnchorPane
{
    private UserData userData;
    private HTTPRequest httpRequest;
    private Language language;

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
        HTTPRequest httpRequest = new HTTPRequest();

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        ImageView carrierFoxImage = (ImageView) root.getChildrenUnmodifiable().get(0);

        HBox errorHBox = (HBox) root.getChildrenUnmodifiable().get(5);
        invalidUserOrPass = (Text) errorHBox.getChildren().get(0);

        setUsername((TextField) root.getChildrenUnmodifiable().get(1));
        setPassword((PasswordField) root.getChildrenUnmodifiable().get(2));

        HBox submitHBox = (HBox) root.getChildrenUnmodifiable().get(4);
        submit = (Button) submitHBox.getChildren().get(0);

        setPrimaryStage(primaryStage);
        setHttpRequest(httpRequest);

        invalidUserOrPass.setVisible(false);
        carrierFoxImage.setImage(new Image(new FileInputStream("src/sample/resources/CarrierFox128x1.png")));

        syncLanguage(root);

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

    private void syncLanguage(Parent root) throws IOException
    {
        this.language = loadLanguage();

        Locale locale = new Locale(language.getLanguage(), language.getCountry());
        ResourceBundle loginResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/LoginPage", locale);

        username.setPromptText(loginResourceBundle.getString("usernamePromptText"));

        password.setPromptText(loginResourceBundle.getString("passwordPromptText"));

        HBox resetPasswordCreateAccountHBox = (HBox) root.getChildrenUnmodifiable().get(3);

        Button resetPassword = (Button) resetPasswordCreateAccountHBox.getChildren().get(0);
        resetPassword.setText(loginResourceBundle.getString("resetPassword"));

        invalidUserOrPass.setText(loginResourceBundle.getString("errorText"));

        Button createAccount = (Button) resetPasswordCreateAccountHBox.getChildren().get(1);
        createAccount.setText(loginResourceBundle.getString("createAccount"));

        submit.setText(loginResourceBundle.getString("submit"));
    }

    private Language loadLanguage() throws IOException
    {
        FileInputStream fileInputStream = new FileInputStream("src/sample/resources/languageSettings.json");

        String jsonString = new String(fileInputStream.readAllBytes(), StandardCharsets.UTF_8);

        Type languageDataType = new TypeToken<Language>(){}.getType();

        return new Gson().fromJson(jsonString, languageDataType);
    }

    public void loginAndLoadUserData() throws HTTPException
    {
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
            scene = new Scene(defaultPageController.initializeDefaultPage(primaryStage, userData, httpRequest, language));
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

    public void setHttpRequest(HTTPRequest httpRequest)
    {
        this.httpRequest = httpRequest;
    }
}
