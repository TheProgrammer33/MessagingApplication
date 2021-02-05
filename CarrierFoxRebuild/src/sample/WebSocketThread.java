package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WebSocketThread implements Runnable
{
    private java.lang.Thread thread;
    private boolean exit;
    private WebSocketController chatclient;

    WebSocketThread(WebSocketController chatclient)
    {
        this.thread = new java.lang.Thread(this, "websocketThread");
        this.chatclient = chatclient;
        this.exit = false;

        this.thread.start();
    }

    public void run()
    {
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (!exit)
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
    }

    public void stop()
    {
        this.exit = true;
        thread.interrupt();
        chatclient.close();
    }
}
