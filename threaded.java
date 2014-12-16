import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class threaded {
  public static void main(String[] args) throws Exception {
	int count = 0;
	ServerSocket serverSock = new ServerSocket(Integer.parseInt(args[0]));
	while(true) {
		try{
			Socket conn = serverSock.accept();
			if (!conn.isClosed()) {
				networkAlive c1 = new networkAlive();
				count++;
				System.out.println("Threads: " + count);
				c1.start(conn);
			}
		}
		catch(IOException e){
		}
	}
}
}

