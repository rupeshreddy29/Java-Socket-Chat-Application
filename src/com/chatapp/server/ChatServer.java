package com.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {

    public static final int PORT = 5000;

    private static final List<ClientHandler> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("     JAVA CHAT SERVER STARTED");
        System.out.println("====================================");
        System.out.println("Listening on port: " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "New client connected: "
                                + clientSocket.getInetAddress().getHostAddress()
                );

                ClientHandler handler = new ClientHandler(clientSocket);

                clients.add(handler);

                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {

            System.out.println(
                    "Server Error: " + e.getMessage()
            );

        }
    }

    // Broadcast message to every client except the sender
    public static void broadcast(String message, ClientHandler sender) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client != sender) {

                    client.sendMessage(message);

                }

            }

        }

    }

    // Broadcast message to every connected client
    public static void broadcastToAll(String message) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                client.sendMessage(message);

            }

        }

    }

    // Remove disconnected client
    public static void removeClient(ClientHandler client) {

        clients.remove(client);

    }
    public static String getOnlineUsers() {

        StringBuilder users = new StringBuilder();

        users.append("\n========= ONLINE USERS =========\n\n");

        int count = 1;

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client.getUsername() != null) {

                    users.append(count++)
                        .append(". ")
                        .append(client.getUsername())
                        .append("\n");

                }

            }

        }

        users.append("\n===============================\n");

        return users.toString();

    }
    public static ClientHandler findClient(String username) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client.getUsername() != null &&
                    client.getUsername().equalsIgnoreCase(username)) {

                    return client;

                }

            }

        }

        return null;

    }

}