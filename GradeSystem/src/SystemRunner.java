import java.util.ArrayList;
import java.util.List;

public class SystemRunner {

	public static void main(String[] args) {
		School s = new School();
		Course active;
		
		s.addCourse("Spanish");
		s.addCourse("Italian");
		s.addCourse("French");
		

		
		active = s.selectCourse("Spanish");
		
		//Create test Overall Component
		Component compOver = new Component("Overall");
		
		Component compHWblock = new Component("Participation");
		Component compExamblock = new Component("Exams");
		
		compOver.addComponentAndScale(compHWblock,10);
		compOver.addComponentAndScale(compExamblock,40);
		
		
		//Create test HW Component
		Component compHWs = new Component("HW");
		
		Component compHW1 = new Component("HW1");
		Component compHW2 = new Component("HW2");
		Component compHW3 = new Component("HW3");
		
		compHWs.addComponentAndScale(compHW1,40);
		compHWs.addComponentAndScale(compHW2,60);
		compHWs.addComponentAndScale(compHW3,10);
		
		compOver.addComponentAndScale(compHWs, 40);
		
		
		active.setTemplate(compOver);
		
		System.out.println(s);
		
		s.totalTemplates.exportComponent(compOver);
		active = s.selectCourse("Italian");
		Component template = s.totalTemplates.importComponent("Overall");//name of compOver

		active.setTemplate(template);
		
		System.out.println(s);
		
		compHW3.changePercentageAndScale(5);
		
		System.out.println(s);
		
		compHW3.changePercentageAndScale(10);
		
		System.out.println(s);

	}

}
