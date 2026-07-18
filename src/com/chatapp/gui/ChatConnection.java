package com.chatapp.gui;

import java.io.*;
import java.net.Socket;

public class ChatConnection {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public boolean connect(String username) {

        try {

            socket = new Socket("localhost", 5000);

            in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                    socket.getOutputStream(), true);

            // Read server welcome message
            in.readLine();

            // Send username
            out.println(username);

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }

    }

    public void sendMessage(String message) {

        if (out != null) {

            out.println(message);

        }

    }

    public void disconnect() {

        try {

            if (out != null)
                out.println("/exit");

            if (socket != null)
                socket.close();

        } catch (Exception ignored) {
        }

    }

    public void startReceiving(ChatWindow window) {

        Thread receiver = new Thread(() -> {

            try {

                String message;

                while ((message = in.readLine()) != null) {

                    if (message.startsWith("USERS:")) {

                        String data = message.substring(6);

                        String[] users;

                        if (data.isEmpty()) {

                            users = new String[0];

                        } else {

                            users = data.split(",");

                        }

                        window.updateUsers(users);

                    } else {

                        window.appendMessage(message);

                    }

                }

            } catch (IOException e) {

                window.appendMessage("[Disconnected from Server]");

            }

        });

        receiver.setDaemon(true);
        receiver.start();

    }

}