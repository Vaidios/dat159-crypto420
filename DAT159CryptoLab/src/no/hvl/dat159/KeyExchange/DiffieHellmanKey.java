package no.hvl.dat159.KeyExchange;

import java.io.IOException;

import no.hvl.dat159.config.ServerConfig;
import no.hvl.dat159.server.TCPServer;

public class DiffieHellmanKey {
	public int primitiveGen;
	public int RandPrime;
	
	public int getPrimitiveGen(){
		return 2;
	}
	public int getRandPrime(){
		return 2;
	}
	
	public static void main(String[] args) {
		System.out.println("Main succes");

	}
}
