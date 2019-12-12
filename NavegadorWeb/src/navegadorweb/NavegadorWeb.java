package navegadorweb;
import java.net.*;
import java.io.*;

public class NavegadorWeb {
	
	private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    
    public NavegadorWeb () {

    }
    
    public void startConnection(String ip, int port) {
        try {
			clientSocket = new Socket(ip, port);
	        out = new PrintWriter(clientSocket.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	        
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) {
	        	if (".".equals(inputLine)) {
	            out.println("good bye");
	         }
	        }	
	         out.println(inputLine);
	        
	        
	        in.close();
	        out.close();
	        clientSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}				
    }
 
    @SuppressWarnings("finally")
	public String sendMessage(String msg) {
        out.println(msg);
        String resp = "";
		try {
			resp = in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
	        return resp;
		}
    }
		
		
//	    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException{
//	        InetAddress host = InetAddress.getLocalHost();
//	        NavegadorWeb navegadorWeb = new NavegadorWeb();
//	        navegadorWeb.startConnection(host.getHostName(), 8080);
//	    }
    

	

	
	
	// https://cs.lmu.edu/~ray/notes/javanetexamples/
	// http://zetcode.com/java/socket/
	// https://www.baeldung.com/a-guide-to-java-sockets
}
