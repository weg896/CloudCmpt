import java.io.*;

public class CentralServer{
    
    private TCPServer[] chatRoomList = null;
    private final int CHAR_ROOM_OFFSET = 8080;

    public CentralServer(){
        if(null == this.chatRoomList){
            chatRoomList = new Array();
        }
    }


	public void createChatRoom(){
        TCPServer tempServer = null;
        int newPort = this.CHAR_ROOM_OFFSET + this.chatRoomList.length;
        tempServer = new TCPServer(newPort);
	}

	public void listChatRoom(){
        foreach()
	}

	public void joinChatRoom(){

	}

	public void leaveChatRoom(){

	}
}