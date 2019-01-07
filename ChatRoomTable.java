import java.util.Hashtable;;

public class ChatRoomTable{
    private Hashtable<String, ChatMemberList> roomTable = null;

    public Hashtable<String, ChatMemberList> getChatRoomTable(){
        if(this.roomTable == null){
            this.roomTable = new Hashtable<String, ChatMemberList>();
        }
        return this.roomTable;
    }

    public void joinChatRoom(String chatRoomName, String userName, TCPConnectionListener connectionListener){
        ChatMemberList tempRoomTable = this.roomTable.get(chatRoomName);
        if(tempRoomTable == null){
            System.out.println("can not join chat room '"+chatRoomName+"', not exist.");
        }else{
            tempRoomTable.putChatMemberToMemberList(userName, connectionListener);
        }
    }


    public void craeteChatRoom(String chatRoomName){
        ChatMemberList tempRoomTable = this.roomTable.get(chatRoomName);
        if(tempRoomTable == null){
            this.roomTable.put(chatRoomName, new ChatMemberList());
        }else{
            System.out.println("can not crate chat room '"+chatRoomName+"', exist already.");
        }
    }

    public void sendMessageToChatRoom(String chatRoomName, String message){
        ChatMemberList tempRoomTable = this.roomTable.get(chatRoomName);
        if(tempRoomTable == null){
            System.out.println("can not send message to chat room '"+chatRoomName+"', not exist.");
        }else{
            tempRoomTable.brocastMessageToMember(message);
        }
    }

    public void leaveChatRoom(String chatRoomName, String userName){
        ChatMemberList tempRoomTable = this.roomTable.get(chatRoomName);
        if(tempRoomTable == null){
            System.out.println("can not leave chat room '"+chatRoomName+"', not exist.");
        }else{
            tempRoomTable.memverLeave(userName);
        }
    }
}
