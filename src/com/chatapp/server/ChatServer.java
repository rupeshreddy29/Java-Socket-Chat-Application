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
                                + clientSocket.getInetAddress().getHostAddress());

                ClientHandler handler = new ClientHandler(clientSocket);

                clients.add(handler);

                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {

            System.out.println("Server Error: " + e.getMessage());

        }
    }

    public static void broadcast(String message, ClientHandler sender) {

        synchronized (clients) {

            for (ClientHandler client : clients) {

                if (client != sender) {
                    client.sendMessage(message);
                }

            }

        }

    }

    public static void removeClient(ClientHandler client) {

        clients.remove(client);

    }
}