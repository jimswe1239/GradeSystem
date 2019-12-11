package Logic;

public class Student implements Comparable<Student> ,java.io.Serializable{
	private Name name;
	/*** Uncomment if we want IDs
	private String id;
	
	public Student(Name n,String i) {
		name = n;
		id = i;
	}
	
	public Student(String fName, String lName, String i) {
		name = new Name(fName, lName);
		id = i;
	}
	
	public Student(String fName, String mName, String lName, String i) {
		name = new Name(fName, mName, lName);
		id = i;
	}
	***/
	
	public Student(Name n) {
		name = n;
	}
	
	public Student(String fName, String lName) {
		name = new Name(fName, lName);
	}
	
	public Student(String fName, String mName, String lName) {
		name = new Name(fName, mName, lName);
	}
	
	public String toString() {
		return name.toString(); //if we add IDs, modify this to include them
	}
	
	@Override
	public int compareTo(Student other) {
		return this.toString().compareTo(other.toString());
	}

}
