import java.io.*;
import java.net.*;
/* This class creates multiple threads and copies of itself to run
 * in those threads
 */
public class EchoServer extends TCPServer {

    public void run (Socket data) {
        try (
             PrintWriter out = 
             new PrintWriter(data.getOutputStream(), true);
             BufferedReader in = 
             new BufferedReader(new InputStreamReader(data.getInputStream()));
             BufferedReader stdIn = 
             new BufferedReader(new InputStreamReader(System.in));
             ) {
                out.println("Hello, socket!");
            } catch (Exception e) {}
    }
}
