import java.util.Iterator;
import java.util.Hashtable;;

public class ChatMemberList{
    private Hashtable<String,TCPConnectionListener> cmList = null;
    public ChatMemberList(){
        this.cmList = new Hashtable<String, TCPConnectionListener>();
    }

    public Hashtable<String, TCPConnectionListener> getChatMemberList(){
        return this.cmList;
    }

    public void putChatMemberToMemberList(String userName, TCPConnectionListener connectionListener){
        this.cmList.put(userName, connectionListener);
    }

    public void brocastMessageToMember(String message){
        
        Iterator<TCPConnectionListener> list_Iter = this.cmList.iterator();
  
        while(list_Iter.hasNext()){ 
            // send message to everyone
        } 
    }

    public void memverLeave(String userName){
        TCPConnectionListener tempConnection = this.cmList.remove(userName);
        // destory
    }
}