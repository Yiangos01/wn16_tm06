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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import com.sun.management.OperatingSystemMXBean;
public class Server{
	
	public static void main(String[] args) throws IOException{
		
		int repetitions = Integer.parseInt(args[1]);
		global g= new global(repetitions);
		int port = Integer.parseInt(args[0]);
		long throuput[]=new long[3];
		long nowMillis = System.currentTimeMillis();
		CPUTimer.start();
		ServerSocket servSocket = new ServerSocket(port);
		System.out.println(Ip());
		while(g.reps()){
		Socket socket = servSocket.accept();  
		new Thread(new response(socket,g,throuput,System.currentTimeMillis())).start();
		System.out.println(System.currentTimeMillis());
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

class global{
	private int repets;
	global(int repetitions){
		this.repets=repetitions;
	}
	public boolean reps(){
		if(this.repets!=0){
			this.repets--;
			return true;
		}
		else return false;
	}
	public int gexR(){
		return this.repets;
	}
}

class response extends Thread{
	Socket cSocket;
	global g;
	long[] thr;
	long mill;
	response(Socket cSocket,global g,long[] thr,long mill){
		this.cSocket = cSocket;
		this.g=g;
		this.thr=thr;
		this.mill=mill;
		System.out.println("Thread start");

	}


	public void run(){
		double throup=0;
		while(g.reps()){
		InputStream is;
		double CPU=0;
		OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
		try {
			//Get the requests message from the client
			is = cSocket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String message = br.readLine();
			//System.out.println(message);
			if(message.equals("END")){
				//this.stop();
				throup=thr[0]/(double)((System.currentTimeMillis()-mill )/1000);
				System.out.println("memory utilization "+thr[1]/(throup*(int)(System.currentTimeMillis()-mill)));
				System.out.println(" CPU load: " + thr[2]/(throup*(int)(System.currentTimeMillis()-mill)));
				System.out.println("Throuput : "+throup);
				break;
			}
			
			String responce[]=message.split(" ");
			OutputStream os = cSocket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			int payload =(int)((System.nanoTime()%1700) +300)*1000;
			char data[]=new char[payload/2];
			bw.write("Welcome "+responce[1] +" "+ data.length/1000 +" "+String.valueOf(data)+"\n");
			thr[0]+=1;
			// Get the Java runtime
	        Runtime runtime = Runtime.getRuntime();
	        // Run the garbage collector
	        runtime.gc();
	        // Calculate the used memory
	        long memory = (long)(runtime.maxMemory() - runtime.freeMemory());
	        //System.out.println(memory);
	        thr[1]+=memory;
	        //System.out.println("Used memory is bytes: " + memory);	
	        thr[2]+= operatingSystemMXBean.getSystemCpuLoad();
			//System.out.println("Throuput : "+throup);
			//System.out.println("Memory : "+(thr[1]/(throup*(int)(System.currentTimeMillis()-mill))));
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
	}
	
}
class CPUTimer {
    private static long _startTime = 0l;

    public static void start (){
        _startTime = getCpuTimeInMillis();
    }


    public static long stop (){
        long result = (getCpuTimeInMillis() - _startTime);
        //_startTime = 0l;
        return result;
    }

    public boolean isRunning (){
        return _startTime != 0l;
    }

    /** thread CPU time in milliseconds. */
    private static long getCpuTimeInMillis (){
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime()/1000000: 0L;
    }
}

