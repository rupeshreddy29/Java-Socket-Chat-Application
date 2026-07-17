package com.chatapp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatServer {

    // Server Port
    public static final int PORT = 5000;

    // Thread-safe list of connected clients
    private static final List<ClientHandler> clients =
            Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("        JAVA SOCKET CHAT SERVER");
        System.out.println("========================================");
        System.out.println("Server started successfully.");
        System.out.println("Listening on Port : " + PORT);
        System.out.println();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            while (true) {

                Socket clientSocket = serverSocket.accept();

                System.out.println(
                        "New Client Connected : "
                                + clientSocket.getInetAddress().getHostAddress()
                );

                ClientHandler handler = new ClientHandler(clientSocket);


                Thread thread = new Thread(handler);

                thread.start();

                System.out.println(
                        "Assigned Thread : "
                                + thread.getName()
                );

                System.out.println("----------------------------------------");

            }

        } catch (IOException e) {

            System.out.println(
                    "Server Error : " + e.getMessage()
            );

        }

    }

    /*
     * Broadcast message to everyone
     * except sender
     */
    public static void broadcast(String message, ClientHandler sender) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client != sender) {

                    client.sendMessage(message);

                }

            }

        }

    }

    /*
     * Broadcast to every client
     */
    public static void broadcastToAll(String message) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                client.sendMessage(message);

            }

        }

    }

    /*
     * Remove disconnected client
     */
    public static void removeClient(ClientHandler client) {

        clients.remove(client);

    }
    public static void addClient(ClientHandler client) {

        clients.add(client);

    }

    /*
     * Return list of online users
     */
    public static String getOnlineUsers() {

        StringBuilder builder = new StringBuilder();

        builder.append("\n");
        builder.append("========== ONLINE USERS ==========\n");

        int count = 1;

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client.getUsername() != null &&
                        !client.getUsername().isBlank()) {

                    builder.append(count++)
                            .append(". ")
                            .append(client.getUsername())
                            .append("\n");

                }

            }

        }

        builder.append("==================================");

        return builder.toString();

    }

    /*
     * Find client using username
     */
    public static ClientHandler findClient(String username) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client.getUsername() == null) {
                    continue;
                }

                if (client.getUsername()
                        .equalsIgnoreCase(username)) {

                    return client;

                }

            }

        }

        return null;

    }

    /*
     * Check duplicate username
     */
    public static boolean usernameExists(String username) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client.getUsername() == null) {
                    continue;
                }

                if (client.getUsername()
                        .equalsIgnoreCase(username)) {

                    return true;

                }

            }

        }

        return false;

    }

    /*
     * Number of connected clients
     */
    public static int getClientCount() {

        return clients.size();

    }

}