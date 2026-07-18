# 🏗️ System Architecture

## Overview

The Java Socket Chat Application follows a **Client-Server Architecture**.

A central server listens for incoming client connections. Each connected client communicates with the server through a dedicated thread, allowing multiple users to chat simultaneously.

---

## Architecture Diagram

```
                 +----------------------+
                 |      Chat Server     |
                 |----------------------|
                 | ServerSocket : 5000  |
                 | Client Management    |
                 | Message Broadcast    |
                 +----------+-----------+
                            |
        ---------------------------------------------
        |                   |                      |
        |                   |                      |
+---------------+   +---------------+    +---------------+
| Client (GUI)  |   | Client (GUI)  |    | Client (GUI)  |
| Rupesh        |   | Krithi        |    | User 3        |
+---------------+   +---------------+    +---------------+
```

---

## Components

### Chat Server

Responsibilities:

- Accept client connections
- Create a new thread for each client
- Broadcast public messages
- Handle private messages
- Maintain online user list

---

### Client Handler

Responsibilities:

- Read messages from a client
- Process commands
- Send messages
- Handle disconnects

---

### GUI Client

Responsibilities:

- Login window
- Chat interface
- Display online users
- Send/receive messages

---

## Communication Flow

1. Client connects to the server.
2. Username is validated.
3. Server creates a ClientHandler thread.
4. Messages are exchanged.
5. Online users list is updated automatically.
6. Client disconnects gracefully.

---

## Technologies

- Java
- TCP Socket Programming
- Java Swing
- Multithreading
- Git & GitHub