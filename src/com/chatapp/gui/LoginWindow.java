package com.chatapp.gui;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JButton connectButton;
    private JLabel statusLabel;

    private ChatConnection connection;

    public LoginWindow() {

        initializeFrame();
        initializeComponents();
        initializeLayout();
        initializeEvents();

    }

    private void initializeFrame() {

        setTitle("Java Socket Chat");

        setSize(400,220);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void initializeComponents() {

        usernameField = new JTextField();

        usernameField.setFont(
                new Font("Arial", Font.PLAIN, 16));

        connectButton = new JButton("Connect");

        connectButton.setFont(
                new Font("Arial", Font.BOLD, 16));

        statusLabel = new JLabel(
                "Enter your username",
                SwingConstants.CENTER);

    }

    private void initializeLayout() {

        JPanel panel = new JPanel(new GridLayout(5,1,10,10));

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        20,20,20,20));

        JLabel title = new JLabel(
                "JAVA SOCKET CHAT",
                SwingConstants.CENTER);

        title.setFont(
                new Font("Arial",
                        Font.BOLD,
                        22));

        panel.add(title);
        panel.add(usernameField);
        panel.add(connectButton);
        panel.add(statusLabel);

        add(panel);

    }

    private void initializeEvents() {

        connectButton.addActionListener(e -> connect());

        usernameField.addActionListener(e -> connect());

    }

    private void connect() {

        String username = usernameField.getText().trim();

        if(username.isEmpty()){

            JOptionPane.showMessageDialog(
                    this,
                    "Enter Username");

            return;

        }

        connection = new ChatConnection();

        boolean ok =
                connection.connect(username);

        if(ok){

            ChatWindow window =
                    new ChatWindow(
                            username,
                            connection);

            connection.startReceiving(window);

            window.setVisible(true);

            dispose();

        }else{

            statusLabel.setText(
                    "Unable to connect");

        }

    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() ->

                new LoginWindow().setVisible(true));

    }

}