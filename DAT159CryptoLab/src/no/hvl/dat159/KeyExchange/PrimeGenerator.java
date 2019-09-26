package no.hvl.dat159.KeyExchange;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PrimeGenerator {
	
	public static void Primes() {
		System.out.print(PrimeNumber((long)Math.pow(2, 63), (long)Math.pow(2, 63)+100));
	}
	public static long PrimeNumber(long min, long max) {
		List<Long> primes = primeNumbersTill(min,max);
		Random rand = new Random(System.currentTimeMillis());
		return primes.get(rand.nextInt(primes.size()));
	}
	public static List<Long> primeNumbersTill(long min,long max) {
	    return LongStream.rangeClosed(min, max)
	      .filter(x -> isPrime(x) == true).boxed()
	      .collect(Collectors.toList());
	}
	private static boolean isPrime(long number) {
	    return LongStream.rangeClosed(2, (long) (Math.sqrt(number)))
	      .allMatch(n -> number % n != 0);
	}
	
}
