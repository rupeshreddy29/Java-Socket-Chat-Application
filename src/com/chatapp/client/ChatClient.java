package com.chatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("        JAVA CHAT APPLICATION");
        System.out.println("========================================");

        try (
                Socket socket = new Socket(HOST, PORT);

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(socket.getInputStream()));

                PrintWriter writer =
                        new PrintWriter(socket.getOutputStream(), true);

                Scanner scanner = new Scanner(System.in)
        ) {

            // Initial server messages
            System.out.println(reader.readLine());
            System.out.println(reader.readLine());

            String username;

            // Username registration loop
            while (true) {

                System.out.print("Username: ");
                username = scanner.nextLine().trim();

                writer.println(username);

                String response = reader.readLine();

                if (response == null) {
                    return;
                }

                System.out.println(response);

                if (response.startsWith("[SERVER] Welcome")) {
                    break;
                }
            }

            final String currentUser = username;

            System.out.println();
            System.out.println("============= COMMANDS =============");
            System.out.println("/users");
            System.out.println("/pm <username> <message>");
            System.out.println("/exit");
            System.out.println("====================================");
            System.out.println();

            Thread receiveThread = new Thread(() -> {

                try {

                    String message;

                    while ((message = reader.readLine()) != null) {

                        System.out.println();
                        System.out.println(message);
                        System.out.print(currentUser + " > ");

                    }

                } catch (IOException e) {

                    System.out.println();
                    System.out.println("Disconnected from server.");

                }

            });

            receiveThread.setDaemon(true);
            receiveThread.start();

            while (true) {

                System.out.print(currentUser + " > ");

                String message = scanner.nextLine();

                writer.println(message);

                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }

            }

            System.out.println("Disconnected.");

        } catch (IOException e) {

            System.out.println("Client Error : " + e.getMessage());

        }

    }

}