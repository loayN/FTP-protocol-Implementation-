import java.io.*;
import java.net.*;

public class FTPServer {

    public static void main(String[] args) {
            int port = 1993;
            ServerSocket listener = null;
            try {
                listener =
                        new ServerSocket(port, 50,
                                InetAddress.getByAddress(new byte[] {0,0,0,0}));
                System.out.println("Starting to listen on: " +
                        listener.getLocalSocketAddress().toString());

                while (true){
                    Socket client = listener.accept();
                    ServerLog sl = new ServerLog();
                    HandleClient clientThread = new HandleClient(client,sl);
                    clientThread.start();
                }

            } catch (IOException e) {
                e.printStackTrace();


            }
            try { listener.close(); } catch (IOException ex){}
        }

    }
