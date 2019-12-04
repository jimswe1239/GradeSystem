import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Course {

	private String name;
	private Component root;
	private ArrayList<Section> sections;//collections of students
	private int currentSectionNumber;
	
	public Course(String n) {//default number of sections = 1
		name = n;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		addSection();
		
	}
	
	public Course(String n, Component c) {//default number of section = 1
		name = n;
		root = c;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		addSection();
	}
	
	public Course(String n, int numOfSections) {//the process for creating a Course must require the user to input number of sections
		name = n;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		setSections(numOfSections);
		
	}
	
	public Course(String n, Component c, int numOfSections) {//the process for creating a Course must require the user to input number of sections
		name = n;
		root = c;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		setSections(numOfSections);
	}

	public void setTemplate(Component newRoot) {
		root = newRoot;
	}
	
	public void setSections(int numOfSections) {
		for (int x = 0; x<numOfSections; x++) {
			addSection();
			currentSectionNumber++;
		}
	}
	
	public void addSection() {
		sections.add(new Section("Section "+currentSectionNumber));
		currentSectionNumber++;
	}
	
	public boolean deleteSection(Section s) {
		if (sections.contains(s)) {
			sections.remove(s);
			return true;
		}
		return false;
	}
	
	public void addComponentAndScale(Component toAdd, Component whereToAdd, double percentage) {
		whereToAdd.addComponentAndScale(toAdd, percentage);
	}
	
	public void addComponent(Component toAdd, Component whereToAdd) {//Use this to add a new component worth 0 percent, and then use changeAllChildren to adjust the new values based on user input
		whereToAdd.addComponent(toAdd);
	}
	
	public boolean changeAllChildren(Component toChangeChildrenOf, HashMap<Component, Double> newChildren) {
		return toChangeChildrenOf.changeAllChildren(newChildren);
	}
	
	public String toString() {
		
		return name + " " + root;
	}
	
	public Component getRoot() {
		return root;
	}
	
	public void addStudent(Student s, Section sectionToAddTo) {//students must always be added to a section
		sectionToAddTo.addStudent(s);
	}
	
	public void addStudents(List<Student> studentsToAdd, Section sectionToAddTo) {
		sectionToAddTo.addStudents(studentsToAdd);
	}

	public String getClassList() {
		String ret = "";
		for (Section s : sections) {
			ret = ret + s.name + " [\n";
			for (Student stu : s.students) {
				ret = ret + stu.toString() + "\n";
			}
			ret = ret + "]\n";
		}
		return ret;
	}

	public ArrayList<Section> getSections(){
		return sections;
	}
	
	public Section defaultSection() {
		return sections.get(0);
	}


}
