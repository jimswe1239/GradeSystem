import java.util.ArrayList;
import java.util.HashMap;

public class Component {

	String name;
	
	private Component parent; //if == null, then this is root
	
	public HashMap<Component,Double> children;
	

	
	public Component(String n) {
		name = n;
		children = new HashMap<Component,Double>();
		
	}
	
	public Component(String n, Component p) {
		name = n;
		parent = p;
		children = new HashMap<Component,Double>();
		
	}
	
	public Component(String n, ArrayList<Component> c, ArrayList<Double> per) {
		name = n;
		children = new HashMap<Component,Double>();
	}
	
	public Component(String n, Component p, ArrayList<Component> c, ArrayList<Double> per) {
		name = n;
		parent = p;
		children = new HashMap<Component,Double>();
	}

	public void addComponentAndScale(Component toAdd, double per) {//adds component and scales the other components by the value
		toAdd.setParent(this);
		if(children.isEmpty()) {
			children.put(toAdd,(double) 100);
		}
		else {
			double newTotal = 100 - per;
			
			double decimalTotal = newTotal / 100; //convert from percentage to decimal
			
			for (Component c : children.keySet()) {
				children.replace(c, (children.get(c) * decimalTotal));
			}
			
			children.put(toAdd,(double) per);
			
		}
		
	}
	
	private void addComponent(Component toAdd, double per) {//adds component and doesn't scale. MUST ONLY be used when it is verified that the total will become 100%
		toAdd.setParent(this);
		children.put(toAdd,(double) per);
	}
	

	
	public void setParent(Component p) {
		parent = p;
	}
	
	
	public void changePercentageAndScale(double newPer) {
		parent.changeChildPercentageAndScale(this,newPer);
	}
	
	public void changeChildPercentageAndScale(Component c, double newPer) {//doesn't really work yet
		double difference = newPer - children.get(c);
		double decimalTotal = (100+difference) / 100;
		
		for (Component child : children.keySet()) {
			if (child == c) {
				children.replace(child, newPer);
			}
			else {
				children.replace(child, (children.get(child) * decimalTotal));
			}
		}
	}
	
	public double getPercentage() {//returns the percentage of this component, according to it's parent
		if(parent == null) {
			return 100.0;
		}
		return parent.children.get(this);
	}
	
	public String toString() {
		
		return name + " %" + getPercentage();
		/***
		if(children.isEmpty()) {
			return "[" + name + "]";
		}
		else {
			String ret = "";
			for (Component c : children.keySet()) {
				ret = ret + " " + children.get(c) + c;
			}
			return "[" + name + ret + "]";
		}
		***/
	}

	public Component deepCopy() {
		Component deepCopy = new Component(name);
		Component childCopy;
		
		for (Component c : children.keySet()) {
			childCopy = c.deepCopy();
			deepCopy.addComponent(childCopy, children.get(c));
		}
		
		return deepCopy;
	}



}
