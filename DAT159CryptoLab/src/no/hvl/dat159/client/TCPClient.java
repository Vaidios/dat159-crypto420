package no.hvl.dat159.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import no.hvl.dat159.config.ServerConfig;
import no.hvl.dat159.crypto.Vignere;

import java.util.Timer;
import java.util.TimerTask;


public class TCPClient {
	
	private String server;
	private int port;
	
	public TCPClient(String server, int port) {
		this.server = server;
		this.port = port;
	}
	
	public void clientProcess(String msg) {
		
		String outtxt = "";
		Vignere cypher = new Vignere("BITTERLEMON");
		try {
			Socket csocket = new Socket(server, port);
			
			
			
			
			
			
			PrintWriter outmsg = new PrintWriter(csocket.getOutputStream(), true);
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
			System.out.println("______________________________________");
			System.out.println("Message to TCPServer: "+ msg);
			System.out.println("Message to TCPServer: "+ cypher.encrypt(msg));
			System.out.println("______________________________________");
			this.sendMessege(csocket, msg);
			if(csocket.isClosed() == false) {
				this.getResponse(csocket);
			}
//			StringBuffer sb = new StringBuffer();
//			while((outtxt = inmsg.readLine()) != null) {
//				sb.append(outtxt+"\n");
//			}
//			System.out.println("______________________________________");
//			System.out.println("[Response from TCPServer]: "+sb);
//			System.out.println("Decrypted: "+ cypher.decrypt(sb.toString()));
//			System.out.println("______________________________________");
//			inmsg.close();
			csocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void sendMessege(Socket sSocket, String messege) {
		try {
			PrintWriter outmsg = new PrintWriter(sSocket.getOutputStream(), true);
			outmsg.println(messege);
			//outmsg.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void getResponse(Socket serverSocket) {
		try {
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			String inputMsg;
			while((inputMsg = inmsg.readLine()) != null) {
				int lastIndexOfHeader = inputMsg.lastIndexOf("n") + 1;
				String httpHeader = inputMsg.substring(0, lastIndexOfHeader);
				String messege = inputMsg.substring(lastIndexOfHeader, inputMsg.length());
				System.out.println(httpHeader);
				System.out.println(messege);
			}
			inmsg.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
//		Timer timer = new Timer();
		TCPClient c = new TCPClient(ServerConfig.SERVER, ServerConfig.PORT);
		c.clientProcess("CLIENTXXXEncryptedXXXMessege");
//		timer.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				
//			}
//		}, 0, 20 * 1000);
	}

}
