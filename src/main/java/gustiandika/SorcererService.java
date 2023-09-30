package gustiandika;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SorcererService {

	@Autowired
	private PrimeNumberGenerator primeGenerator;

	/**
	 * Return number of killed villagers at {@code year}
	 */
	public int numberOfVictim(int year) {
		if (year <= 0)
			return -1;
		
		if (year == 1 || year == 2) {
			return year;
		}

		return primeGenerator
				.numbers(year)
				.limit(year - 2)
				.reduce(2, (a, b) -> a + b);
	}

	/**
	 * Get average number of victim between two person year of birth.
	 * 
	 * return -1 if person year of birth is > year of death
	 * 
	 */
	public double avgNumberOfVictim(Person... persons) {

		if (Stream.of(persons).anyMatch(p -> p.getYearOfBirth() > p.getYearOfDeath())) {
			return -1;
		}

		return Stream.of(persons)
				.mapToInt(p -> p.getYearOfBirth())
				.distinct()
				.map(y -> numberOfVictim(y))
				.average()
				.orElse(-1);
	}
}
