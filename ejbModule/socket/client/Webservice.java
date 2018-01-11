package socket.client;

import java.io.IOException;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * Author: Alfredo Barrios
 * */
@Stateless
@WebService(serviceName="PruebaWs")
public class Webservice {
   @EJB
   SingletonSocket sock=null;
  
   
   @WebMethod(operationName="call")
   public String getRespuesta()  {  
	   	String resp="";
		
			
			try {
				sock=SingletonSocket.getInstance();
				sock.writeLine("g");
				resp=sock.readLine();
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  return resp;
   }


}
