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
            this.connectionSocket = serverSocket.accept();
            this.inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            this.outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            this.isConnection = true;

            this.tempThread = new TCPConnectionListener(this.serverSocket);

            String clientSentence = "";
            out

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

	public void createChatRoom(){
        TCPServer tempServer = null;
        int newPort = this.CHAR_ROOM_OFFSET + this.chatRoomList.length;
        tempServer = new TCPServer(newPort);
	}

	public void listChatRoom(){

	}

	public void joinChatRoom(){

	}

	public void leaveChatRoom(){

    }
    
    public void helpMenu(){
        String message = ""
    }

 }