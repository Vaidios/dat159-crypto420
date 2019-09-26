package no.hvl.dat159.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import no.hvl.dat159.config.ServerConfig;
import no.hvl.dat159.crypto.Vignere;
import no.hvl.dat159.KeyExchange.*;

import java.util.Timer;
import java.util.TimerTask;


public class TCPClient {
	
	private String server;
	private int port;
	private long privateKey;
	private int primitiveGen;
	private long randomPrime;
	
	public TCPClient(String server, int port) {
		this.server = server;
		this.port = port;
		this.randomPrime = DiffieHellmanKey.getRandPrime();
		this.primitiveGen = DiffieHellmanKey.getPrimitiveGen();
		this.privateKey = DiffieHellmanKey.genPrivateKey();
	}
	
	public void clientProcess() {
		String msg = "" + this.randomPrime +
				" " + this.primitiveGen +
				" " + DiffieHellmanKey.genPublicKey(this.privateKey, this.randomPrime, this.primitiveGen);
		try {
			Socket csocket = new Socket(server, port);
			this.sendMessege(csocket, msg);
			String response = this.getResponse(csocket);
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
	public String getResponse(Socket serverSocket) {
		if (serverSocket.isClosed() == false) {
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
		return null;
	}
	
	public static void main(String[] args) {
//		Timer timer = new Timer();
		TCPClient c = new TCPClient(ServerConfig.SERVER, ServerConfig.PORT);
		c.clientProcess();
//		timer.scheduleAtFixedRate(new TimerTask() {
//			@Override
//			public void run() {
//				
//			}
//		}, 0, 20 * 1000);
	}

}
