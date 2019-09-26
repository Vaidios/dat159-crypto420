/**
 * 
 */
package no.hvl.dat159.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.hvl.dat159.crypto.Vignere;

/**
 * @author tdoy
 *
 */
class VignereTest {

	@Test
	void test() {

		String message = "THESEAREENGLISHLETTERS";
		String keys = "JOY";
		
		Vignere vg = new Vignere(keys);
		String ecipher = vg.encrypt(message);
		String dmessage = vg.decrypt(ecipher);

		
		System.out.println(ecipher);
		System.out.println(dmessage);
		assertArrayEquals(message.toUpperCase().toCharArray(), dmessage.toCharArray());
		System.out.println("[Unit-Test] Succes");
		
	}

}
