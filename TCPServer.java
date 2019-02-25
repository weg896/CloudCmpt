import java.io.*;
import java.net.*;
import java.lang.Thread;

public class TCPServer {

	private ServerSocket serverSocket = null;

	public TCPServer(){
		createServerSocket();
	}

	public ServerSocket getServerSocket(){
		return this.serverSocket;
	}

	public void createServerSocket(){
		if(null == this.serverSocket){
			try{
				this.serverSocket = new ServerSocket(GlobalValue.DEFAULT_PORT);
			}catch(IOException e){
				System.out.println("server creation (port:" + GlobalValue.DEFAULT_PORT + ")fail: "+e.toString());
				this.serverSocket = null;
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
			}
		}
	}

	public static void main(String[] args){
		System.out.println("system start");
		TCPServer tcp = new TCPServer();

		TCPConnectionListener listener = new TCPConnectionListener(tcp.getServerSocket());
		listener.start();
		
	}
}
