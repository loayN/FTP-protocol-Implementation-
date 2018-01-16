import java.io.*;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ftpClient {

    private Socket s;

    public ftpClient(String ip, int port, String strFile) {
        try {
            s = new Socket(ip, port);
            sendFile(strFile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }

    public void sendFile(String dir) throws IOException, URISyntaxException, InterruptedException {


        //metaMethod(dir);
        ////////////////////////////////

        String sentence;
        String recievedSentence;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader((metaData( dir ))));
        DataOutputStream outToServer = new DataOutputStream(s.getOutputStream());
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(s.getInputStream()));
        sentence = inFromUser.readLine();
        outToServer.writeBytes(sentence + '\n');
        recievedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + recievedSentence);
        /////////////////////////

        if(recievedSentence.equals( "OK, send it " )) {
            DataOutputStream dos = new DataOutputStream( s.getOutputStream() );
            //TimeUnit.SECONDS.sleep(5);
            FileInputStream fis = new FileInputStream( dir );
            byte[] buffer = new byte[4096];

            while (fis.read( buffer ) > 0) {
                dos.write( buffer );
            }

            fis.close();
            dos.close();
            dos.flush();
        }
        else{
            System.out.println( "your request to upload was denied. sorry \n" );
        }
        System.out.println( "send another if you want !! \n" );
        run();
    }

    /**
     * metaData method used for:
     * get the file name and type
     * get the size of the file
     * */
    private static InputStream metaData(String dir) throws IOException {
        File file = new File( dir );
        String[] filename = dir.split( "\\\\" );
        String fileName = filename[filename.length-1];
        System.out.print( fileName +" ");
        long size = file.length();
        System.out.println( size+ "bytes. \n waiting for server approval");
        String retData = (fileName + " " + size);
        InputStream stream = new ByteArrayInputStream(retData.getBytes());
        return stream;


    }


    public static void main(String[] args) throws IOException {
        run();
    }

    private static void run() throws IOException {
        System.out.println( "your options is :\n 1.send \n 2.quit\n insert your choice please:" );
        Scanner scan = new Scanner( System.in );
        String choice = scan.nextLine();
        if(choice.equals( "1" )) {
            System.out.println( "insert the file dir: " );
            String filePath = scan.nextLine();
            ftpClient fc = new ftpClient( "localhost", 1993, filePath );
        }
        else System.exit(0);

    }

}