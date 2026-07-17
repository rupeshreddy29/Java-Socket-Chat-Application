package com.chatapp.server;

import com.chatapp.util.ChatLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket socket;

    private BufferedReader reader;
    private PrintWriter writer;

    private String username;

    public ClientHandler(Socket socket) {

        this.socket = socket;

    }

    @Override
    public void run() {

        try {

            reader = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            writer = new PrintWriter(
                    socket.getOutputStream(),
                    true);

            writer.println("[SERVER] Connected successfully.");

            // -----------------------------
            // Username Registration
            // -----------------------------

            while (true) {

                writer.println("Enter your username:");

                username = reader.readLine();

                if (username == null) {

                    return;

                }

                username = username.trim();

                if (username.isEmpty()) {

                    writer.println(
                            "[SERVER] Username cannot be empty."
                    );

                    continue;

                }

                if (ChatServer.usernameExists(username)) {

                    writer.println(
                            "[SERVER] Username already taken. Try another."
                    );

                    continue;

                }

                break;

            }

            System.out.println(
                    "Username Registered : " + username
            );
            ChatServer.addClient(this);

            ChatLogger.log(username + " joined the chat.");

            writer.println(
                    "[SERVER] Welcome " + username + "!"
            );

            ChatServer.broadcast(
                    "[SERVER] " + username + " joined the chat.",
                    this
            );

            // -----------------------------
            // Chat Loop
            // -----------------------------

            String message;

            while ((message = reader.readLine()) != null) {

                message = message.trim();

                if (message.isEmpty()) {

                    continue;

                }

                System.out.println(
                        "[" + username + "] : " + message
                );

                // Exit

                if (message.equalsIgnoreCase("/exit")) {

                    writer.println("[SERVER] Goodbye!");

                    break;

                }

                // Online Users

                if (message.equalsIgnoreCase("/users")) {

                    writer.println(
                            ChatServer.getOnlineUsers()
                    );

                    continue;

                }

                // Private Message

                if (message.startsWith("/pm ")) {

                    String[] parts =
                            message.split(" ", 3);

                    if (parts.length < 3) {

                        writer.println(
                                "[SERVER] Usage : /pm <username> <message>"
                        );

                        continue;

                    }

                    String receiver = parts[1];
                    String privateMessage = parts[2];

                    ClientHandler target =
                            ChatServer.findClient(receiver);

                    if (target == null) {

                        writer.println(
                                "[SERVER] User not found."
                        );

                        continue;

                    }

                    target.sendMessage(
                            "[PRIVATE] "
                                    + username
                                    + " : "
                                    + privateMessage
                    );

                    writer.println(
                            "[PRIVATE to "
                                    + receiver
                                    + "] "
                                    + privateMessage
                    );

                    ChatLogger.log(
                            "[PRIVATE] "
                                    + username
                                    + " -> "
                                    + receiver
                                    + " : "
                                    + privateMessage
                    );

                    continue;

                }

                // Public Chat

                String chatMessage =
                        username + " : " + message;

                ChatLogger.log(chatMessage);

                ChatServer.broadcast(
                        chatMessage,
                        this
                );

            }

        } catch (IOException e) {

            System.out.println(
                    "Client Error : "
                            + e.getMessage()
            );

        } finally {

            ChatServer.removeClient(this);

            if (username != null) {

                ChatLogger.log(
                        username + " left the chat."
                );

                ChatServer.broadcast(
                        "[SERVER] "
                                + username
                                + " left the chat.",
                        this
                );

            }

            try {

                socket.close();

            } catch (IOException e) {

                System.out.println(
                        "Error closing socket : "
                                + e.getMessage()
                );

            }

            System.out.println(
                    "Client Disconnected : "
                            + username
            );

        }

    }

    public void sendMessage(String message) {

        if (writer != null) {

            writer.println(message);

        }

    }

    public String getUsername() {

        return username;

    }

}