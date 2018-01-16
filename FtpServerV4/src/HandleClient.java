import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class HandleClient extends Thread{

    Socket clSocket ;
    ServerLog sl;

    public HandleClient(Socket socket, ServerLog serverlog){
        super();
        clSocket = socket;
        sl=serverlog;
    }

    @Override
    public void run()
    {
        System.out.println("Received connection from: " +
                clSocket.getRemoteSocketAddress().toString() );
        try {
            sl.serverLog( "new FTP connection established " );
            sl.serverLog( "new connection established from: " + clSocket.getRemoteSocketAddress().toString());
        } catch (IOException e) {
            ServerLog sl = null;
            try {
                sl = new ServerLog();
                sl.serverLog( e.getMessage() );
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        System.out.println( "do you want to accept it ? \n 1.accept \n 2.reject \n choose the number." );
        Scanner scan = new Scanner( System.in );
        String choice = scan.nextLine();
        if (choice.equals("1")){
                       try {
                           sl.serverLog( "the connection from: " + clSocket.getRemoteSocketAddress().toString() + " has been accepted");
                               MetaData meta = new MetaData( clSocket , sl);
                       } catch (IOException e) {
                           try {
                               ServerLog sl = new ServerLog();
                               sl.serverLog( e.getMessage() );
                           } catch (IOException e1) {
                               e1.printStackTrace();
                           }
                           ;
                       }
        }
        else {

            DataOutputStream outToClient = null;
            try {
                sl.serverLog( "the connection from: " + clSocket.getRemoteSocketAddress().toString() + "has been rejected");
                outToClient = new DataOutputStream(clSocket.getOutputStream());
                outToClient.writeBytes("don't send please. \n");
                System.out.println( "you rejected the connection \n listening to other connections " );
                outToClient.flush();
            } catch (IOException e) {
                ServerLog sl = null;
                try {
                    sl = new ServerLog();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                try {
                    sl.serverLog( e.getMessage() );
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }
}