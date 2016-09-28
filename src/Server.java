import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{
  
    public static void main(String[] args) throws IOException{
    
    	ServerSocket serSocket =new ServerSocket(1000);	
    	Socket s = serSocket.accept();
    	Scanner scanner = new Scanner(s.getInputStream());
    	String Hello = scanner.next();
    	String userId = scanner.next();
    	String ip = scanner.next();
    	String port = scanner.next();
    	PrintStream stream = new PrintStream(s.getOutputStream());
    	stream.print("Hello");
    	System.out.println(Hello);

    }
}