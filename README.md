# Multi-Threaded-Web-Server Part A

First, implementation of the Web server will be multi-threaded, where the processing of each incoming request will take place inside a separate thread of execution. This allows the server to service multiple clients in parallel, or to perform multiple file transfers to a single client in parallel. When we create a new thread of execution, we need to pass to the Thread's constructor an instance of some class that implements the Runnable interface. This is the reason that we define a separate class called HttpRequest. 

## Develop the Web server class
Normally, Web servers process service requests that they receive through well-known port number 80. You can choose any port higher than 1024, but remember to use the same port number when making requests to your Web server from your browser.

Next, we open a socket and wait for a TCP connection request. Because we will be servicing request messages indefinitely, we place the listen operation inside of an infinite loop. This means we will have to terminate the Web server by pressing ^C on the keyboard.


When a connection request is received, we create an HttpRequest object, passing to its constructor a reference to the Socket object that represents our established connection with the client.

//	Construct an object to process the HTTP request message. 

*HttpRequest request = new HttpRequest( ? );*

//	Create a new thread to process the request.

*Thread thread = new Thread(request);*

// Start the thread. 

*thread.start();*


In order to have the HttpRequest object handle the incoming HTTP service request in a separate thread, we first create a new Thread object, passing to its constructor a reference to the HttpRequest object, and then call the thread's start() method.

After the new thread has been created and started, execution in the main thread returns to the top of the message processing loop. The main thread will then block, waiting for another TCP connection request, while the new thread continues running. When another TCP connection request is received, the main thread goes through the same process of thread creation regardless of whether the previous thread has finished execution or is still running.

This completes the code in main(). 

## Develop the HttpRequest class

We declare two variables for the HttpRequest class: CRLF and socket. According to the HTTP specification, we need to terminate each line of the server's response message with a carriage return (CR) and a line feed (LF), so we have defined CRLF as a convenience. The variable socket will be used to store a reference to the connection socket, which is passed to the constructor of this class.

In order to pass an instance of the HttpRequest class to the Thread's constructor, HttpRequest must implement the Runnable interface, which simply means that we must define a public method

called run() that returns void. Most of the processing will take place within processRequest(), which is called from within run().

Up until this point, we have been throwing exceptions, rather than catching them. However, we cannot throw exceptions from run(), because we must strictly adhere to the declaration of run() in the Runnable interface, which does not throw any exceptions. We will place all the processing code in processRequest(), and from there, throw exceptions to run(). Within run(), we explicitly catch and handle exceptions with a try/catch block.

Now, develop the code within processRequest(). We first obtain references to the socket's input and output streams. Then we wrap InputStreamReader and BufferedReader filters around the input stream. However, we won't wrap any filters around the output stream, because we will be writing bytes directly into the output stream.

Then we are prepared to get the client's request message, which we do by reading from the socket's input stream. The readLine() method of the BufferedReader class will extract characters from the input stream until it reaches an end-of-line character, or in our case, the end-of-line character sequence CRLF.

The first item available in the input stream will be the HTTP request line.

// Get the request line of the HTTP request message. 

*String requestLine = ?;*

After obtaining the request line of the message header, we obtain the header lines. Since we don't know ahead of time how many header lines the client will send, we must get these lines within a looping operation.

// Get and display the header lines. 

*String headerLine = null;*

_while ((headerLine = br.readLine()).length() != 0) { System.out.println(headerLine);_

_}_


We don't need the header lines, other than to print them to the screen, so we use a temporary String variable, headerLine, to hold a reference to their values. The loop terminates when the expression

_(headerLine = br.readLine()).length()_

evaluates to zero, which will occur when headerLine has zero length. This will happen when the empty line terminating the header lines is read. (See the HTTP Request Message diagram in Section 2.2 of the textbook)

In the next step of this lab, we will add code to analyze the client's request message and send a response. But before we do this, let's try compiling our program and testing it with a browser. Add the following lines of code to close the streams and socket connection.

// Close streams and socket. os.close();

_br.close();_
_socket.close();_


After your program successfully compiles, run it with an available port number, and try contacting it from a browser. To do this, you should enter into the browser's address text box the IP address of your running server. For example, if your machine name is host.someschool.edu, and you ran the server with port number 6789, then you would specify the following URL: http://host.someschool.edu:6789/

