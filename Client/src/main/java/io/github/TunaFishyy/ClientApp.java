package io.github.TunaFishyy;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {

            Scanner scanner = new Scanner(System.in);

            String msgToSend;
            while (true) {
                msgToSend = scanner.nextLine();
                bufferedWriter.write(msgToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();

                System.out.println("Server: " + bufferedReader.readLine());

                if (msgToSend.equalsIgnoreCase("shutdown") || msgToSend.equalsIgnoreCase("stop")) {
                    System.out.println("Shutting down.");
                    break;
                }
            }

        } catch (IOException exception) {
            System.out.println("Oops");
            exception.printStackTrace();
        }
    }
}
