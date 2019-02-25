import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class ChatRoomTable{
    private Hashtable<String, ChatMemberList> roomTable = null;
    private static ChatRoomTable crt = null;

    public static ChatRoomTable getChatRoomTable(){
        if(crt == null){
            crt = new ChatRoomTable();
            crt.roomTable = new Hashtable<String, ChatMemberList>();
        }
        return crt;
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

    public void sendMessageToChatRoom(String chatRoomName, String message, String userName){
        ChatMemberList tempRoomTable = this.roomTable.get(chatRoomName);
        if(tempRoomTable == null){
            System.out.println("can not send message to chat room '"+chatRoomName+"', not exist.");
        }else{
            tempRoomTable.brocastMessageToMember(message, userName);
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

    public void leaveAllChatRoom(String userName){
        Iterator<Entry<String,ChatMemberList>> list_Iter = this.roomTable.entrySet().iterator();
    
        while(list_Iter.hasNext()){ 
            // send message to everyone
            Entry<String,ChatMemberList> individualChatMember = (Entry<String,ChatMemberList>)list_Iter.next();
            ChatMemberList tempCML = (ChatMemberList)individualChatMember.getValue();
            tempCML.memverLeave(userName);
        }
    }

    public String listAllChatRoom(){
        Iterator<Entry<String,ChatMemberList>> list_Iter = this.roomTable.entrySet().iterator();
    
        String reStr="";
        while(list_Iter.hasNext()){ 
            // send message to everyone
            Entry<String,ChatMemberList> individualChatMember = (Entry<String,ChatMemberList>)list_Iter.next();
            reStr += (String)individualChatMember.getKey() + "\n";
        }
        return reStr;
    }
}
