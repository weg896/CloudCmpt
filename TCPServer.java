import java.io.*;
import java.net.*;

public class TCPServer {

	private ServerSocket serverSocket = null;
	private int connectedPort = -1;

	public TCPServer(int port){
		createServerSocket(port);
	}

	public void createServerSocket(int port){
		if(null == this.serverSocket){
			try{
				this.serverSocket = new ServerSocket(port);
				this.connectedPort = port;
			}catch(IOException e){
				System.out.println("server creation (port:" + port + ")fail: "+e.toString());
				this.serverSocket = null;
				this.connectedPort = -1;
			}
		}
	}

	public void doConnunication(){
		if(null != this.serverSocket){
			try{
				Socket connectionSocket = serverSocket.accept();
				BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
				
				String clientSentence = inFromClient.readLine();
				System.out.println("Received: "+clientSentence);
				outToClient.writeBytes(clientSentence);
			}catch(IOException e){
				System.out.println("Error: "+e.toString());
			}
		}
	}


	public void destoryServerSocket(){
		if(null != this.serverSocket){
			try{
				this.serverSocket.close();
			}catch(IOException e){
				System.out.println("server close error: "+e.toString());
			}finally{
				this.serverSocket = null;
				this.connectedPort = -1;
			}
		}
	}


	public int getSocketPort(){
		return this.connectedPort;
	}

	public static void main(String[] args){
	
	}
}
