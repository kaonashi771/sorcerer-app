package gustiandika;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Person {
	
	@Id
	@GeneratedValue
	private Long id;
	private int yearOfDeath;
	private int ageOfDeath;
	
	public Person() {}
	
	public Person(int yearOfDeath, int ageOfDeath) {
		this.yearOfDeath = yearOfDeath;
		this.ageOfDeath = ageOfDeath;	
	}
	
	public Long getId() {
		return id;
	}
	
	public int getYearOfDeath() {
		return yearOfDeath;
	}

	public int getAgeOfDeath() {
		return ageOfDeath;
	}

	public void setAgeOfDeath(int ageOfDeath) {
		this.ageOfDeath = ageOfDeath;
	}

	public void setYearOfDeath(int yearOfDeath) {
		this.yearOfDeath = yearOfDeath;
	}

	public int getYearOfBirth() {
		int yearOfBirth = yearOfDeath - ageOfDeath;
		if (yearOfBirth == 0)
			yearOfBirth = 1;
	
		return yearOfBirth;
	}

	static Person of(int deathYear, int birthYear) {
		return new Person(deathYear, birthYear);
	}
}