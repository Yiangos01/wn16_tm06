import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Server{

	public static void main(String[] args) throws IOException{
		
		int port = Integer.parseInt(args[0]);
		ServerSocket servSocket = new ServerSocket(port);
		System.out.println(Ip());
		while(true){
		Socket socket = servSocket.accept();
		new Thread(new response(socket)).start();
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

class response extends Thread{
	Socket cSocket;
	response(Socket cSocket){
		this.cSocket = cSocket;
		System.out.println("Thread start");

	}

	public void run(){
		int i=0;
		while(true){
		InputStream is;
		try {
			//Get the requests message from the client
			is = cSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			System.out.println(message);
			if(message==null){
				//Thread.interrupted();
				System.out.println(i);
				break;
			}
			
			String responce[]=message.split(" ");
			OutputStream os = cSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			int payload =(int)((System.nanoTime()%1700) +300)*1000;
			char data[]=new char[payload/2];
			bw.write("Welcome "+responce[1] +" "+ data.length/1000 +" "+String.valueOf(data)+"\n");
			bw.flush();
			i++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
	}
	public String toStringByte(Byte[] data){
		String temp =" ";
		for(int i=0; i<data.length;i++){
			temp +=data[i];
		}
		System.out.println("forr");
		return temp;
	}

}


