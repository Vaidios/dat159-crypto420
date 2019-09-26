package no.hvl.dat159.server;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import no.hvl.dat159.KeyExchange.DiffieHellmanKey;
import no.hvl.dat159.config.ServerConfig;


public class TCPServer {

	private ServerSocket ssocket;
	private long privateKey;
	private long clientPublicKey;
	private long publicKey;
	private int primitiveGen;
	private long randomPrime;
	
	public TCPServer(int port) throws IOException {
		ssocket = new ServerSocket(port);
		this.privateKey = DiffieHellmanKey.genPrivateKey();
	}
	
	public void socketlistener() {
		
		try {

			System.out.println("[SERVER] -> Listening on port: " + ServerConfig.PORT);

			Socket socket = ssocket.accept();
			
			BufferedReader inmsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			DataOutputStream outmsg = new DataOutputStream(socket.getOutputStream());
			String clientmsg = inmsg.readLine();
			String[] param = clientmsg.split(" ");
			this.randomPrime = Long.parseLong(param[0]);
			this.primitiveGen = Integer.parseInt(param[1]);
			this.clientPublicKey = Long.parseLong(param[2]);
			this.publicKey = DiffieHellmanKey.genPublicKey(this.privateKey, this.randomPrime, this.primitiveGen);
			long secret = DiffieHellmanKey.genSharedSecret(this.clientPublicKey, this.privateKey, this.randomPrime );
			
			System.out.println("\n[Messege from Client]: "+ clientmsg);
			System.out.println("\n[randomPrime]: " + this.randomPrime);
			System.out.println("\n[primitiveGen]: " + this.primitiveGen);
			System.out.println("\n[clientPublicKey]: " + this.clientPublicKey);
			System.out.println("\n[Decrypt messege]: ");
			System.out.println("______________________________________");
			String response = "HTTP/1.1 200 OK \r\n\r\n";
			response += "Public-key: " + this.publicKey;
			this.sendMessege(socket, response);
			
			
			clientmsg = inmsg.readLine();
			System.out.println("Secrets: " + secret + " " + clientmsg);
			
			
			
			inmsg.close();
			outmsg.close();
			
			socket.close();
	
		}catch(IOException e) {
			//
		}
	}
	
	public void sendMessege(Socket sSocket, String messege) {
		try {
			PrintWriter outmsg = new PrintWriter(sSocket.getOutputStream(), true);
			outmsg.println(messege);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getMessege(Socket serverSocket) {
		if (serverSocket.isClosed() == false) {
			try {
				BufferedReader inmsg = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
				String inputMsg;
				StringBuffer sb = new StringBuffer();
				while((inputMsg = inmsg.readLine()) != null) {
					int lastIndexOfHeader = inputMsg.lastIndexOf("n") + 1;
					String httpHeader = inputMsg.substring(0, lastIndexOfHeader);
					String messege = inputMsg.substring(lastIndexOfHeader, inputMsg.length());
					System.out.println(httpHeader);
					System.out.println(messege);
					sb.append(inputMsg);
				}
				inmsg.close();
				return sb.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {

		TCPServer tcpserver = new TCPServer(ServerConfig.PORT);
		
		// start the server and let it run forever
		while(true) {
			tcpserver.socketlistener();
		}

	}

}
