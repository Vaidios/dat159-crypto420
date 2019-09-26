package no.hvl.dat159.KeyExchange;


import java.io.IOException;

import no.hvl.dat159.config.ServerConfig;
import no.hvl.dat159.server.TCPServer;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class DiffieHellmanKey {
	
	public static int getPrimitiveGen(){
		return 2;
	}

	public static long getRandPrime(){
		return PrimeGenerator.PrimeNumber((long)Math.pow(2, 3), (long)Math.pow(2, 3)+10);

	}
	public static long genPublicKey(long privateKey, long randPrime, int primitiveGen) {
		return (long) Math.pow(primitiveGen, privateKey)%randPrime;
	}
	public static long genPrivateKey() {
		
		return ThreadLocalRandom.current().nextLong((long)Math.pow(2, 3),(long)Math.pow(2, 3)+10);
	}
	
	public static long genSharedSecret(long publicKey, long privateKey, long randPrime) {
		return (long) Math.pow(publicKey, privateKey)%randPrime;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Main succes");

	}
}
