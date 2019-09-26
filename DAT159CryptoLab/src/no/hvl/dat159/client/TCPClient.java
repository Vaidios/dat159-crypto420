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
			outmsg.println(cypher.encrypt(msg));
			StringBuffer sb = new StringBuffer();
			while((outtxt = inmsg.readLine()) != null) {
				sb.append(outtxt+"\n");
			}
			System.out.println("______________________________________");
			System.out.println("[Response from TCPServer]: "+sb);
			System.out.println("Decrypted: "+ cypher.decrypt(sb.toString()));
			System.out.println("______________________________________");
			outmsg.close();
			inmsg.close();
			csocket.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public static void main(String[] args) {
		Timer timer = new Timer();
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				TCPClient c = new TCPClient(ServerConfig.SERVER, ServerConfig.PORT);
				c.clientProcess("CLIENTXXXEncryptedXXXMessege");
			}
		}, 0, 20 * 1000);
	}

}
