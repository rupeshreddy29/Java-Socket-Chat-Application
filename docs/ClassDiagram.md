# 📦 Class Diagram

## Overview

The Java Socket Chat Application follows an object-oriented design. Each class has a specific responsibility, making the project modular and easy to maintain.

---

# Package Structure

```
com.chatapp
│
├── client
│   └── ChatClient.java
│
├── gui
│   ├── LoginWindow.java
│   ├── ChatWindow.java
│   └── ChatConnection.java
│
├── server
│   ├── ChatServer.java
│   └── ClientHandler.java
│
└── util
    └── ChatLogger.java
```

---

# UML Style Class Diagram

```
                    +----------------------+
                    |     ChatServer       |
                    +----------------------+
                    | - clients            |
                    | + broadcast()        |
                    | + addClient()        |
                    | + removeClient()     |
                    | + usernameExists()   |
                    | + broadcastUserList()|
                    +----------+-----------+
                               |
                               |
                               ▼
                    +----------------------+
                    |    ClientHandler     |
                    +----------------------+
                    | - socket             |
                    | - reader             |
                    | - writer             |
                    | - username           |
                    +----------------------+
                    | + run()              |
                    | + sendMessage()      |
                    | + send()             |
                    | + getUsername()      |
                    +----------------------+

                               ▲
                               |
                               |
        +----------------------------------------------+
        |                                              |
        |                                              |
+----------------------+                  +----------------------+
|   ChatConnection     |                  |     ChatWindow       |
+----------------------+                  +----------------------+
| - socket             |                  | - chatArea           |
| - reader             |                  | - userList           |
| - writer             |                  | - messageField       |
+----------------------+                  +----------------------+
| + connect()          |                  | + appendMessage()    |
| + sendMessage()      |                  | + updateUsers()      |
| + disconnect()       |                  | + initializeEvents() |
+----------------------+                  +----------------------+

                 ▲
                 |
                 |
         +----------------------+
         |    LoginWindow       |
         +----------------------+
         | + connect()          |
         | + initializeFrame()  |
         +----------------------+

                 ▲
                 |
                 |
         +----------------------+
         |     ChatLogger       |
         +----------------------+
         | + log()              |
         +----------------------+
```

---

# Responsibilities

## ChatServer

- Starts the server
- Accepts client connections
- Maintains connected clients
- Broadcasts messages
- Updates online users

---

## ClientHandler

- Manages communication with one client
- Processes chat commands
- Handles private messaging
- Handles client disconnection

---

## ChatConnection

- Establishes socket connection
- Sends messages
- Receives messages
- Communicates with the GUI

---

## LoginWindow

- Collects username
- Connects to the server
- Opens the chat window

---

## ChatWindow

- Displays chat messages
- Shows online users
- Sends messages
- Handles user interactions

---

## ChatLogger

- Records important chat events
- Logs joins, leaves, and messages

---

# Design Principles

- Object-Oriented Programming (OOP)
- Single Responsibility Principle
- Modular Design
- Separation of Concerns
- Reusable Components

---

# Benefits

- Easy to understand
- Easy to maintain
- Easy to extend
- Suitable for future enhancements