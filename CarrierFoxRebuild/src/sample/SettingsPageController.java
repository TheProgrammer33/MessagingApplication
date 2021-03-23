package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public class SettingsPageController extends AnchorPane
{
    @FXML
    private Stage primaryStage;

    private UserData userData;
    private HTTPRequest httpRequest;
    private Language language;

    @FXML
    private Parent root;

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

    public Group initializeSettingsPage(Stage primaryStage, UserData userData, HTTPRequest httpRequest, Language language) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("resources/Settings.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        AnchorPane root = fxmlLoader.load();
        AnchorPane passwordAnchorPane = (AnchorPane) root.getChildren().get(1);
        AnchorPane emailAnchorPane = (AnchorPane) root.getChildren().get(2);
        AnchorPane notifications = (AnchorPane) root.getChildren().get(3);

        HBox oldPasswordHBox = (HBox) passwordAnchorPane.getChildren().get(1);
        oldPasswordField = (PasswordField) oldPasswordHBox.getChildren().get(0);

        HBox newPasswordHBox = (HBox) passwordAnchorPane.getChildren().get(2);
        newPasswordField = (PasswordField) newPasswordHBox.getChildren().get(0);

        HBox confirmPasswordHBox = (HBox) passwordAnchorPane.getChildren().get(3);
        confirmNewPasswordField = (PasswordField) confirmPasswordHBox.getChildren().get(0);

        HBox oldPasswordErrorHBox = (HBox) passwordAnchorPane.getChildren().get(4);
        oldPasswordError = (Text) oldPasswordErrorHBox.getChildren().get(0);

        HBox newPasswordErrorHBox = (HBox) passwordAnchorPane.getChildren().get(5);
        newPasswordsMatchError = (Text) newPasswordErrorHBox.getChildren().get(0);

        HBox newEmailFieldHBox = (HBox) emailAnchorPane.getChildren().get(1);
        newEmailField = (TextField) newEmailFieldHBox.getChildren().get(0);

        HBox newEmailErrorHBox = (HBox) emailAnchorPane.getChildren().get(2);
        newEmailError = (Text) newEmailErrorHBox.getChildren().get(0);

        HBox notificationToggleHBox = (HBox) notifications.getChildren().get(1);
        notificationsOn = (RadioButton) notificationToggleHBox.getChildren().get(0);
        notificationsOff = (RadioButton) notificationToggleHBox.getChildren().get(1);

        HBox englishFrenchHBox = (HBox) notifications.getChildren().get(9);
        HBox germanSpanishHBox = (HBox) notifications.getChildren().get(10);

        englishLanguage = (RadioButton) englishFrenchHBox.getChildren().get(0);
        frenchLanguage = (RadioButton) englishFrenchHBox.getChildren().get(1);

        germanLanguage = (RadioButton) germanSpanishHBox.getChildren().get(0);
        spanishLanguage = (RadioButton) germanSpanishHBox.getChildren().get(1);

        if (language.getLanguage().equals("en"))
        {
            englishLanguage.fire();
        }
        else if (language.getCountry().equals("fr"))
        {
            frenchLanguage.fire();
        }
        else if (language.getLanguage().equals("de"))
        {
            germanLanguage.fire();
        }
        else
        {
            spanishLanguage.fire();
        }

        this.primaryStage = primaryStage;
        this.userData = userData;
        this.httpRequest = httpRequest;
        this.language = language;
        this.root = root;

        if (userData.getNotificationSettings())
        {
            notificationsOn.fire();
        }
        else
        {
            notificationsOff.fire();
        }

        syncLanguage(root);

        return new Group(root);
    }

    private void syncLanguage(Parent root)
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle settingsResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/SettingsPage", locale);

        HBox backButtonHBox = (HBox) root.getChildrenUnmodifiable().get(0);
        Button backButton = (Button) backButtonHBox.getChildren().get(0);
        backButton.setText(settingsResourceBundle.getString("back"));

        AnchorPane passwordAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);

        HBox changePasswordTextHBox = (HBox) passwordAnchorPane.getChildren().get(0);
        Text changePasswordText = (Text) changePasswordTextHBox.getChildren().get(0);
        changePasswordText.setText(settingsResourceBundle.getString("changePassword"));

        oldPasswordField.setPromptText(settingsResourceBundle.getString("oldPasswordPrompt"));
        oldPasswordError.setText(""); //FIXME: add translation

        newPasswordField.setPromptText(settingsResourceBundle.getString("newPasswordPrompt"));
        confirmNewPasswordField.setPromptText(settingsResourceBundle.getString("confirmNewPasswordPrompt"));

        HBox changePasswordButtonHBox = (HBox) passwordAnchorPane.getChildren().get(6);
        Button changePasswordButton = (Button) changePasswordButtonHBox.getChildren().get(0);
        changePasswordButton.setText(settingsResourceBundle.getString("changePassword"));

        AnchorPane changeEmailAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(2);

        HBox changeEmailTextHBox = (HBox) changeEmailAnchorPane.getChildren().get(0);
        Text changeEmailText = (Text) changeEmailTextHBox.getChildren().get(0);
        changeEmailText.setText(settingsResourceBundle.getString("changeEmail"));

        newEmailField.setPromptText(settingsResourceBundle.getString("newEmail"));
        newEmailError.setText(""); //FIXME: add translation

        HBox changeEmailButtonHBox = (HBox) changeEmailAnchorPane.getChildren().get(3);
        Button changeEmailButton = (Button) changeEmailButtonHBox.getChildren().get(0);
        changeEmailButton.setText(settingsResourceBundle.getString("changeEmail"));

        AnchorPane notificationsAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(3);

        HBox notificationSettingsHBox = (HBox) notificationsAnchorPane.getChildren().get(0);
        Text notificationSettingsText = (Text) notificationSettingsHBox.getChildren().get(0);
        notificationSettingsText.setText(settingsResourceBundle.getString("notificationSettings"));

        HBox sendNotificationHBox = (HBox) notificationsAnchorPane.getChildren().get(3);
        Text sendNotificationsText = (Text) sendNotificationHBox.getChildren().get(0);
        sendNotificationsText.setText(settingsResourceBundle.getString("sendNotifications"));

        notificationsOn.setText(settingsResourceBundle.getString("on"));
        notificationsOff.setText(settingsResourceBundle.getString("off"));

        HBox languageTextHBox = (HBox) notificationsAnchorPane.getChildren().get(8);
        Text languageText = (Text) languageTextHBox.getChildren().get(0);
        languageText.setText(settingsResourceBundle.getString("language"));

        HBox logoutTextHBox = (HBox) notificationsAnchorPane.getChildren().get(5);
        Text logoutText = (Text) logoutTextHBox.getChildren().get(0);
        logoutText.setText(settingsResourceBundle.getString("Logout"));

        HBox logoutButtonHBox = (HBox) notificationsAnchorPane.getChildren().get(6);
        Button logoutButton = (Button) logoutButtonHBox.getChildren().get(0);
        logoutButton.setText(settingsResourceBundle.getString("Logout"));
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

    private void saveLanguage() throws IOException
    {
        FileOutputStream fileOutputStream = new FileOutputStream("src/sample/resources/languageSettings.json");

        ToggleGroup languageToggleGroup = englishLanguage.getToggleGroup();

        RadioButton selectedRadioButton = (RadioButton) languageToggleGroup.getSelectedToggle();

        String jsonString = "";

        if (selectedRadioButton == englishLanguage)
        {
            jsonString = "{\n" +
                    "  \"language\": \"en\",\n" +
                    "  \"country\": \"US\"\n" +
                    "}";

            language.setLanguage("en");
            language.setCountry("US");
        }
        else if (selectedRadioButton == frenchLanguage)
        {
            jsonString = "{\n" +
                    "  \"language\": \"fr\",\n" +
                    "  \"country\": \"FR\"\n" +
                    "}";

            language.setLanguage("fr");
            language.setCountry("FR");
        }
        else if (selectedRadioButton == germanLanguage)
        {
            jsonString = "{\n" +
                    "  \"language\": \"de\",\n" +
                    "  \"country\": \"DE\"\n" +
                    "}";

            language.setLanguage("de");
            language.setCountry("DE");
        }
        else if (selectedRadioButton == spanishLanguage)
        {
            jsonString = "{\n" +
                    "  \"language\": \"es\",\n" +
                    "  \"country\": \"419\"\n" +
                    "}";

            language.setLanguage("es");
            language.setCountry("419");
        }

        fileOutputStream.write(jsonString.getBytes(StandardCharsets.UTF_8));

        syncLanguage(root);
    }

    private void saveSettings() throws IOException
    {
        boolean notificationSettings = getCurrentNotificationSetting();
        String languageSetting = getCurrentLanguage();
        saveLanguage();

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
    public void returnToDefaultPage() throws IOException
    {
        saveSettings();

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
