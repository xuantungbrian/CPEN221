package cpen221.mp3.server;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;
import cpen221.mp3.wikimediator.WikiMediator;

/** Template taken from FibonacciServer **/
public class WikiMediatorServer {

    /** Default port number where the server listens for connections. */
    public static final int WIKI_PORT = 7676;
    public static final int search = 0;
    public static final int getPage = 1;
    public static final int zeitgeist = 2;
    public static final int trending = 3;
    public static final int windowedPeakLoadInt = 4;
    public static final int windowedPeakLoad = 5;
    public WikiMediator wikiMed;
    public int operation;
    public int id;

    private ServerSocket serverSocket;

    // Rep invariant: serverSocket != null

    /**
     * Make a WikiMediatorServer that listens for connections on port.
     *
     * @param port
     *            port number, requires 0 <= port <= 65535
     */
    public WikiMediatorServer(int port, WikiMediator wikiMed, int operation) throws IOException {
        serverSocket = new ServerSocket(port);
        this.wikiMed = wikiMed;
        this.operation = operation;
    }

    /**
     * Run the server, listening for connections and handling them.
     *
     * @throws IOException
     *             if the main server socket is broken
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            Socket socket = serverSocket.accept();
            try {
                handle(socket);
            } catch (IOException ioe) {
                ioe.printStackTrace(); // but don't terminate serve()
            } finally {
                socket.close();
            }
        }
    }

    /**
     * Print the parameters of the WikiMediator operation that is being done
     *
     * @param line is the String that will be printed
     * @param x is the number for which WikiMediator operation that will be implemented
     * @param y is yth time line is being read
     */
    public void PrintJSON (String line, int x, int y, int id) {

        if (x == search) {

        }

        else if (x == getPage) {

        }

        else if (x == zeitgeist) {

        }

        else if (x == trending) {

        }

        else if (x == windowedPeakLoadInt) {

        }
        else if (x == windowedPeakLoad) {

        }


    }

    /**
     * Handle one client connection. Returns when client disconnects.
     *
     * @param socket
     *            socket where client is connected
     * @throws IOException
     *             if connection encounters an error
     */
    private void handle(Socket socket) throws IOException {
        System.err.println("client connected");

        BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        PrintWriter out = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream()));

        try {
            int y = 0;
            // each request is a single line containing a number
            for (String line = in.readLine(); line != null; line = in
                    .readLine(), y++) {
                PrintJSON (line, operation, y, id);
                try {

                    if (operation == search) {}

                    System.err.println("reply: " + y);
                    out.print(y + "\n");
                } catch (NumberFormatException e) {
                    // complain about ill-formatted request
                    System.err.println("reply: err");
                    out.println("err");
                }
                out.flush();
            }
        } finally {
            out.close();
            in.close();
        }
    }

    /**
     * Start a WikiMediatorServer running on the default port.
     */
    public static void main(String[] args) {
        try {
            WikiMediator wikiMed = new WikiMediator (20, 10);
            WikiMediatorServer server = new WikiMediatorServer(WIKI_PORT, wikiMed, 1);
            server.serve();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}