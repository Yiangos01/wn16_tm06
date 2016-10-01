import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
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
		String serverIp =args[0];
		int port =Integer.parseInt(args[1]);
		for(int i=0;i<5;i++){
			new Thread(new userThread(userId,port,Ip(),serverIp)).start();
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
	private String clientip,serverIp;
	private double latency=0;
	userThread(int id,int port,String ip,String serverIp){
		this.userId=id;
		this.port=port;
		this.clientip=ip;
		this.serverIp= serverIp;
	}

	public void run(){
		Socket cl;
		int request = 0;
		try {
			cl = new Socket(serverIp,port);

			request=1;
			while(request<=300){
								
				//send HELLO
				OutputStream os = cl.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				BufferedWriter bw = new BufferedWriter(osw);
				String message="HELLO "+clientip+" "+port+" "+userId+"\n";
				long startTime=System.nanoTime();
				bw.write(message);
				bw.flush();
			
				//Get the return message from the server
				InputStream is = cl.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(isr);
				String returnMessage=br.readLine();
				long endTime=System.nanoTime();
				double seconds = (double)(endTime -startTime)/ 1000000000.0;
				latency+=seconds;
				
				//System.out.println("Userid: " + userId+", request: "+request);
				//System.out.println("Latency: "+ seconds+" seconds, size payload: "+returnMessage.split(" ")[2]);
				request++;	
			}
			OutputStream os = cl.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			String message="END\n";
			long startTime=System.nanoTime();
			bw.write(message);
			bw.flush();
			System.out.println("Socket closed for Client "+userId+" latency "+latency);
			cl.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Socket closed for Client "+userId+" latency "+latency+" for "+request+" request");
			System.out.println("The server is down ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Socket closed for Client "+userId+" latency "+latency+" for "+request+" request");
			System.out.println("The server is down ");
		}


	}
}





