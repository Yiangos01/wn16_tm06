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
		for(int i=0;i<10;i++){
			new Thread(new userThread(userId,port,Ip())).start();
			userId++;
		}

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

class userThread extends Thread{
	private int userId,port;
	private String ip;

	userThread(int id,int port,String ip){
		this.userId=id;
		this.port=port;
		this.ip=ip;
	}

	public void run(){
		Socket cl;
		try {
			cl = new Socket(InetAddress.getByName("localhost"),1000);

			int request=0;
			while(request<300){

				//System.out.println(request);
				//send HELLO
				OutputStream os = cl.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				String message="HELLO "+userId+" "+" "+ip+" "+port+"\n";
				long startTime=System.nanoTime();
				bw.write(message);

				bw.flush();

				System.out.println(userId);
				//Get the return message from the server
				InputStream is = cl.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String test=br.readLine();
				long endTime=System.nanoTime();
				double seconds = (double)(endTime -startTime)/ 1000000000.0;
				System.out.println(seconds+"  "+test.split(" ")[2]);
				request++;
			}
			cl.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}



