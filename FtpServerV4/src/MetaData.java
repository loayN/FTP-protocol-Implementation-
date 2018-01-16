import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class MetaData {
    Socket metaSocket;
    ServerLog sl;
    public MetaData(Socket socket , ServerLog serverlog) throws IOException {
        try{
            metaSocket = socket;
            sl = serverlog;
            extract(metaSocket , sl);
        }catch (Exception e){ sl = new ServerLog();
            sl.serverLog( e.getMessage() );}

    }

    public void extract(Socket metaSocket, ServerLog sl) throws IOException {
        String clientSentence;
        Socket connectionSocket = metaSocket;
        BufferedReader inFromClient =
                new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
        DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
        clientSentence = inFromClient.readLine();
        System.out.println("Received: " + clientSentence);
        sl.serverLog( "received from: " + metaSocket.getRemoteSocketAddress().toString() + " - " + clientSentence);


        /**
        ** extract the metadata from client message
        * */

        String recievedData[] ;
        recievedData = clientSentence.split( " " );
        String fileName = recievedData[0];
        String fileSize = recievedData[1];
        //System.out.println( fileSize );
        System.out.println( "do you want to save it ? \n 1.yes \n 2.no" );
        Scanner scan = new Scanner( System.in );
        String choice = scan.nextLine();
        if(choice.equals( "1" )){
            sl.serverLog( "The file received from: " + metaSocket.getRemoteSocketAddress().toString() + " - " + clientSentence + " has been approved by the server manager to save it");
            outToClient.writeBytes("OK, send it \n");
            outToClient.flush();
            SaveFile sf = new SaveFile(fileName,fileSize,connectionSocket,sl);
        }
        else {

            try {
                sl.serverLog( "The file received from: " + metaSocket.getRemoteSocketAddress().toString() + " - " + clientSentence + " has been denied by the server manager to save it");
                outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeBytes(" the file will not be saved.\n");
                outToClient.flush();
                System.out.println( "listening to other connections." );
            } catch (IOException e) {
                sl = new ServerLog();
                sl.serverLog( e.getMessage() );
            }

        }

    }

}



