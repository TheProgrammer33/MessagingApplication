package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class SettingsPageController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;

    @FXML
    private PasswordField oldPasswordField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private Text oldPasswordError;
    @FXML
    private Text newPasswordsMatchError;

    @FXML
    private TextField newEmailField;
    @FXML
    private Text newEmailError;

    @FXML
    private RadioButton notificationsOn;
    @FXML
    private RadioButton notificationsOff;

    @FXML
    private RadioButton englishLanguage;
    @FXML
    private RadioButton frenchLanguage;
    @FXML
    private RadioButton germanLanguage;
    @FXML
    private RadioButton spanishLanguage;

    public Group initializeSettingsPage(Stage primaryStage, UserData userData) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/Settings.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        AnchorPane root = fxmlLoader.load();
        AnchorPane passwordAnchorPane = (AnchorPane) root.getChildren().get(1);
        AnchorPane emailAnchorPane = (AnchorPane) root.getChildren().get(2);
        AnchorPane notifications = (AnchorPane) root.getChildren().get(3);

        oldPasswordField = (PasswordField) passwordAnchorPane.getChildren().get(1);
        newPasswordField = (PasswordField) passwordAnchorPane.getChildren().get(2);
        confirmNewPasswordField = (PasswordField) passwordAnchorPane.getChildren().get(3);

        oldPasswordError = (Text) passwordAnchorPane.getChildren().get(4);
        newPasswordsMatchError = (Text) passwordAnchorPane.getChildren().get(5);

        newEmailField = (TextField) emailAnchorPane.getChildren().get(1);
        newEmailError = (Text) emailAnchorPane.getChildren().get(2);

        notificationsOn = (RadioButton) notifications.getChildren().get(1);
        notificationsOff = (RadioButton) notifications.getChildren().get(2);

        englishLanguage = (RadioButton) notifications.getChildren().get(9);
        frenchLanguage = (RadioButton) notifications.getChildren().get(10);
        germanLanguage = (RadioButton) notifications.getChildren().get(11);
        spanishLanguage = (RadioButton) notifications.getChildren().get(12);

        HTTPRequest httpRequest = new HTTPRequest();

        if (userData.getNotificationSettings())
        {
            notificationsOn.fire();
        }
        else
        {
            notificationsOff.fire();
        }

        this.primaryStage = primaryStage;
        this.userData = userData;

        return new Group(root);
    }

    @FXML
    public void changeEmail()
    {
        if (!validateEmailAddress(newEmailField.getText()))
        {
            newEmailError.setVisible(true);
            return;
        }

        HTTPRequest httpRequest = new HTTPRequest();

        try
        {
            httpRequest.changeEmail(newEmailField.getText(), this.userData.get_id());
        }
        catch (HTTPException e)
        {
            e.printStackTrace();
            newEmailError.setVisible(true);
        }

    }

    private boolean validateEmailAddress(String emailAddress)
    {
        if (!(emailAddress.contains("@") && emailAddress.contains(".")))
            return false;
        else
            return true;
    }

    @FXML
    public void submitPassword()
    {
        oldPasswordError.setVisible(false);
        newPasswordsMatchError.setVisible(false);

        if (!compareNewPasswords(newPasswordField.getText(), confirmNewPasswordField.getText()))
        {
            newPasswordsMatchError.setVisible(true);
            return;
        }

        HTTPRequest httpRequest = new HTTPRequest();

        try
        {
            httpRequest.changePassword(oldPasswordField.getText(), newPasswordField.getText(), this.userData.get_id());
        }
        catch (HTTPException e)
        {
            e.printStackTrace();
            oldPasswordError.setVisible(true);
        }
    }

    @FXML
    public void checkPasswords()
    {
        if (!compareNewPasswords(newPasswordField.getText(), confirmNewPasswordField.getText()))
        {
            newPasswordsMatchError.setVisible(true);
        }
        else
            newPasswordsMatchError.setVisible(false);
    }

    private boolean compareNewPasswords(String password, String confirmPassword)
    {
        if (password.compareTo(confirmPassword) == 0)
            return true;
        else
            return false;
    }

    private String getCurrentLanguage()
    {
        ToggleGroup languageToggleGroup = englishLanguage.getToggleGroup();

        RadioButton selectedLanguage = (RadioButton) languageToggleGroup.getSelectedToggle();

        return selectedLanguage.getText();
    }

    private Boolean getCurrentNotificationSetting()
    {
        ToggleGroup selectedToggleGroup = notificationsOn.getToggleGroup();

        RadioButton selectedNotificationSetting = (RadioButton) selectedToggleGroup.getSelectedToggle();

        if (selectedNotificationSetting == notificationsOn)
            return true;
        else
            return false;
    }

    private void saveSettings()
    {
        boolean notificationSettings = getCurrentNotificationSetting();
        String languageSetting = getCurrentLanguage();

        this.userData.setNotificationSettings(notificationSettings);
        this.userData.setLanguage(languageSetting);

        HTTPRequest httpRequest = new HTTPRequest();

        httpRequest.saveSettings(notificationSettings, this.userData.get_id());
    }

    @FXML
    public void logout()
    {
        LogInController logInController = new LogInController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(logInController.initializeLogInPage(this.primaryStage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();
    }

    @FXML
    public void returnToDefaultPage()
    {
        saveSettings();

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
