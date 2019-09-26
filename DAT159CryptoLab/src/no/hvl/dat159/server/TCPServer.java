package no.hvl.dat159.server;
import no.hvl.dat159.KeyExchange.DiffieHellmanKey;
import no.hvl.dat159.KeyExchange.PrimeGenerator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import no.hvl.dat159.KeyExchange.PrimeGenerator;
import no.hvl.dat159.config.ServerConfig;
import no.hvl.dat159.crypto.Vignere;


public class TCPServer {

	private ServerSocket ssocket;
	
	public TCPServer(int port) throws IOException {
		ssocket = new ServerSocket(port);
	}
	
	public void socketlistener() {
		
		try {
			int G = DiffieHellmanKey.getPrimitiveGen();
			long P = DiffieHellmanKey.getRandPrime();
			long privKey1 = DiffieHellmanKey.genPrivateKey();
			long pubKey1 = DiffieHellmanKey.genPublicKey(privKey1,P,G);
			long privKey2 = DiffieHellmanKey.genPrivateKey();
			long pubKey2 = DiffieHellmanKey.genPublicKey(privKey2,P,G);
			long secret1 = DiffieHellmanKey.genSharedSecret(pubKey2, privKey1, P);
			long secret2 = DiffieHellmanKey.genSharedSecret(pubKey1, privKey2, P);
			
			System.out.println("G:"  + G);
			System.out.println("P:"  + P);
			
			System.out.println("Private Key1:"  + privKey1);
			System.out.println("Public Key1: " +  pubKey1);
			System.out.println("Private Key2:"  + privKey2);
			System.out.println("Public Key2: " +  pubKey2);
			System.out.println("Secret1: " +  secret1);
			System.out.println("Secret2: " +  secret2);
			
			System.out.println("[LISTENIG] "+ServerConfig.PORT);
			
			Socket socket = ssocket.accept();
			
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream outmsg = new DataOutputStream(socket.getOutputStream());
			Vignere cypher = new Vignere("BITTERLEMON");
			String clientmsg = inmsg.readLine();
			System.out.println("\n[Messege from Client]: "+ clientmsg);
			System.out.println("\n[Decrypt messege]: "+ cypher.decrypt(clientmsg));
			System.out.println("______________________________________");
			
//			String response = cypher.encrypt("ThanksXXXTClient");
//			outmsg.write(response.getBytes());
			String response = "HTTP/1.1 200 OK \\r\\n\\r\\n" + cypher.encrypt("ThanksXXXTClient");
			outmsg.write(response.getBytes());
			outmsg.flush();
			inmsg.close();
			outmsg.close();
			
			socket.close();
	
		}catch(IOException e) {
			//
		}
	}
	
	public static void main(String[] args) throws IOException {

		TCPServer tcpserver = new TCPServer(ServerConfig.PORT);
		
		// start the server and let it run forever
		while(true) {
			tcpserver.socketlistener();
		}

	}

}
