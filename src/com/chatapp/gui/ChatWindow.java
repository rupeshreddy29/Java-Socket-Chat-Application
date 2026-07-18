package com.chatapp.gui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

public class ChatWindow extends JFrame {

    private final String username;
    private final ChatConnection connection;

    private JTextArea chatArea;
    private JTextField messageField;
    private JButton sendButton;

    private DefaultListModel<String> userModel;
    private JList<String> userList;

    private JLabel statusLabel;

    public ChatWindow(String username, ChatConnection connection) {

        this.username = username;
        this.connection = connection;

        initializeFrame();
        initializeComponents();
        initializeLayout();
        initializeEvents();

    }

    private void initializeFrame() {

        setTitle("Java Socket Chat - " + username);

        setSize(950, 600);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }

    private void initializeComponents() {

        chatArea = new JTextArea();

        chatArea.setEditable(false);

        chatArea.setLineWrap(true);

        chatArea.setWrapStyleWord(true);

        chatArea.setFont(
                new Font("Consolas", Font.PLAIN, 15));

        messageField = new JTextField();

        messageField.setFont(
                new Font("Arial", Font.PLAIN, 15));

        sendButton = new JButton("Send");

        sendButton.setFont(
                new Font("Arial", Font.BOLD, 15));

        statusLabel = new JLabel(
                "Connected as " + username);

        statusLabel.setFont(
                new Font("Arial", Font.PLAIN, 13));

        userModel = new DefaultListModel<>();

        userList = new JList<>(userModel);

        userList.setFont(
                new Font("Arial", Font.PLAIN, 15));

    }
    private void initializeLayout() {

        setLayout(new BorderLayout());

        // -------------------------
        // Header
        // -------------------------

        JLabel header = new JLabel(
                "JAVA SOCKET CHAT APPLICATION",
                SwingConstants.CENTER);

        header.setFont(
                new Font("Arial",
                        Font.BOLD,
                        24));

        header.setBorder(
                BorderFactory.createEmptyBorder(
                        15,
                        10,
                        15,
                        10));

        add(header, BorderLayout.NORTH);

        // -------------------------
        // Online Users Panel
        // -------------------------

        JPanel leftPanel = new JPanel(new BorderLayout());

        leftPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        5));

        JLabel usersLabel = new JLabel(
                "Online Users",
                SwingConstants.CENTER);

        usersLabel.setFont(
                new Font("Arial",
                        Font.BOLD,
                        16));

        leftPanel.add(
                usersLabel,
                BorderLayout.NORTH);

        JScrollPane userScroll =
                new JScrollPane(userList);

        userScroll.setPreferredSize(
                new Dimension(180, 0));

        leftPanel.add(
                userScroll,
                BorderLayout.CENTER);

        add(
                leftPanel,
                BorderLayout.WEST);

        // -------------------------
        // Chat Area
        // -------------------------

        JScrollPane chatScroll =
                new JScrollPane(chatArea);

        chatScroll.setBorder(
                BorderFactory.createTitledBorder(
                        "Chat"));

        add(
                chatScroll,
                BorderLayout.CENTER);

        // -------------------------
        // Bottom Panel
        // -------------------------

        JPanel bottomPanel =
                new JPanel(new BorderLayout(10,10));

        bottomPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        10,
                        10,
                        10,
                        10));

        bottomPanel.add(
                messageField,
                BorderLayout.CENTER);

        bottomPanel.add(
                sendButton,
                BorderLayout.EAST);

        bottomPanel.add(
                statusLabel,
                BorderLayout.SOUTH);

        add(
                bottomPanel,
                BorderLayout.SOUTH);

    }
    
    private void initializeEvents() {

        sendButton.addActionListener(e -> sendCurrentMessage());

        messageField.addActionListener(e -> sendCurrentMessage());

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int option = JOptionPane.showConfirmDialog(
                        ChatWindow.this,
                        "Disconnect and close the chat?",
                        "Exit",
                        JOptionPane.YES_NO_OPTION);

                if (option == JOptionPane.YES_OPTION) {

                    connection.disconnect();

                    dispose();

                }

            }

        });

    }

    private void sendCurrentMessage() {

        String message = messageField.getText().trim();

        if (message.isEmpty()) {

            return;

        }

        connection.sendMessage(message);

        messageField.setText("");

        messageField.requestFocus();

    }

    public void appendMessage(String message) {

        SwingUtilities.invokeLater(() -> {

            chatArea.append(message + "\n");

            chatArea.setCaretPosition(
                    chatArea.getDocument().getLength());

        });

    }

    public void updateUsers(String[] users) {

        SwingUtilities.invokeLater(() -> {

            userModel.clear();

            userModel.addElement("Online Users");

            for (String user : users) {

                if (!user.trim().isEmpty()) {

                    userModel.addElement(user);

                }

            }

        });

    }

    public JTextArea getChatArea() {

        return chatArea;

    }

    public JTextField getMessageField() {

        return messageField;

    }

    public JButton getSendButton() {

        return sendButton;

    }

}