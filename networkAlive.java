import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class networkAlive extends Thread{
  public void start(Socket conn) throws Exception {
    while(true) {
      Scanner scanin = new Scanner(conn.getInputStream());
      String line=null;
      int nlines=0;
      String stringArray[] = new String[32];
      String returnString = "";
      String errorCode = "HTTP/1.0 200 Found\r\n" +
		"Connection: close\r\n" +
		"Content-Type: text/html\r\n" +
		"\r\n" +
		"<h1>File Found</h1>\r\n";
      while (true) {
        line = scanin.nextLine();
       if(line.length()==0) break;
       stringArray[nlines] = line;
        nlines = nlines + 1;
        System.out.println("line "+nlines+": "+line);
      }
      Scanner scanOut = new Scanner(stringArray[0]);
      String command = scanOut.next();
      if (!command.equals("GET")) {
		  errorCode = "HTTP/1.0 501 Not Implemented\r\n" +
		  "Connection: close\r\n" +
		  "Content-Type: text/html\r\n" +
		  "\n\r" +
		  "<h1> Feature not Implemented </h1\r\n";
		   }
      String resource = scanOut.next();
      String fileName = "www" + resource;
	try{
		File newFile = new File(fileName); 
		InputStream fileIn = new FileInputStream(newFile);
		Scanner fileScanner = new Scanner(fileIn);
		byte[] lineA = new byte[2564];
		int count=0;
		while(true) {
		if (fileScanner.hasNextLine()) {
				returnString = returnString + 
				"\n\r" + fileScanner.nextLine(); 
				} else {
					break;
				}
			count++;
		}
		}
	catch (FileNotFoundException e){
		errorCode = "HTTP/1.0 404 Not Found\r\n" +
		"Connection: close\r\n" +
		"Content-Type: text/html\r\n" +
		"\r\n" +
		"<h1>This file does not exist</h1>\r\n";
		}
	catch (NoSuchElementException e) {
	}
	returnString = errorCode + returnString;
	OutputStream outs = conn.getOutputStream();
	outs.write(returnString.getBytes());
	outs.close();
	conn.close();
	}	
}
}


