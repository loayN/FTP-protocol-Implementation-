import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SaveFile {

   Socket flSocket;
   String fileNameToSave , fileSizeToSave;
   ServerLog sl;
   public SaveFile(String fileName , String fileSize , Socket cs, ServerLog serverlog) throws IOException {
       flSocket = cs;
       fileNameToSave = fileName;
       fileSizeToSave = fileSize;
       sl = serverlog;
       //System.out.println( "im in save file" );
       saveFile(fileNameToSave,fileSizeToSave,flSocket,sl);

   }

    void saveFile(String fn, String fs, Socket flSocket, ServerLog sl) throws IOException {


        DataInputStream dis = new DataInputStream( flSocket.getInputStream());
        FileOutputStream fos = new FileOutputStream( fn);
        byte[] buffer = new byte[4096];
        int filesize = Integer.valueOf( fs );
        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        int n = 0;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            n++;
            System.out.println("packet NO."+ n +" read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);

        }
        sl.serverLog( "The file " + fn +" from: " + flSocket.getRemoteSocketAddress().toString() + " - " + n + "the total number of packets is "+ n);
        fos.flush();
        fos.close();
        dis.close();
        System.out.println( "\n waiting for other connections." );

    }

}
