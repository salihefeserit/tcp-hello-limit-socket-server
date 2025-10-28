import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPClientAuto {

    public static void main(String[] args) {
        String serverAddress = "192.168.1.33";
        int port = 5500;                

        try (
            Socket socket = new Socket(serverAddress, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleIn = new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
            System.out.println("Server Connection Established.");
            String response;
            String userInput;

            while (true) {
                out.println("hello");
                Thread.sleep(1);

                response = in.readLine();

                if (response == null) {
                    System.out.println("The server disconnected unexpectedly.");
                    break;
                }
                
                System.out.println("Server Response: " + response);
                
                if ("Whats wrong with you?".equals(response)) {
                    break;
                }
            }
            userInput = consoleIn.readLine();
            out.println(userInput);

        } catch (Exception e) {
            System.out.println("Client Error: " + e.getMessage());
        }
        
        System.out.println("Client program is ended.");
    }
}