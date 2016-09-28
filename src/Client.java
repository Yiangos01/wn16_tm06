import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class Client {
	
    public static void main(String[] args) throws UnknownHostException, IOException{
    	int userId = 1;
    	int port = 450;
    	String h = "Hello";
    	Socket cl = new Socket("127.0.0.1",1000);
    	Scanner scanner = new Scanner(cl.getInputStream());
    	PrintStream stream = new PrintStream(cl.getOutputStream());
    	stream.print(h+" "+userId+" "+" "+Ip()+" "+port);
    	System.out.println(scanner.next());
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
