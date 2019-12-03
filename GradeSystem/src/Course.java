
public class Course {

	private String name;
	private Component root;
	
	public Course(String n) {
		name = n;
	}
	
	public Course(String n, Component c) {
		name = n;
		root = c;
	}

	public void setTemplate(Component newRoot) {
		root = newRoot;
	}
	
	public void addComponent(Component toAdd, Component whereToAdd, double percentage) {
		whereToAdd.addComponentAndScale(toAdd, percentage);
	}
	
	public String toString() {
		
		return name + " " + root;
	}
	
	public Component getRoot() {
		return root;
	}


}
