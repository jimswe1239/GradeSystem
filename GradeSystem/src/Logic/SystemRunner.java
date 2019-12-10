package Logic;
import java.util.ArrayList;
import UI.*;

import javax.swing.*;

public class SystemRunner {

	public static void main(String[] args) {
		School school = new School();
		Course active;
		
		school.addCourse("Spanish");
		school.addCourse("Italian");
		school.addCourse("French");
		

		
		active = school.selectCourse("Spanish");
		
		//Create test Overall Component
		Component compOver = new Component("Overall");
		
		Component compPartblock = new Component("Participation");
		Component compExamblock = new Component("Exams");
		
		compOver.addComponentAndScale(compPartblock,60);
		compOver.addComponentAndScale(compExamblock,40);
		

		//Create test HW Component
		Component compHWblock = new Component("HWs");
		
		Component compHW1 = new Component("HW1");
		Component compHW2 = new Component("HW2");
		Component compHW3 = new Component("HW3");
		
		compHWblock.addComponentAndScale(compHW1,40);//this 40 will be disregarded and changed to 100
		compHWblock.addComponentAndScale(compHW2,60);//this will work as intended
		compHWblock.addComponentAndScale(compHW3,10);//addComponentAnd Scale begins messing with things when the third component is added
		
		compOver.addComponentAndScale(compHWblock, 50);

		
		active.setTemplate(compOver);
		
		System.out.println(school);
		
		school.totalTemplates.exportComponent(compOver);

		active = school.selectCourse("Italian");
		Component template = school.totalTemplates.importComponent("Overall");//name of compOver

		active.setTemplate(template);
		
		System.out.println(school);
		
		compHW3.changePercentageAndScale(5);
		
		System.out.println(school);
		
		compHW3.changePercentageAndScale(10);
		
		System.out.println(school);

		active = school.selectCourse("Spanish");

		active.addStudent(new Student("Seamus","Finnegan"),active.defaultSection());//a section must be specified, even if it is the defaultSection()
		active.addStudent(new Student("Blarney","Stone"),active.defaultSection());
		active.addStudent(new Student("Seamus","MacSuibhne"),active.defaultSection());
		
		active.addSection();
		
		ArrayList<Student> newStudentsToAdd = new ArrayList<Student>();
		newStudentsToAdd.add(new Student("Kevin","MacAllister"));
		newStudentsToAdd.add(new Student("Andre","Nozik"));
		//newStudentsToAdd.add(new Student("Dr","Dre"));

		//If we can parse an input list of student names in plain text into an ArrayList<Student>, then we can make this function work for a large group of students
		
		Section selectedSection = active.getSections().get(1);//use the ui to actually select a section to add to
		
		active.addStudents(newStudentsToAdd,selectedSection);//a section must be specified, even if it is the defaultSection()
		
		active.putScore(active.getStudentList().get(0), new Score(100), compPartblock);
		active.putScore(active.getStudentList().get(0), new Score(100), compExamblock);
		active.putScore(active.getStudentList().get(0), new Score(100), compHW1);
		active.putScore(active.getStudentList().get(0), new Score(50), compHW2);
		active.putScore(active.getStudentList().get(0), new Score(0), compHW3);
		
		active.putScore(active.getStudentList().get(1), new Score(100), compPartblock);
		active.putScore(active.getStudentList().get(1), new Score(100), compExamblock);
		active.putScore(active.getStudentList().get(1), new Score(100), compHW1);
		active.putScore(active.getStudentList().get(1), new Score(50), compHW2);
		active.putScore(active.getStudentList().get(1), new Score(0), compHW3);
		
		active.putScore(active.getStudentList().get(2), new Score(100), compPartblock);
		active.putScore(active.getStudentList().get(2), new Score(100), compExamblock);
		active.putScore(active.getStudentList().get(2), new Score(100), compHW1);
		active.putScore(active.getStudentList().get(2), new Score(50), compHW2);
		active.putScore(active.getStudentList().get(2), new Score(0), compHW3);
		
		active.putScore(active.getStudentList().get(3), new Score(0), compPartblock);
		active.putScore(active.getStudentList().get(3), new Score(0), compExamblock);
		active.putScore(active.getStudentList().get(3), new Score(100), compHW1);
		active.putScore(active.getStudentList().get(3), new Score(50), compHW2);
		active.putScore(active.getStudentList().get(3), new Score(0), compHW3);
		
		active.putScore(active.getStudentList().get(4), new Score(0), compPartblock);
		active.putScore(active.getStudentList().get(4), new Score(0), compExamblock);
		active.putScore(active.getStudentList().get(4), new Score(100), compHW1);
		active.putScore(active.getStudentList().get(4), new Score(50), compHW2);
		active.putScore(active.getStudentList().get(4), new Score(0), compHW3);
		/***
		active.putScore(active.getStudentList().get(5), new Score(0), compPartblock);
		active.putScore(active.getStudentList().get(5), new Score(0), compExamblock);
		active.putScore(active.getStudentList().get(5), new Score(100), compHW1);
		active.putScore(active.getStudentList().get(5), new Score(50), compHW2);
		active.putScore(active.getStudentList().get(5), new Score(0), compHW3);
		***/

		System.out.println(active.getClassList());

		
		for(Student s: active.getStudentList()) {
			System.out.println(s.toString() + "  Final Score: " + active.getFinalScore(s, active.getRoot()));
		}
		

		System.out.println(active.getFinalMedian(active.getRoot()));
		System.out.println(active.getFinalStandardDeviation(active.getRoot()));

	}

}
