import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.Random;

public class TCPServer {
    public static void main(String[] args) {
        int port = 6789;
        List<Integer> saved = new ArrayList<>(); 

        // try-with-resources
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started, port " + port + " listening...");

            while (true) {
                System.out.println("Waiting for a new client...");

                Random random = new Random();
                int count = random.nextInt(50000);
                
                try (
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
                ) {
                    
                    System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    
                    
                    int helloCount = 0;
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Response from Client (" + clientSocket.getPort() + "): " + inputLine);

                        if ("hello".equals(inputLine)) {
                            helloCount++;

                            if (helloCount == count) {
                                out.println("Whats wrong with you?");
                            } else {
                                out.println(helloCount + "th 'hello'");
                            }

                        } else if (helloCount >= count) {
                            try {
                                saved.add(Integer.parseInt(inputLine));
                                out.println("Saved, Goodbye!");
                                break; 
                            } catch (NumberFormatException e) {
                                out.println("It is not a number. Please enter a number!");
                            }

                        } else {
                            out.println("Please keep saying 'hello'. (" + helloCount + "/" + count + ")");
                        }
                    }
                    System.out.println("Server (" + clientSocket.getPort() + ") connection closed.");

                } catch (IOException e) {
                    System.out.println("Client Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Server Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}