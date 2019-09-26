package no.hvl.dat159.KeyExchange;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PrimeGenerator {
	static Random rand = new Random(System.currentTimeMillis());
	
	
	public static long PrimeNumber(long min, long max) {
		List<Long> primes = primeNumbersTill(min,max);
		return primes.get(ThreadLocalRandom.current().nextInt(primes.size()));
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
