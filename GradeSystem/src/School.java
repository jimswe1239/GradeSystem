import java.util.HashMap;

public class School {
	HashMap<String,Course> courses; //active courses stored here
	HashMap<String,Course> pastCourses; //when courses are removed they go here
	ComponentMap totalTemplates; //if a node with parent == null is exported, store here
	ComponentMap partialTemplates; //if a node with parent != null is exported, store here

	
	public School() {
		courses = new HashMap<String,Course>(); //active courses stored here
		pastCourses = new HashMap<String,Course>(); //when courses are removed they go here
		totalTemplates = new ComponentMap(); //if a node with parent == null is exported, store here
		partialTemplates = new ComponentMap(); //if a node with parent != null is exported, store here
	}
	
	public void addCourse(String name) {
		courses.put(name, new Course(name));
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
}
