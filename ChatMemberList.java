import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Hashtable;

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

    public void brocastMessageToMember(String message, String userName){
        
        Iterator<Entry<String,TCPConnectionListener>> list_Iter = this.cmList.entrySet().iterator();
  
        while(list_Iter.hasNext()){ 
            // send message to everyone
            Entry<String,TCPConnectionListener> individualChatMember = (Entry<String,TCPConnectionListener>)list_Iter.next();
            ((TCPConnectionListener)individualChatMember.getValue()).sendMessage(message, userName);
        } 
    }

    public void memverLeave(String userName){
        TCPConnectionListener tempConnection = this.cmList.remove(userName);
        // destory
    }
}