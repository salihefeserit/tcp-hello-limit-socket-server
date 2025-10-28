# Java "Hello" Limit Socket Server

This project is a simple client-server application built in Java that demonstrates stateful communication using TCP sockets. The server implements a unique protocol where each client must send "hello" a randomly determined number of times before being allowed to send a final, "saved" message.

## Features

- **Client-Server Architecture:** Uses `java.net.ServerSocket` and `java.net.Socket` for communication.
    
- **Dynamic "Hello" Protocol:**
    
    - For each new client connection, the server generates a random "hello" limit.
        
    - The server tracks the number of times the client has successfully said "hello".
        
    - The client receives a numerical count for each valid "hello" message.
        
    - The server provides a specific rejection message if the client sends anything other than "hello" before the limit is reached.
        
- **Stateful Connection:** The server's response depends on the "state" of the client (i.e., how many times they have said "hello").
    
- **Automatic Disconnection:** The connection is terminated by the server after the client successfully sends their final message.

---
## 💬 Communication Protocol

1. **Connection:** A client establishes a connection with the server.
    
2. **Limit Generation:** The server accepts the connection (in a new thread) and secretly generates a random integer (e.g., `5`). This number becomes the "hello limit" for this specific client.
    
3. **"hello" Phase:** The server enters a loop, waiting for client messages.
    
    - **Case 1: Client sends "hello" (and limit is NOT reached):**
        
        - The server increments its counter for that client.
            
        - The server responds to the client with the current count (e.g., "1", "2", "3"...).
            
    - **Case 2: Client sends anything else (and limit is NOT reached):**
        
        - The client sends a message like "test" or "hi".
            
        - The server's "hello" counter does not change.
            
        - The server replies: `Please keep saying 'hello'.`
            
    - **Case 3: Client sends "hello" (and the limit is reached):**
        
        - The client sends the final "hello" required (e.g., 5500th "hello").
            
        - The server's state for this client changes.
            
        - The server replies: `Whats wrong with you?`
            
4. **Final Message Phase:**
    
    - The server now expects one final message from the client (this message can be anything).
        
    - The client sends their desired message (e.g., "This is my final message.").
        
    - The server receives this message (and can save it, or print it to its console).
        
5. **Termination:**
    
    - Immediately after receiving the final message, the server closes the socket connection for that client.
        
    - The main server thread continues to listen for new client connections.

---

### ⚠️ Important: Server Address Configuration

Before running the client (TCPClientAuto.java), you must edit the file to set the correct server address.

Locate the serverAddress variable and change its value:

- If you are running the server on your local machine, set the value to "localhost".

- Otherwise, set it to the IP address of the machine where the server is running.
        
