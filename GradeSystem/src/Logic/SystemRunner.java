package Logic;

import java.util.ArrayList;


public class SystemRunner {

	public static void main(String[] args) {
		School s = new School();
		Course active;
		
		s.addCourse("Spanish");
		s.addCourse("Italian");
		s.addCourse("French");
		

		
		active = s.selectCourse("Spanish");
		
		//Create test Overall Logic.Component
		Component compOver = new Component("Overall");
		
		Component compHWblock = new Component("Participation");
		Component compExamblock = new Component("Exams");
		
		compOver.addComponentAndScale(compHWblock,60);
		compOver.addComponentAndScale(compExamblock,40);
		
		/***
		//Create test HW Logic.Component
		Logic.Component compHWs = new Logic.Component("HW");
		
		Logic.Component compHW1 = new Logic.Component("HW1");
		Logic.Component compHW2 = new Logic.Component("HW2");
		Logic.Component compHW3 = new Logic.Component("HW3");
		
		compHWs.addComponentAndScale(compHW1,40);//this 40 will be disregarded and changed to 100
		compHWs.addComponentAndScale(compHW2,60);//this will work as intended
		compHWs.addComponentAndScale(compHW3,10);//addComponentAnd Scale begins messing with things when the third component is added
		
		compOver.addComponentAndScale(compHWs, 50);
		***/
		
		active.setTemplate(compOver);
		
		System.out.println(s);
		
		s.totalTemplates.exportComponent(compOver);
		/***
		active = s.selectCourse("Italian");
		Logic.Component template = s.totalTemplates.importComponent("Overall");//name of compOver

		active.setTemplate(template);
		
		System.out.println(s);
		
		compHW3.changePercentageAndScale(5);
		
		System.out.println(s);
		
		compHW3.changePercentageAndScale(10);
		
		System.out.println(s);
		***/

		active.addStudent(new Student("Seamus","Finnegan"),active.defaultSection());//a section must be specified, even if it is the defaultSection()
		active.addStudent(new Student("Blarney","Stone"),active.defaultSection());
		active.addStudent(new Student("Seamus","MacSuibhne"),active.defaultSection());
		
		active.addSection();
		
		ArrayList<Student> newStudentsToAdd = new ArrayList<Student>();
		newStudentsToAdd.add(new Student("Kevin","MacAllister"));
		newStudentsToAdd.add(new Student("Andre","Nozik"));
		newStudentsToAdd.add(new Student("Pete","Eckhart"));
		//If we can parse an input list of student names in plain text into an ArrayList<Logic.Student>, then we can make this function work for a large group of students
		
		Section selectedSection = active.getSections().get(1);//use the ui to actually select a section to add to
		
		active.addStudents(newStudentsToAdd,selectedSection);//a section must be specified, even if it is the defaultSection()
		
		
		System.out.println(active.getClassList());
	}

}
