import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Course {

	private String name;
	private Component root;
	private ArrayList<Section> sections;//collections of students
	private int currentSectionNumber;
	private GradeMap gradeMap;
	
	public Course(String n) {//default number of sections = 1
		name = n;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		addSection();
		gradeMap = new GradeMap();
		
	}
	
	public Course(String n, Component c) {//default number of section = 1
		name = n;
		root = c;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		addSection();
		gradeMap = new GradeMap();
	}
	
	public Course(String n, int numOfSections) {//the process for creating a Course must require the user to input number of sections
		name = n;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		setSections(numOfSections);
		gradeMap = new GradeMap();
		
	}
	
	public Course(String n, Component c, int numOfSections) {//the process for creating a Course must require the user to input number of sections
		name = n;
		root = c;
		sections = new ArrayList<Section>();
		currentSectionNumber = 1;
		setSections(numOfSections);
		gradeMap = new GradeMap();
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
		whereToAdd.addComponentAndScale(toAdd,0);
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
	
	public void putScore(Student student, Score score, Component component) {
		gradeMap.putScore(student, score, component);
	}
	
	public double getFinalScore(Student s, Component c) {//For either a specific assignment or the root
		Grade grade = gradeMap.getGrade(s);
		return grade.getFinalScore(c);
	}
	
	public double getFinalAverage(Component c) {//For either a specific assignment or the root
		double finalSum = 0;
		for (Student s: this.getStudentList()) {
			finalSum += getFinalScore(s,c);
		}
		return finalSum/(this.getStudentList().size());
	}
	
	public double getFinalMedian(Component c) {//For either a specific assignment or the root
		ArrayList<Double> percentageList = new ArrayList<Double>();
		for (Student s: this.getStudentList()) {
			percentageList.add(this.getFinalScore(s, c));
		}
		Collections.sort(percentageList);
		int numOfScores = percentageList.size();
		if (numOfScores % 2 == 1) {
			return percentageList.get(numOfScores/2);
		}
		return (percentageList.get(numOfScores/2 -1) + percentageList.get((numOfScores/2))) /2;
	}
	
	public double getFinalStandardDeviation(Component c) {//For either a specific assignment or the root
		ArrayList<Double> percentageList = new ArrayList<Double>();
		for (Student s: this.getStudentList()) {
			percentageList.add(this.getFinalScore(s, c));
		}
		double mean = this.getFinalAverage(c);
		double sumOfPowers = 0;
		for (double i : percentageList) {
			sumOfPowers+=Math.pow(((double) i - mean), 2);
		}

		return Math.sqrt(sumOfPowers/percentageList.size());
}
	
	
	
	public void setEndBonus(double d) {//Add a curve value to all student's Final grades on root
		//after calling this method, make sure that everything (each student's final score, and the final average, mean, and stddev) is recalculated and re-rendered
		//this ONLY affects the root final score
		Grade grade;
		for (Student s: this.getStudentList()) {
			grade = gradeMap.getGrade(s);
			grade.setEndBonus(d);
		}
	}

	public ArrayList<Student> getStudentList() {
		ArrayList<Student> ret = new ArrayList<Student>();
		for (Section s : sections) {
			ret.addAll(s.getStudentList());
		}
		return ret;
	}



}
