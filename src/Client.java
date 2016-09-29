import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class Client {
	//private static Socket socket;

	public static void main(String[] args) throws UnknownHostException, IOException{

		int userId=1;
		int port = 1000;


		Socket cl = new Socket(InetAddress.getByName("localhost"),1000);
		int request=0;
		while(request<300){
			System.out.println("sdadsa");
			//send HELLO
			OutputStream os = cl.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			String message="HELLO "+userId+" "+" "+Ip()+" "+port+"\n";
			bw.write(message);
			
			bw.flush();

			//Get the return message from the server
			InputStream is = cl.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			System.out.println(br.readLine());
			request++;

		}
		cl.close();
	}




	public static String Ip() {

		InetAddress ip;
		try {

			ip = InetAddress.getLocalHost();
			return ip.getHostAddress();

		} catch (UnknownHostException e) {

			e.printStackTrace();

		}
		return "";
	}
}
