import java.io.*;
import java.net.*;

public class EchoClient
{
    public void startClient(int port) {
        try (
             Socket client = new Socket("localhost", port);
             PrintWriter out =
             new PrintWriter(client.getOutputStream());
             BufferedReader in = 
             new BufferedReader(new InputStreamReader(client.getInputStream()));
             BufferedReader stdIn = 
             new BufferedReader(new InputStreamReader(System.in));
             )
                {
                    String line = null;
                    String response = null;
                    while ((line = (stdIn.readLine())) != null) {
                        out.println(line);
                        response = in.readLine();
                        System.out.println(response);
                    }

                } catch (Exception e) {
            System.out.println("Couldn't connect: " + e.getMessage());
        }
    }

    public static void main(String[] args)
    {
        EchoClient client = new EchoClient();
        client.startClient(9393);
    }
}
