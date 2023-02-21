package cpen221.mp3.server;

import java.io.*;
import java.math.BigInteger;
import java.net.Socket;


/** Template taken from FibonacciClient **/
public class WikiMediatorClient {

    private Socket socket;
    private BufferedReader in;
    // Rep invariant: socket, in, out != null
    private PrintWriter out;
    private int operation;

    /**
     * Make a WikiMediatorClient and connect it to a server running on
     * hostname at the specified port.
     *
     * @throws IOException if can't connect
     */
    public WikiMediatorClient(String hostname, int port, int operation) throws IOException {
        socket = new Socket(hostname, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        operation = this.operation;
    }

    /**
     *
     */
    public static void main(String[] args) {
        try {
            int operation = 0;
            WikiMediatorClient client = new WikiMediatorClient("127.0.0.1",
                    WikiMediatorServer.WIKI_PORT, operation);

            // 0 search, 1 getpage, 2 zeitgeist, 3 trending, 4 windowedPeakLoad(int), 5 windowPeakLoad
            int x = 0;
            client.sendRequest(operation);

            client.close();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Send a request to the server. Requires this is "open".
     *
     * @param x to determine which operation to do
     * @throws IOException if network or server failure
     */
    public void sendRequest(int x) throws IOException {
        out.print(x + "\n");
        out.flush();
    }


    /**
     * Closes the client's connection to the server.
     * This client is now "closed". Requires this is "open".
     *
     * @throws IOException if close fails
     */
    public void close() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

}