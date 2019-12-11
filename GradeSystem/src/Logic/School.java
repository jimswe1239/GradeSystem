package Logic;
import java.util.ArrayList;
import java.util.HashMap;



public class School implements java.io.Serializable{
	HashMap<String,Course> courses; //active courses stored here
	HashMap<String,Course> pastCourses; //when courses are removed they go here
	ComponentMap totalTemplates; //if a node with parent == null is exported, store here
	ComponentMap partialTemplates; //if a node with parent != null is exported, store here

	
	public School() {
		courses = new HashMap<String,Course>(); //active courses stored here
		pastCourses = new HashMap<String,Course>(); //when courses are removed they go here
		totalTemplates = new ComponentMap(); //if a node with parent == null is exported, store here
		//partialTemplates = new ComponentMap(); //if a node with parent != null is exported, store here
		totalTemplates.exportComponent(new Component("Blank Template"));
	}
	
	public void addCourse(String name) {
		courses.put(name, new Course(name));
	}
	
	public void addCourse(Course course) {
		courses.put(course.getName(), course);
	}
	
	public Course selectCourse(String name) {
		return courses.get(name);
	}
	
	public String toString() {
		String ret = "";
		for (String name : courses.keySet()) {
			ret = ret + "\n" + courses.get(name);
		}
		
		return ret;
	}

	public Course[] getCourseArray() {
		//Course[] courseArray = (Course[])courses.values().toArray();
		ArrayList<Course> courseArrayList = new ArrayList<Course>(courses.values());
		Course[] courseArray = new Course[courseArrayList.size()];
		for (int x = 0; x<courseArrayList.size(); x++) {
			courseArray[x] = courseArrayList.get(x);
		}
		return courseArray;
	}

	public Component[] getTemplateArray() {
		ArrayList<Component> componentArrayList = new ArrayList<Component>(totalTemplates.getComponents());
		Component[] componentArray = new Component[componentArrayList.size()];
		for (int x = 0; x<componentArrayList.size(); x++) {
			componentArray[x] = componentArrayList.get(x);
		}
		return componentArray;
	}
	
	public ComponentMap getTotalTemplates() {
		return totalTemplates;
	}
}
