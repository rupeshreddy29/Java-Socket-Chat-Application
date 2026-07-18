# 🌐 Socket Communication

## Overview

The Java Socket Chat Application uses the **TCP (Transmission Control Protocol)** for reliable communication between the server and connected clients.

The server continuously listens for incoming client connections on **port 5000**.

Each client establishes a TCP connection with the server and communicates by sending and receiving text messages.

---

# Communication Architecture

```
                  +----------------------+
                  |     Chat Server      |
                  |----------------------|
                  | ServerSocket : 5000  |
                  +----------+-----------+
                             |
         -------------------------------------------
         |                    |                    |
         |                    |                    |
   +------------+      +------------+      +------------+
   | Client 1   |      | Client 2   |      | Client 3   |
   | (Swing)    |      | (Swing)    |      | (Swing)    |
   +------------+      +------------+      +------------+
```

---

# Connection Process

1. The server starts and creates a `ServerSocket`.
2. A client opens a `Socket` connection.
3. The server accepts the connection.
4. A new `ClientHandler` thread is created.
5. The client sends its username.
6. Communication begins.

---

# Public Messaging

When a client sends a normal message:

```
Hello Everyone!
```

The server:

- Receives the message.
- Identifies the sender.
- Broadcasts it to every connected client except the sender.

---

# Private Messaging

Private messages follow this format:

```
/pm username message
```

Example:

```
/pm krithi Hi!
```

The server:

1. Finds the target user.
2. Sends the message only to that client.
3. Confirms delivery to the sender.

---

# Online Users

Whenever:

- A user joins
- A user disconnects

the server broadcasts:

```
USERS:rupesh,krithi,user3
```

The GUI client receives this update and refreshes the Online Users panel automatically.

---

# Thread Management

Each client connection is handled independently.

```
Server
│
├── Thread-1 (rupesh)
├── Thread-2 (krithi)
├── Thread-3 (user3)
└── Thread-4 (user4)
```

This allows multiple users to communicate simultaneously.

---

# Advantages of TCP

- Reliable communication
- Ordered message delivery
- Error checking
- Connection-oriented protocol

These properties make TCP suitable for real-time chat applications.

---

# Classes Involved

| Class | Responsibility |
|--------|----------------|
| ChatServer | Accepts client connections |
| ClientHandler | Handles communication with one client |
| ChatConnection | GUI client socket communication |
| ChatWindow | Displays chat messages |
| LoginWindow | Collects username and connects to server |

---

# Summary

The application follows a reliable TCP-based client-server architecture using multithreading. Each connected client communicates through an independent thread, enabling smooth real-time messaging between multiple users.