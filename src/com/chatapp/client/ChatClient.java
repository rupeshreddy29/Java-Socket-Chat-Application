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

        try (

                Socket socket = new Socket(HOST, PORT);

                BufferedReader reader =
                        new BufferedReader(
                                new InputStreamReader(
                                        socket.getInputStream()));

                PrintWriter writer =
                        new PrintWriter(
                                socket.getOutputStream(),
                                true);

                Scanner scanner =
                        new Scanner(System.in)

        ) {

            Thread receiveThread = new Thread(() -> {

                try {

                    String message;

                    while ((message = reader.readLine()) != null) {

                        System.out.println(message);

                    }

                } catch (IOException e) {

                    System.out.println("Disconnected from server.");

                }

            });

            receiveThread.start();

            System.out.print("Username: ");

            String username = scanner.nextLine();

            writer.println(username);

            while (true) {

                System.out.print(username + ": ");

                String message = scanner.nextLine();

                writer.println(message);

                if (message.equalsIgnoreCase("/exit")) {

                    break;

                }

            }

        } catch (IOException e) {

            System.out.println(
                    "Client Error: " + e.getMessage());

        }

    }

}