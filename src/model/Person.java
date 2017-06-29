package model;

public class Person {

	private String firstName;
	private String lastName;
	private String age;
	
	
	public Person() {
		this("", "", "");		
	}
		
	public Person(String firstName, String lastName) {
		this(firstName, lastName, "");
	}
	
	public Person(String firstName, String lastName, String age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	// Getter
	public String getFirstName(){ return firstName;	}
	public String getLastName(){ return lastName;	}
	public String getAge(){ return age;	}
	
	// Setter
	public void setFirstName(String firstName){ this.firstName = firstName; }
	public void setLastName(String lastName){ this.lastName = lastName; }
	public void setAge(String age){ this.age = age; }

	@Override
	public String toString() {
		return "Person [firstName = " + firstName + ", lastName = " + lastName + ", age = " + age + "]";
	}
}


