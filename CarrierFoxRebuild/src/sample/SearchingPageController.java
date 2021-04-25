package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchingPageController extends AnchorPane
{
    private UserData userData;
    private HTTPRequest httpRequest;
    private Language language;
    private List<Message> decryptedMessages;

    private Stage primaryStage;

    private TextField searchField;
    private Text results;
    private VBox messageResultsBox;

    private TextField messagingTextField;

    public Group initializePage(Stage primaryStage, UserData userData, HTTPRequest httpRequest, Language language) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/DefaultPageWithSearching.fxml"));

        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        Parent root = fxmlLoader.load();

        setPrimaryStage(primaryStage);
        setHttpRequest(httpRequest);
        setUserData(userData);
        setLanguage(language);
        setDecryptedMessages(httpRequest.getCurrentThreadMessages());

        AnchorPane messagesPane = (AnchorPane) root.getChildrenUnmodifiable().get(1);
        httpRequest.updateMessageBox((ScrollPane) messagesPane.getChildren().get(0));

        setMessagingTextField((TextField) root.getChildrenUnmodifiable().get(2));

        AnchorPane usernamePane = (AnchorPane) root.getChildrenUnmodifiable().get(0);
        Text username = (Text) usernamePane.getChildren().get(0);
        username.setText(userData.getUsername());

        HBox buttonsHBox = (HBox) root.getChildrenUnmodifiable().get(5);

        Image friendsIcon = new Image(String.valueOf(this.getClass().getResource("resources/friendsx2.png")));
        Button friendsButton = (Button) buttonsHBox.getChildren().get(0);
        buildImageButton(friendsButton, friendsIcon);

        Image searchIcon = new Image(String.valueOf(this.getClass().getResource("resources/searchx2.png")));
        Button searchButton = (Button) buttonsHBox.getChildren().get(1);
        buildImageButton(searchButton, searchIcon);

        Image chatSettingsIcon = new Image(String.valueOf(this.getClass().getResource("resources/meatballMenux2.png")));
        Button chatSettingsButton = (Button) buttonsHBox.getChildren().get(2);
        buildImageButton(chatSettingsButton, chatSettingsIcon);

        Image settingsIcon = new Image(String.valueOf(this.getClass().getResource("resources/settingsx2.png")));
        Button settingsButton = (Button) buttonsHBox.getChildren().get(3);
        buildImageButton(settingsButton, settingsIcon);

        AnchorPane searchPane = (AnchorPane) root.getChildrenUnmodifiable().get(3);

        AnchorPane resultMessagesPane = (AnchorPane) searchPane.getChildren().get(0);
        ScrollPane messagesScrollPane = (ScrollPane) resultMessagesPane.getChildren().get(0);
        setMessageResultsBox((VBox) messagesScrollPane.getContent());

        HBox searchHBox = (HBox) searchPane.getChildren().get(1);
        setSearchField((TextField) searchHBox.getChildren().get(0));

        HBox resultsHBox = (HBox) searchPane.getChildren().get(2);
        setResults((Text) resultsHBox.getChildren().get(0));

        httpRequest.refreshMessagesBox();

        syncLanguage(root);

        return new Group(root);
    }

    private void buildImageButton(Button button, Image image)
    {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(20.0);
        imageView.setPreserveRatio(true);

        button.setText("");
        button.setGraphic(imageView);
    }

    private void syncLanguage(Parent root)
    {
        Locale locale = new Locale(language.getLanguage(), language.getCountry());

        ResourceBundle defaultResourceBundle = ResourceBundle.getBundle("sample/resources/resourcebundles/DefaultPage", locale);

        messagingTextField.setPromptText(defaultResourceBundle.getString("textPromtText"));

        HBox sendButtonHBox = (HBox) root.getChildrenUnmodifiable().get(4);
        Button sendButton = (Button) sendButtonHBox.getChildren().get(0);
        sendButton.setText(defaultResourceBundle.getString("send"));

        AnchorPane searchAnchorPane = (AnchorPane) root.getChildrenUnmodifiable().get(3);

        HBox resultsHBox = (HBox) searchAnchorPane.getChildren().get(2);
        Text resultsText = (Text) resultsHBox.getChildren().get(0);
        resultsText.setText(defaultResourceBundle.getString("results"));
    }

    private Parent buildSearchMessageTemplate() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("resources/searchMessageTemplate.fxml"));

        return fxmlLoader.load();
    }

    @FXML
    public void searchMessages() throws IOException, ParseException
    {
        List<Message> resultsList = new ArrayList<>();
        String searchTerm = this.searchField.getText();

        messageResultsBox.getChildren().clear();

        for (Message message : decryptedMessages)
        {
            if (message.getMessageBody().contains(searchTerm))
            {
                resultsList.add(message);
            }
        }

        results.setText(resultsList.size() + " Results");

        for (int j = resultsList.size() - 1; j > 0; j--)
        {
            Message message = resultsList.get(j);
            Parent searchMessageBox = buildSearchMessageTemplate();

            Button bottomBox = (Button) searchMessageBox.getChildrenUnmodifiable().get(0);
            Text textContext = (Text) searchMessageBox.getChildrenUnmodifiable().get(1);

            HBox userDateHBox = (HBox) searchMessageBox.getChildrenUnmodifiable().get(2);
            Text username = (Text) userDateHBox.getChildren().get(0);
            Text date = (Text) userDateHBox.getChildren().get(1);

            username.setText(message.getUser());

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String dateString = dateFormat.format(message.getMessageSentDate());
            date.setText(dateString);

            if (message.getMessageBody().contains("\n"))
            {
                message.setMessageBody(message.getMessageBody().replace("\n", ""));
            }
            textContext.setText(message.getMessageBody());

            VBox newLineBox = (VBox) bottomBox.getGraphic();
            int approximateSize = (message.getMessageBody().length() / 28) + 3;

            System.out.println("Approximate size: " + approximateSize);

            for (int i = 0; i < approximateSize; i++)
            {
                Label newLineLabel = new Label("");

                newLineBox.getChildren().add(newLineLabel);
            }

            messageResultsBox.getChildren().add(searchMessageBox);
        }
    }

    @FXML
    public void initializeSettingsPage()
    {
        SettingsPageController settingsPageController = new SettingsPageController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(settingsPageController.initializeSettingsPage(primaryStage, userData, httpRequest, language));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        primaryStage.toFront();
    }

    @FXML
    public void initializeFriendsPage()
    {
        FriendsManagementController friendsManagementController = new FriendsManagementController();

        this.primaryStage.close();

        Scene scene = null;
        try {
            scene = new Scene(friendsManagementController.initializeFriendsManagementPage(primaryStage, userData, httpRequest, language));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        primaryStage.setAlwaysOnTop(true);
        primaryStage.setAlwaysOnTop(false);

        primaryStage.toFront();
    }

    @FXML
    private void initializeSearchingPage()
    {
        SearchingPageController searchingPageController = new SearchingPageController();

        Scene newScene = null;
        try
        {
            newScene = new Scene(searchingPageController.initializePage(primaryStage, userData, httpRequest, language));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        primaryStage.setScene(newScene);
    }

    @FXML
    public void initializeChatSettings() throws IOException
    {
        MenuPageController menuPageController = new MenuPageController();

        Scene newScene = new Scene(menuPageController.initializePage(primaryStage, userData, httpRequest, language));

        primaryStage.setScene(newScene);
    }

    @FXML
    public void sendMessageOnClick() throws IOException
    {
        int currentThreadId = httpRequest.getThreadId();

        if (messagingTextField.getText().compareTo("") == 0)
        {
            return;
        }

        if (currentThreadId == -1)
            return;

        String encryptedMessage = encryptMessage(messagingTextField.getText());

        this.httpRequest.sendMessage(encryptedMessage, String.valueOf(currentThreadId), userData.getUsername());

        messagingTextField.setText("");

        httpRequest.refreshMessagesBox();
    }

    public String encryptMessage(String message)
    {
        Encyption encryptionHandler = new Encyption();

        try
        {
            return encryptionHandler.encrypt(message);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }

    private void setPrimaryStage(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
    }

    public void setUserData(UserData userData)
    {
        this.userData = userData;
    }

    public void setHttpRequest(HTTPRequest httpRequest)
    {
        this.httpRequest = httpRequest;
    }

    public void setMessagingTextField(TextField messagingTextField)
    {
        this.messagingTextField = messagingTextField;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public void setDecryptedMessages(List<Message> decryptedMessages)
    {
        this.decryptedMessages = decryptedMessages;
    }

    public void setSearchField(TextField searchField)
    {
        this.searchField = searchField;
    }

    public void setMessageResultsBox(VBox messageResultsBox)
    {
        this.messageResultsBox = messageResultsBox;
    }

    public void setResults(Text results)
    {
        this.results = results;
    }
}
