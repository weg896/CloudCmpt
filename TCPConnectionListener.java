/**
 * This class is inheritance from java.lang.Thread
 * For creating a new thread so that can listening to client connection signal
 * and won't block other clients communication
 */

 import java.lang.Thread;
 import java.io.*;
 import java.net.*;
 import java.util.regex.Pattern;
 import java.util.regex.Matcher;

 public class TCPConnectionListener extends Thread{
    private Socket connectionSocket = null;
    private ServerSocket serverSocket = null;
    private BufferedReader inFromClient = null;
    private DataOutputStream outToClient = null;
    private boolean isConnection = false;
    private String currentChatRoom = null;
    private String currentUserName = null;
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
            this.tempThread.start();
        }catch(IOException e){
            System.out.println("initialize: " + e.getMessage());
        }

        // predefine 
        String clientSentence = "";
        try{
            this.outToClient.writeBytes("System: welcome to Chat Hall! \n");
            this.outToClient.writeBytes("System: please tell us your name \n");
            this.outToClient.writeBytes("System: name only contain letter and number and at least 2 character \n");
        }catch(IOException e){
            System.out.println("welcome: " + e.getMessage());
        }

        try{
            while(this.currentUserName == null){
                String tempStr = this.inFromClient.readLine();
                Pattern pattern = Pattern.compile("[a-zA-Z0-9]{2}");
                Matcher matcher = pattern.matcher(tempStr);

                if (matcher.find()){
                    this.currentUserName = matcher.group(0);
                    this.outToClient.writeBytes("System: hello "+ this.currentUserName +" \n");
                    break;
                }else{
                    this.outToClient.writeBytes("System: name only contain letter and number and at least 2 character \n");
                    this.outToClient.writeBytes("System: please tell us your name :\n");
                }
            }
        }catch(IOException e){
            System.out.println("getUserName: " + e.getMessage());
        }
        this.helpMenu();
        // communication
        while(true){
            try{
                clientSentence = this.inFromClient.readLine();

                int inputType = this.detectInputType(clientSentence);
                switch(inputType){
                    case 0:
                        this.brocastMessage(clientSentence);
                        break;
                    case 1:
                        this.helpMenu();
                        break;
                    case 2:
                        this.createChatRoom(clientSentence);
                        break;
                    case 3:
                        this.joinChatRoom(clientSentence);
                        break;
                    case 4:
                        this.listChatRoom();
                        break;
                    case 5:
                        this.leaveChatRoom();
                        break;
                    case 6:
                        this.leaveAllChatRoom();
                        this.outToClient.writeBytes("System: Bye Bye!\n");
                        return;
                    default:
                        this.outToClient.writeBytes("System: errrr\n");
                        break;
                }
            }catch(IOException e){
                System.out.println("infoConvert: " + e.getMessage());
            }
        }
    }

    public boolean isConnectionCreated(){
        return this.isConnection;
    }

    public void helpMenu(){
        try{
            this.outToClient.writeBytes("System: you can type '-h' call help menu \n"); //1
            this.outToClient.writeBytes("System: you can type '-c chatRoomName' to create a chat room \n"); //2
            this.outToClient.writeBytes("System: you can type '-j chatRoomName' to enter or join a chat room \n"); //3
            this.outToClient.writeBytes("System: you can type '-l' to list all chat room \n"); //4
            this.outToClient.writeBytes("System: you can type '-z' to leave chat room \n"); //5
            this.outToClient.writeBytes("System: you can type '-x' to leave chat System \n"); //6
            this.outToClient.flush();
        }catch(IOException e){
            System.out.println("help: " + e.getMessage());
        }
    }

    public void createChatRoom(String chatRoomTemp){
        String chatRoom;
        try{
            if(chatRoomTemp.length() <=3) {
                this.outToClient.writeBytes("System: Please type proper Chat Room name\n");
            }else{
                chatRoom = chatRoomTemp.substring(3);
                boolean isSuccess = ChatRoomTable.getChatRoomTable().craeteChatRoom(chatRoom);
                if(isSuccess){
                    this.outToClient.writeBytes("System: chat room - " + chatRoom +" is created\n");
                }else{
                    this.outToClient.writeBytes("System: chat room - " + chatRoom +" is already existed\n");
                }
            }
        }catch(IOException e){
            System.out.println("createChatRoom: " + e.getMessage());
        }
    }

	public void listChatRoom(){
        String tempStr = ChatRoomTable.getChatRoomTable().listAllChatRoom();
        try{
            this.outToClient.writeBytes("System: " + tempStr+"\n");
        }catch(IOException e){
            System.out.println("listChatRoom: " + e.getMessage());
        }
	}

	public void joinChatRoom(String chatRoomTemp){
        String chatRoom;
        try{
            if(chatRoomTemp.length() <=3) {
                this.outToClient.writeBytes("System: Please type proper Chat Room name\n");
            }else{
                chatRoom = chatRoomTemp.substring(3);
                if(this.currentChatRoom != null){
                    this.outToClient.writeBytes("System: Please leave Chat Room First\n");
                }else{
                    ChatRoomTable.getChatRoomTable().joinChatRoom(chatRoom, this.currentUserName, this);
                    this.currentChatRoom = chatRoom;
                    this.brocastMessage("System: "+this.currentUserName+" join chat Room - " + chatRoom);
                    this.outToClient.writeBytes("System: Welcome to Chat Room - " + chatRoom + "\n");
                }
            }
        }catch(IOException e){
            System.out.println("joinChatRoom: " + e.getMessage());
        }
        
	}

	public void leaveChatRoom(){
        try{
            if(this.currentChatRoom == null){
                this.outToClient.writeBytes("System: you are not in any chat room \n");
            }else{
                ChatRoomTable.getChatRoomTable().leaveChatRoom(this.currentChatRoom, this.currentUserName);
                this.brocastMessage("System: "+this.currentUserName+" leave chat room - " + this.currentChatRoom);
                this.outToClient.writeBytes("System: leave chat room" + this.currentChatRoom + "\n");
                this.currentChatRoom = null;
            }
        }catch(IOException e){
            System.out.println("leaveChatRoom: " + e.getMessage());
        }
    }

    public void leaveAllChatRoom(){
        ChatRoomTable.getChatRoomTable().leaveAllChatRoom(this.currentUserName);
        this.currentChatRoom = null;
    }
    
    public void sendMessage(String message, String userName){
        if(userName.equals(this.currentUserName)){
            return;
        }
        try{
            this.outToClient.writeBytes(userName + "@" + this.currentChatRoom + ": " + message +'\n');
        }catch(IOException e){
            System.out.println("sendMessage: " + e.getMessage());
        }
    }

    public void brocastMessage(String message){
        if(this.currentChatRoom == null){
            try{
                this.outToClient.writeBytes("System: Please join a Chat Room First\n");
            }catch(IOException e){
                System.out.println("brocastMessage: " + e.getMessage());
            }
        }else{
            ChatRoomTable.getChatRoomTable().sendMessageToChatRoom(this.currentChatRoom, message, this.currentUserName);
        }
    }

    public int detectInputType(String message){
        if(message.charAt(0) == '-'){
            if(message.charAt(1) == 'h'){
                return 1;
            }else if(message.charAt(1) == 'c'){
                return 2;
            }else if(message.charAt(1) == 'j'){
                return 3;
            }else if(message.charAt(1) == 'l'){
                return 4;
            }else if(message.charAt(1) == 'z'){
                return 5;
            }else if(message.charAt(1) == 'x'){
                return 6;
            }
        }
        return 0;
    }

 }