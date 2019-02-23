/**
 * This class is inheritance from java.lang.Thread
 * For creating a new thread so that can listening to client connection signal
 * and won't block other clients communication
 */

 import java.lang.Thread;
 import java.io.*;
 import java.net.*;

 public class TCPConnectionListener extends Thread{
    private Socket connectionSocket = null;
    private ServerSocket serverSocket = null;
    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;
    private boolean isConnection = false;
    private TCPConnectionListener tempThread = null;

    public TCPConnectionListener(ServerSocket ss){
        this.serverSocket = ss;
    }

    public void run(){
        try{
            // wait for connection
            this.connectionSocket = serverSocket.accept();
            this.inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            this.outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            this.isConnection = true;

            // prepare for next connection
            this.tempThread = new TCPConnectionListener(this.serverSocket);

            // predefine 
            String clientSentence = "";
            this.outToClient.writeBytes("welcome to Chat Hall!\n");

            this.helpMenu();
            // communication
            while(true){

                clientSentence = this.inFromClient.readLine();
            


            }
        }catch(IOException e){
            this.connectionSocket = null;
            this.serverSocket = null;
            this.inFromClient = null;
            this.outToClient = null;
        }

    }

    public boolean isConnectionCreated(){
        return this.isConnection;
    }

	public void listChatRoom(){
        try
	}

	public void joinChatRoom(){

	}

	public void leaveChatRoom(){

    }
    
    public void helpMenu(){
        try{
            this.outToClient.writeBytes("you can type '-h' call help menu \n");
            this.outToClient.writeBytes("you can type '-c chatRoomName' to create a chat room \n");
            this.outToClient.writeBytes("you can type '-j chatRoomName' to enter or join a chat room \n");
            this.outToClient.writeBytes("you can type '-l' to list all chat room \n");
            this.outToClient.writeBytes("you can type '-z' to leave chat room \n");
        }catch(IOException e){
            this.connectionSocket = null;
            this.serverSocket = null;
            this.inFromClient = null;
            this.outToClient = null;
        }
    }

    public void brocastMessage(String message){
        try{
            this.outToClient.writeBytes(message); 
        }catch(IOException e){

        }
    }

    public int detectInputType(String message){
        if(){

        }
    }

 }