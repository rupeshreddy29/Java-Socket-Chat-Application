# ⚙️ Installation Guide

This guide explains how to set up and run the Java Socket Chat Application on your computer.

---

# Prerequisites

Before running the project, ensure the following software is installed:

- Java JDK 17 or later
- Git
- Visual Studio Code (recommended)

---

# Clone the Repository

```bash
git clone https://github.com/rupeshreddy29/Java-Socket-Chat-Application.git
```

Move into the project directory:

```bash
cd Java-Socket-Chat-Application
```

---

# Compile the Project

Open a terminal in the project folder and run:

```bash
javac -d out src/com/chatapp/util/*.java ^
src/com/chatapp/server/*.java ^
src/com/chatapp/client/*.java ^
src/com/chatapp/gui/*.java
```

---

# Start the Server

Open a terminal:

```bash
java -cp out com.chatapp.server.ChatServer
```

Expected output:

```
========================================
JAVA SOCKET CHAT SERVER
========================================
Server started successfully.
Listening on Port : 5000
```

---

# Start the GUI Client

Open another terminal:

```bash
java -cp out com.chatapp.gui.LoginWindow
```

Enter a username and connect.

To test multiple users, open another client window and connect using a different username.

---

# Features Available

- Public Chat
- Private Messaging (`/pm username message`)
- Live Online Users
- Username Validation
- Graceful Disconnect

---

# Troubleshooting

### Port Already in Use

If port **5000** is already occupied, close the existing server or change the port number in `ChatServer.java` and `ChatConnection.java`.

### Java Not Found

Verify Java installation:

```bash
java --version
```

### Compilation Errors

Make sure all source files are located under:

```
src/com/chatapp/
```

and compile using the commands above.

---

# Project Structure

```
src/
└── com/
    └── chatapp/
        ├── client/
        ├── gui/
        ├── server/
        └── util/
```

---

# Author

**Rupesh Reddy Avula**

GitHub:
https://github.com/rupeshreddy29