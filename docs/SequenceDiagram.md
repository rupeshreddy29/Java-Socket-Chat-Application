# 🔄 Sequence Diagram

## Overview

This document illustrates the sequence of interactions between the client and the server during common operations such as connecting, sending messages, private messaging, and disconnecting.

---

# 1. Client Connection Sequence

```
+--------+        +------------+        +---------------+
| Client |        | ChatServer |        | ClientHandler |
+--------+        +------------+        +---------------+
     |                  |                       |
     | Connect          |                       |
     |----------------->|                       |
     |                  | Accept Connection     |
     |                  |---------------------->|
     |                  | Create ClientHandler  |
     |                  |<----------------------|
     | Send Username    |                       |
     |----------------->|                       |
     |                  | Validate Username     |
     |                  |                       |
     |                  | Add Client            |
     |                  |                       |
     |<-----------------| Connection Successful |
```

---

# 2. Public Message Sequence

```
Client A           ChatServer          Client B         Client C
   |                   |                  |                |
   | Send Message      |                  |                |
   |------------------>|                  |                |
   |                   | Broadcast        |                |
   |                   |----------------->|                |
   |                   |------------------------------->   |
   |                   |                  |                |
```

---

# 3. Private Message Sequence

```
Client A           ChatServer          Client B
   |                   |                  |
   | /pm message       |                  |
   |------------------>|                  |
   |                   | Locate User      |
   |                   |----------------->|
   |                   | Deliver Message  |
   |                   |----------------->|
   |<------------------| Delivery Success |
```

---

# 4. Online Users Update

```
Client            ChatServer         Other Clients
   |                  |                   |
   | Connect          |                   |
   |----------------->|                   |
   |                  | Update User List  |
   |                  |------------------>|
   |                  | USERS:Rupesh,...  |
   |                  |------------------>|
```

---

# 5. Client Disconnect

```
Client            ChatServer         Other Clients
   |                  |                   |
   | Disconnect       |                   |
   |----------------->|                   |
   |                  | Remove Client     |
   |                  |                   |
   |                  | Update User List  |
   |                  |------------------>|
   |                  | Notify Users      |
   |                  |------------------>|
```

---

# Sequence Summary

The communication flow follows these steps:

1. Client connects to the server.
2. Server validates the username.
3. Server creates a dedicated `ClientHandler` thread.
4. Messages are exchanged through TCP sockets.
5. Public messages are broadcast to all users.
6. Private messages are delivered only to the intended recipient.
7. The online users list is synchronized whenever a client joins or leaves.
8. On disconnect, the server removes the client and informs the remaining users.

---

# Key Components

| Component | Responsibility |
|-----------|----------------|
| Client | Sends and receives messages |
| ChatServer | Accepts connections and manages clients |
| ClientHandler | Handles communication for one client |
| ChatWindow | Displays messages and user list |
| ChatConnection | Manages socket communication |

---

# Conclusion

The sequence diagrams demonstrate the flow of communication between the client and server. Each client operates through its own `ClientHandler` thread, enabling multiple users to communicate simultaneously while maintaining reliable TCP-based communication.