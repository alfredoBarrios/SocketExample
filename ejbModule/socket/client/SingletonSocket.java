package socket.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
/*
 * Author: Alfredo Barrios
 * 
 * */
@Singleton
@Startup
public class SingletonSocket {
   
	private static SingletonSocket instance=null;
    private static Socket socket=null;
    private static BufferedReader in;
    private static BufferedWriter out;
    private IOException exception;
    @PostConstruct
    void init(){
    	instance=new SingletonSocket();
    }
    @PreDestroy
    void destroy(){
    	if(socket!=null)
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    SingletonSocket() {
        String host ="localhost";
        int port = 3000;
        try {
            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            exception = e;    
        }
    }
    public static synchronized SingletonSocket getInstance(){
    	if(instance==null)
    		instance=new SingletonSocket();

    	return instance;
    	
    }
    public IOException getException() {
        return exception;
    }
 
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }
 
    public void close() throws IOException {
        if (socket != null)
            socket.close();
    }
    public boolean isClosed(){
    	if(socket==null)
    		return true;
    	return socket.isClosed();
    }
 
    public String readLine() throws IOException {
        if (!isConnected()) {throw new IllegalStateException("Socket is not connected."); }
        String r=in.readLine();
       
        return r;
    }
 
    public void writeLine(String line) throws IOException {
    	if (!isConnected()) {throw new IllegalStateException("Socket is not connected."); }
        synchronized (out) {
            out.write(line);
            out.newLine();
            out.flush();
            
        }
    }
}