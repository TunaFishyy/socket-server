package io.github.TunaFishyy;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                handleClient(serverSocket.accept());
            }
        } catch (IOException exception) {
            System.err.println("Server error: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (InputStreamReader inputStreamReader = new InputStreamReader(clientSocket.getInputStream());
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(clientSocket.getOutputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

            while (true) {
                String msgFromClient = bufferedReader.readLine();

                System.out.println("Client: " + msgFromClient);

                bufferedWriter.write("Message received.");
                bufferedWriter.newLine();
                bufferedWriter.flush();

                if (msgFromClient.equalsIgnoreCase("shutdown") || msgFromClient.equalsIgnoreCase("stop")) {
                    System.out.println("Shutting down.");
                    break;
                }
            }

        } catch (IOException exception) {
            System.err.println("Error handling client: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}
