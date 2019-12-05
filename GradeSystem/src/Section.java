import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class Section {//A section is simply a collection of students that are group together for organizational purposes
	public ArrayList<Student> students; //This is a priorityQueue meaning that it will always be in alphabetical order!
	public String name;
	private Course course;
	private Map<Student,Grade> gradeMap;
	
	
	//TODO: Add the connection between section and course
	public Section(String n) {
		students = new ArrayList<Student>();
		name = n;
	}
	
	public void addStudent(Student s) {
		students.add(s);
		Collections.sort(students);
	}
	
	public void addStudents(List<Student> stus) {
		students.addAll(stus);
		Collections.sort(students);
		
	}
	
	public String toString() {
		String ret = "";
		for (Student s : students) {
			ret = ret + "\n" + s;
		}
		return ret;
	}
}
