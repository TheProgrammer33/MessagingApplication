package sample;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class HTTPRequest
{
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

    public void sendMessage(String message)
    {
        String url = "https://catherinegallaher.com/api/thread/1000/message/add";

        String parameters = "messageBody=" + message + "&user=septri";
        byte[] postData = parameters.getBytes(StandardCharsets.UTF_8);

        try {
            URL connection = new URL(url);

            HttpURLConnection httpURLConnection = (HttpURLConnection) connection.openConnection();

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            httpURLConnection.setDoOutput(true);

            try( DataOutputStream wr = new DataOutputStream( httpURLConnection.getOutputStream())) {
                wr.write( postData );
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
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

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            System.out.println(response.toString());

            httpURLConnection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
