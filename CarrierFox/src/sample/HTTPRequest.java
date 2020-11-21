package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;


public class HTTPRequest
{
    private ExampleClient chatClient;

    private void openWebSocket()
    {
        try
        {
            ExampleClient chatclient = new ExampleClient(new URI("wss://catherinegallaher.com/"));

            SSLContext sslContext = null;
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, null, null);

            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

            chatclient.setSocketFactory(factory);

            chatclient.connectBlocking();

            this.chatClient = chatclient;

            Thread t1 = new Thread(new Runnable() { //Switch to end when user switches threads
                public void run()
                {
                    try
                    {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        while (true)
                        {
                            String line = reader.readLine();
                            if (line.equals("close"))
                            {
                                chatclient.closeBlocking();
                            } else if (line.equals("open"))
                            {
                                chatclient.reconnect();
                            } else
                            {
                                chatclient.send(line);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }});
            t1.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages()
    {
        String url = "https://catherinegallaher.com/api/thread/1000/messages/";

        StringBuilder response = new StringBuilder();

        try {
            URL connection = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Type message = new TypeToken<ArrayList<Message>>(){}.getType();
        List<Message> messageList = new Gson().fromJson(response.toString(), message);

        return messageList;
    }

    public void sendMessage(String message, String id)
    {
        openWebSocket();

        this.chatClient.send("{\"update\": \"" + message + "\", \"threadId\": " + id + "}");
    }

    public boolean login(String username, String password)
    {
        String url = "https://catherinegallaher.com/api/login/";

        String parameters = "username=" + username + "&password=" + password;
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

        try {
            URL connection = new URL(url);
            StringBuilder response = new StringBuilder();

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            try( DataOutputStream wr = new DataOutputStream( httpURLConnection.getOutputStream())) {
                wr.write( postData );
            }

            if (httpURLConnection.getResponseCode() == 404 || httpURLConnection.getResponseCode() == 401)
                throw new HTTPException("Invalid Username or Password!");

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            httpURLConnection.disconnect();

            if (response.toString().compareTo("{}") == 0)
            {
                return true;
            }
            else
            {
                System.out.println(response.toString());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createAccount(String email, String username, String password)
    {
        String url = "https://catherinegallaher.com/api/create-account";

        String parameters = "email=" + email + "&username=" + username + "&password=" + password;
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

        try {
            URL connection = new URL(url);
            StringBuilder response = new StringBuilder();

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            try( DataOutputStream wr = new DataOutputStream( httpURLConnection.getOutputStream())) {
                wr.write( postData );
            }

            if (httpURLConnection.getResponseCode() == 409)
                throw new HTTPException("Username exists!\n");

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            httpURLConnection.disconnect();

            String fullResponse = response.toString();

            if (fullResponse.compareTo("{}") != 0)
            {
                throw new HTTPException(fullResponse);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
