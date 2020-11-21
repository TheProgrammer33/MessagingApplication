package sample;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most
 * important callbacks are overloaded.
 */
public class ExampleClient extends WebSocketClient {

    public ExampleClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected");

    }

    @Override
    public void onMessage(String message) {
        System.out.println("got: " + message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected");

    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();

    }

}
