import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
	//private static Socket socket;

	public static void main(String[] args) throws UnknownHostException, IOException{
		double[] latencyUser = new double[100];
		int userId=0;
		String serverIp =args[0];
		int port =Integer.parseInt(args[1]);
		for(int i=0;i<5;i++){
			new Thread(new userThread(userId,port,Ip(),serverIp,latencyUser)).start();
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
	double[] latencyUser;
	userThread(int id,int port,String ip,String serverIp, double[] latencyUser){
		this.userId=id;
		this.port=port;
		this.clientip=ip;
		this.serverIp= serverIp;
		this.latencyUser=latencyUser;
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
			bw.write(message);
			bw.flush();
			latencyUser[userId]=latency/300;
			System.out.println("Socket closed for Client "+userId+" latency "+latency/300);
			cl.close();
			
			double sum=0;
			for(int i=0;i<5;i++){
				sum+=latencyUser[i];
			}
			double average = sum/5; 
			System.out.println("average "+ average);
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Socket closed for Client "+userId+" latency "+latency+" for "+request+" request");
			System.out.println("The server is down ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Socket closed for Client "+userId+" latency "+latency+" for "+request+" request");
			System.out.println("The server is down ");
		}


	}
}





