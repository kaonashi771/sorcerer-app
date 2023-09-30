package gustiandika;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SorcererServiceTest {

	@Autowired
	private SorcererService sorcerer;
	
	@Test
	public void testNumberOfVictimByYear() {
		assertThat(sorcerer.numberOfVictim(1)).isEqualTo(1);
		assertThat(sorcerer.numberOfVictim(2)).isEqualTo(2);
		assertThat(sorcerer.numberOfVictim(3)).isEqualTo(4);
		assertThat(sorcerer.numberOfVictim(4)).isEqualTo(7); 
		assertThat(sorcerer.numberOfVictim(5)).isEqualTo(12);
		
		assertThat(sorcerer.numberOfVictim(0)).isEqualTo(-1);
		assertThat(sorcerer.numberOfVictim(-1)).isEqualTo(-1);
		assertThat(sorcerer.numberOfVictim(-2)).isEqualTo(-1);
		assertThat(sorcerer.numberOfVictim(-3)).isEqualTo(-1);
	}
	
	@Test
	public void testAvgNumberOfVictim() {
		//normal case age 2 and 4
		assertThat(sorcerer.avgNumberOfVictim(Person.of(12, 10), Person.of(17, 13)))
			.isEqualTo(4.5);
		
		// normal case, with zero age
		assertThat(sorcerer.avgNumberOfVictim(Person.of(12, 12), Person.of(17, 13)))
		.isEqualTo(4.0);
	
		
		// exceptional case: when either of the Person data is invalid
		assertThat(sorcerer.avgNumberOfVictim(Person.of(12, -10), Person.of(17, 13)))
		.isEqualTo(-1);
		
		// exceptional case: when two person born same year
		assertThat(sorcerer.avgNumberOfVictim(Person.of(12, 10), Person.of(12, 10)))
			.isEqualTo(2.0);
		
	}
}
