package uofm.sectionb02.id7876069.parta;

import java.io.*;
import java.net.*;
import java.util.*;


final class HttpRequest implements Runnable {
    
    final static String CRLF = "\r\n";
    Socket socket = null;
    
    // Constructor
    public HttpRequest(Socket socket) throws Exception {
        this.socket = socket;
    }
    
    // Implement the run() method of the Runnable interface
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    // Does the actual processing of the request
    private void processRequest() throws Exception {
        // Get a reference to the socket's input and output streams
        InputStream is = socket.getInputStream();;
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        
        // Set up input stream filters
        
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        // Get the request line of the HTTP request message
        String requestLine = br.readLine();
        
        // Display the request line
        System.out.println();
        System.out.println(requestLine);
        
        // Get and display the header lines
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }
        
        String statusLine = null;
		// Send the status line
        os.writeBytes(statusLine);
        String contentTypeLine = null;
		// Send the content type line
        os.writeBytes(contentTypeLine);
        //Send a blank line to indicate the end of the header lines
        os.writeBytes(CRLF);
        
        os.close();
		br.close();
		socket.close();
		
		
    }
    
    
    
  
 
}
