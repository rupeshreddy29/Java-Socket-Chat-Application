# 📖 User Guide

## Introduction

The Java Socket Chat Application allows multiple users to communicate over a local network using a client-server architecture.

The application provides both public and private messaging through a simple graphical user interface built with Java Swing.

---

# Starting the Application

## Step 1: Start the Server

Run:

```bash
java -cp out com.chatapp.server.ChatServer
```

The server will listen on port **5000**.

---

## Step 2: Start the Client

Run:

```bash
java -cp out com.chatapp.gui.LoginWindow
```

A login window will appear.

---

# Logging In

Enter a unique username.

Example:

```
rupesh
```

If the username already exists, the server will ask you to choose another username.

---

# Public Chat

Type a message in the input box and click **Send**.

Example:

```
Hello everyone!
```

The message is delivered to every connected user.

---

# Private Messaging

Private messages use the following command:

```
/pm <username> <message>
```

Example:

```
/pm krithi Hello, how are you?
```

Only the selected user receives the message.

---

# Online Users

The Online Users panel automatically displays all currently connected users.

The list updates whenever a user joins or leaves the chat.

---

# Disconnecting

To leave the chat:

- Close the application window

or

```
/exit
```

The server removes the user from the active users list and notifies other clients.

---

# Features

- Public Chat
- Private Messaging
- Live Online Users
- Swing GUI
- Multi-threaded Server
- Graceful Disconnect

---

# Commands

| Command | Description |
|----------|-------------|
| `/pm username message` | Send a private message |
| `/exit` | Disconnect from the server |

---

# Best Practices

- Use a unique username.
- Keep the server running before launching clients.
- Do not use duplicate usernames.
- Close the application gracefully to notify other users.