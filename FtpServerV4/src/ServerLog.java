import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class ServerLog {

    private static final DateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    BufferedWriter writerLog = new BufferedWriter(new FileWriter("ServerLog.txt", true));

    public ServerLog() throws IOException {

    }

    public void serverLog(String str) throws IOException {
    Date date = new Date();
    String message = (str + " - " + sdf.format(date) );
    writerLog.write(message + "\n");
    writerLog.flush();
    //writerLog.close();
    }
}