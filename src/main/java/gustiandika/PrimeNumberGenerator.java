package gustiandika;

import static java.util.stream.IntStream.iterate;
import static java.util.stream.IntStream.rangeClosed;

import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

@Component
public class PrimeNumberGenerator {

	/**
	 * Return prime numbers from 2 to {@code n} 
	 */
	 public IntStream numbers(int n) {
		boolean[] notPrime = new boolean[n + 1];
		rangeClosed(2, (int)Math.sqrt(n * 1f))
			.filter(i -> !notPrime[i])
			.flatMap(i -> iterate(i * i, m -> m <=n , m -> m + i))
			.forEach(i -> notPrime[i] = true);
		
		return rangeClosed(2, n).filter(i -> !notPrime[i]);	
	}
}
