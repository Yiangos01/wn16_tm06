import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
	
	public static void main(String[] args) throws IOException{

			ServerSocket servSocket = new ServerSocket(1000);
			Socket socket = servSocket.accept();
			while(true)
			{
				
				
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String message = br.readLine();
				System.out.println(message);
				String responce[]=message.split(" ");
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				bw.write("Welcome "+responce[1]+"\n");
				bw.flush();
				
			}

	}

}
