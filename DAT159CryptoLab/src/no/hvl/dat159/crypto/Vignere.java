/**
 * 
 */
package no.hvl.dat159.crypto;

import no.hvl.dat159.client.HttpClient;
import no.hvl.dat159.config.ServerConfig;

/**
 * @author tdoy
 *
 */
public class Vignere {
	
	private char[] key;
	/**
	 * 
	 */
	public static void main(String[] args) {
		String key = "BITTERLEMON";
		Vignere c = new Vignere(key);
		System.out.println(c.encrypt("CHUJANIEWAFLA"));
		System.out.println(c.decrypt("CHUJANIEWAFLA"));
	}
	
	
	public Vignere(String key) {
		this.key = key.toCharArray();
	}
	
	
	public String encrypt(String plaintext) {
		
		String result = "";
		
		char[] text = plaintext.toUpperCase().toCharArray();
		for(int i = 0; i < text.length; i++) {
			char Mi = text[i];
			char Ki = this.key[i % this.key.length];
			char Ci = (char) (((Mi + Ki - 2 * 'A') % 26) + 'A');
			result += Ci;
		}
		return result;
	}
	
	/**
	 * 
	 * @param ciphertext
	 * @return
	 */
	public String decrypt(String ciphertext) {
		String result = "";
		char[] text = ciphertext.toUpperCase().replaceAll(" ", "").toCharArray();
		for(int i = 0; i < text.length; i++) {
			char Ci = text[i];
			char Ki = this.key[i % this.key.length];
			char Mi = (char) ((Ci - Ki + 26) % 26 + 'A');
			result += Mi;
		}
		return result;
	}
	
	/**
	 * 
	 * @param input
	 * @param code
	 * @return
	 */

	/**
	 * @return the encoder
	 */
//	public char[] getEncoder() {
//		return encoder;
//	}
//
//	/**
//	 * @return the decoder
//	 */
//	public char[] getDecoder() {
//		return decoder;
//	}

	
}
