package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;


public class HTTPRequest
{
    private WebSocketController chatClient;
    private WebSocketThread websocketThread;
    private int threadId;

    public HTTPRequest(int threadId)
    {
        this.threadId = threadId;
    }

    public HTTPRequest() {}

    public void openWebSocket(ScrollPane messagesScrollPane, Text stringTextWidth, UserData userData, int threadId)
    {
        try
        {
            WebSocketController chatclient = new WebSocketController(new URI("wss://catherinegallaher.com/"), messagesScrollPane, stringTextWidth, userData, threadId);

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            chatclient.setSocketFactory(factory);

            chatclient.connectBlocking();

            this.chatClient = chatclient;

            websocketThread = new WebSocketThread(chatclient);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void updateMessageBox(ScrollPane messagesScrollPane)
    {
        chatClient.setMessagesScrollPane(messagesScrollPane);
    }

    public void refreshMessagesBox() throws IOException
    {
        chatClient.updateMessageBox();
    }

    public void closeWebSocket()
    {
        websocketThread.stop();
    }

    public List<Message> getCurrentThreadMessages()
    {
        return chatClient.getDecryptedMessages();
    }

    public void changeThread(int threadId)
    {
        chatClient.changeThread(threadId);
        this.threadId = threadId;
    }

    public void sendMessage(String message, String id, String username)
    {
        this.chatClient.send("{\"message\": \"" + message + "\", \"threadId\": " + id + ", \"user\": \"" + username + "\"" + ", \"type\": \"newMessage\"}");
    }

    public List<Message> getMessages(int threadId)
    {
        String url = "https://catherinegallaher.com/api/thread/" + threadId + "/messages/";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        String fullResponse = sendHTTPReq(paramTypes, params, url, "GET");

        Type messages = new TypeToken<ArrayList<Message>>(){}.getType();

        List<Message> messageList = new Gson().fromJson(fullResponse, messages);

        if (messageList == null)
            return null;

        for (int i = 0; i < messageList.size(); i++)
        {
            Message message = messageList.get(i);
            String messageBody = message.getMessageBody();
            int lengthOfMessageBody = messageBody.length();

            if (lengthOfMessageBody > 20)
            {
                for (int j = lengthOfMessageBody - 20; j > 0; j--)
                {
                    if (messageBody.charAt(j) == ' ')
                    {
                        message.setMessageBody(messageBody.substring(0, j) + '\n' + messageBody.substring(j));
                        break;
                    }
                }
            }
        }

        return messageList;
    }

    public UserData login(String username, String password)
    {
        String url = "https://catherinegallaher.com/api/login/";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("username");
        params.add(username);

        paramTypes.add("password");
        params.add(password);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        System.out.println(fullResponse);

        Type userDataType = new TypeToken<UserData>(){}.getType();

        return new Gson().fromJson(fullResponse, userDataType);
    }

    public void createAccount(String email, String username, String password)
    {
        String url = "https://catherinegallaher.com/api/create-account";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("email");
        params.add(email);

        paramTypes.add("username");
        params.add(username);

        paramTypes.add("password");
        params.add(password);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        if (fullResponse.compareTo("{}") != 0)
        {
            throw new HTTPException(fullResponse);
        }
    }

    public List<Friend> getFriends(String username, String sessionId)
    {
        String url = "https://catherinegallaher.com/api/friends";

        List<String> parameterTypes = new ArrayList<>(), parameters = new ArrayList<>();
        parameterTypes.add("sessionId");
        parameters.add(sessionId);

        //String response = sendHTTPReq(paramterTypes, parameters, URL);

        String body = "";

        body = "sessionId=" + sessionId;

        byte[] postData = body.getBytes(StandardCharsets.UTF_8);

        try
        {
            URL connection = new URL(url);
            StringBuilder response = new StringBuilder();

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            try (DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream()))
            {
                wr.write(postData);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            httpURLConnection.disconnect();

            String fullResponse = response.toString();
            fullResponse = fullResponse.replace("{\"friends\":", "");
            fullResponse = fullResponse.substring(0, fullResponse.length() - 1);

            System.out.println(fullResponse);

            return new Gson().fromJson(fullResponse, new TypeToken<ArrayList<Friend>>(){}.getType());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public void addFriend(String friendUsername, String userSessionId)
    {
        String url = "https://catherinegallaher.com/api/add-friend";
        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("username");
        params.add(friendUsername);

        paramTypes.add("sessionId");
        params.add(userSessionId);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        if (fullResponse.compareTo("{}") != 0)
        {
            throw new HTTPException(fullResponse);
        }
    }

    public void removeFriend(String friendUsername, String userSessionId)
    {
        String url = "https://catherinegallaher.com/api/remove-friend";
        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("username");
        params.add(friendUsername);

        paramTypes.add("sessionId");
        params.add(userSessionId);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "PUT");

        if (fullResponse.compareTo("{}") != 0)
        {
            throw new HTTPException(fullResponse);
        }
    }

    public void changeEmail(String newEmail, String userSessionId)
    {
        String url = "https://catherinegallaher.com/api/change-email";
        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("newEmail");
        params.add(newEmail);

        paramTypes.add("sessionId");
        params.add(userSessionId);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        if (fullResponse.compareTo("{}") != 0)
        {
            throw new HTTPException(fullResponse);
        }
    }

    public void changePassword(String oldPassword, String newPassword, String userSessionId)
    {
        String url = "https://catherinegallaher.com/api/change-password";
        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("password");
        params.add(oldPassword);

        paramTypes.add("newPassword");
        params.add(newPassword);

        paramTypes.add("sessionId");
        params.add(userSessionId);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        if (fullResponse.compareTo("{}") != 0)
        {
            throw new HTTPException(fullResponse);
        }
    }

    public void saveSettings(boolean notificationSettings, String userSessionId)
    {
        String url = "https://catherinegallaher.com/api/settings/save";
        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("notifications");
        params.add(Boolean.toString(notificationSettings));

        paramTypes.add("sessionId");
        params.add(userSessionId);

        System.out.println(params.get(0));

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        if (fullResponse.compareTo("{}") != 0)
        {
            throw new HTTPException(fullResponse);
        }
    }

    public boolean getSettings(String userSessionId)
    {
        String url = "https://catherinegallaher.com/api/settings/";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("sessionId");
        params.add(userSessionId);

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        Type notifications = new TypeToken<Notifications>(){}.getType();

        Notifications notificationsSettings = new Gson().fromJson(fullResponse, notifications);

        return notificationsSettings.getSettings();
    }

    public void clearConversation()
    {
        String url = "https://catherinegallaher.com/api/thread/clear-messages";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("threadId");
        params.add(String.valueOf(threadId));

        sendHTTPReq(paramTypes, params, url, "POST");
    }

    public void muteConversation(Boolean threadMuted)
    {
        String url = "https://catherinegallaher.com/api/thread/settings";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("threadMuted");
        params.add(Boolean.toString(threadMuted));

        paramTypes.add("threadId");
        params.add(String.valueOf(threadId));

        sendHTTPReq(paramTypes, params, url, "POST");
    }

    public Thread createThread(List<String> users, String username)
    {
        users.add(username);

        String url = "https://catherinegallaher.com/api/create-thread";

        List<String> paramTypes = new ArrayList<>();
        List<String> params = new ArrayList<>();

        paramTypes.add("name");

        paramTypes.add("users");
        StringBuilder userList = new StringBuilder("");

        for (String name : users)
        {
            userList.append(name);
            if (name.compareTo(users.get(users.size() - 1)) != 0)
                userList.append(",");
        }

        params.add(userList.toString());
        params.add(userList.toString());

        String fullResponse = sendHTTPReq(paramTypes, params, url, "POST");

        System.out.println(fullResponse);

        fullResponse = fullResponse.replace("\",\"", ",");
        fullResponse = fullResponse.replace(",name", "\",\"name");
        fullResponse = fullResponse.replace(",threadId", "\",\"threadId");
        fullResponse = fullResponse.replace(":[", ":");
        fullResponse = fullResponse.replace("\"]", "\"");
        fullResponse = fullResponse.replace("[", "");
        fullResponse = fullResponse.replace("]", "");

        Type thread = new TypeToken<Thread>(){}.getType();

        System.out.println(fullResponse);

        return new Gson().fromJson(fullResponse, thread);
    }

    private String sendHTTPReq(List<String> parameterTypes, List<String> parameters, String URL, String httpMethod)
    {
        String body = "";

        if (parameters.size() != 0 && parameterTypes.size() != 0)
        {
            if (parameters.size() == 1 && parameterTypes.size() == 1)
            {
                body = parameterTypes.get(0) + "=" + parameters.get(0);
            } else if (parameters.size() > 1 && parameterTypes.size() == parameters.size())
            {
                body = parameterTypes.get(0) + "=" + parameters.get(0);
                for (int i = 1; i < parameters.size(); i++)
                {
                    body += "&" + parameterTypes.get(i) + "=" + parameters.get(i);
                }
            }
        }
        else if (parameters.size() != parameterTypes.size())
        {
            throw new HTTPException("Parameter lengths do not match!\n");
        }

        byte[] postData = body.getBytes(StandardCharsets.UTF_8);
        StringBuilder response = new StringBuilder();

        try
        {
            URL connection = new URL(URL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod(httpMethod);

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            if (httpMethod.compareTo("GET") != 0)
            {
                try (DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream()))
                {
                    wr.write(postData);
                }
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8")))

            {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null)
                {
                    response.append(responseLine.trim());
                }
            }

            httpURLConnection.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "Failed to connect!";
        }

        if (response.toString().contains("userMessage"))
        {
            Type errorMessage = new TypeToken<ErrorMessage>(){}.getType();
            ErrorMessage message = new Gson().fromJson(response.toString(), errorMessage);

            throw new HTTPException(message.getUserMessage());
        }

        return response.toString();
    }

    public int getThreadId()
    {
        return threadId;
    }

    public void setThreadId(int threadId)
    {
        this.threadId = threadId;
    }
}