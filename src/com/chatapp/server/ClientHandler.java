package com.chatapp.server;

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
                    new InputStreamReader(socket.getInputStream()));

            writer = new PrintWriter(
                    socket.getOutputStream(),
                    true);

            writer.println("[SERVER] Connected successfully.");
            
            writer.println("Enter your username:");
            username = reader.readLine();

            System.out.println(
                    "Username Registered: " + username
            );

            writer.println(
                    "[SERVER] Welcome " + username + "!"
            );

            ChatServer.broadcast(
                    "[SERVER] " + username + " joined the chat.",
                    this
            );

            String message;

            while ((message = reader.readLine()) != null) {

                System.out.println(
                        "[" + Thread.currentThread().getName() + "] "
                                + message);
                if (message.equalsIgnoreCase("/users")) {

                    writer.println(ChatServer.getOnlineUsers());

                    continue;

                }
                if (message.equalsIgnoreCase("/exit")) {

                    writer.println("[SERVER] Goodbye!");
                    break;

                }

                ChatServer.broadcast(
                        username + ": " + message,
                        this
                );

            }

        } catch (IOException e) {

            System.out.println(
                    "Client Error: " + e.getMessage());

        } finally {

            ChatServer.broadcast(
                    "[SERVER] " + username + " left the chat.",
                    this
            );

            ChatServer.removeClient(this);

            try {

                socket.close();

            } catch (IOException e) {

                    System.out.println(
                            "Error closing socket: " + e.getMessage()
                    );

                }

            System.out.println(
                    Thread.currentThread().getName()
                            + " disconnected.");

        }

    }
    public String getUsername() {

        return username;

    }
    public void sendMessage(String message) {

        if (writer != null) {

            writer.println(message);

        }

    }

}