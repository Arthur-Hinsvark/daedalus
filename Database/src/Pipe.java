import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.List;


public class Pipe {
	
	private final String SERVER_URL = "http://ec2-52-87-214-34.compute-1.amazonaws.com/";
	
	public Pipe(){
		
	}
	
	public void download(String file, String moveTo){
		try(
				  ReadableByteChannel in=Channels.newChannel(
				    new URL(SERVER_URL + "/uploads/" + file).openStream());
				  FileChannel out=new FileOutputStream(
				    moveTo).getChannel() ) {

				  out.transferFrom(in, 0, Long.MAX_VALUE);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	}
	
	public void upload(String file){
		String charset = "UTF-8";
        File uploadFile1 = new File(file);
        String requestURL = SERVER_URL + "/upload.php";
 
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addFormField("uploadedfile", file);
            multipart.addFilePart("uploadedfile", uploadFile1);
            
            List<String> response = multipart.finish();
             
            System.out.println("SERVER REPLIED:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
		
		

		
	}
	
	public static void main(String[] args){
		Pipe p = new Pipe();
		p.upload("C:\\Users\\Arthur\\Desktop\\creative.png");
		
	}
}
