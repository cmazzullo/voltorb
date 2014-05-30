import java.io.*;

public class EchoServerRunner {

    public static void main(String[] args) 
        throws IOException {
        EchoServer server = new EchoServer();
        server.startServer(9393);
    }
}
