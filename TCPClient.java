import java.io.*;
import java.net.*;

public class TCPClient {

	private String userName = "";

	public static void main(String[] args) throws Exception{
		try{
			String sentence;
			String modifiedSentence;
			
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			Socket clientSocket = new Socket(GlobalValue.DEFAULT_IP,GlobalValue.DEFAULT_PORT);
			
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			

			Thread tFromServer = new Thread(){
				public void run(){
					try{
						String tempStr = "";
						while(true){
							tempStr = inFromServer.readLine();
							System.out.println(tempStr);
							if(tempStr.equals("System: Bye Bye!")){
								clientSocket.close();
								return;
							}
						}
					}catch(IOException e){

					}
				}
			};

			tFromServer.start();

			while(true){
				sentence = inFromUser.readLine();
				outToServer.writeBytes(sentence);

				if(sentence.charAt(0) == '-'){
					if(sentence.charAt(1) == 'h'){
						break;
					}
				}
				
			}

		}catch(IOException e){

		}
	}
}
