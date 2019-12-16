package Logic;
//import java.util.ArrayList;

//import java.util.HashMap;
import java.util.LinkedHashMap;

public class Component implements java.io.Serializable{

	String name;
	
	private Component parent; //if == null, then this is root
	
	public LinkedHashMap<Component,Double> children;
	
	public boolean valid;
	
	//Various constructors
	
	//constructors when children are not known
	
	public Component(String n) {
		name = n;
		children = new LinkedHashMap<Component,Double>();
		valid = true;
		
	}
	
	public Component(String n, Component p) {
		name = n;
		parent = p;
		children = new LinkedHashMap<Component,Double>();
		if(isXPercent(100,children)) {
			valid = true;
		}
		else{
			valid = false;
		}
	}

	public String getName()
	{
		return name;
	}

	/*** Constructors when children are already known
	public Component(String n, ArrayList<Component> c, ArrayList<Double> per) {
		name = n;
		children = new HashMap<Component,Double>();
	}
	
	public Component(String n, Component p, ArrayList<Component> c, ArrayList<Double> per) {
		name = n;
		parent = p;
		children = new HashMap<Component,Double>();
	}
	***/

	public boolean isComponentNameExist(Component toAdd) {
		boolean isExist = false;
		
		for(Component c: children.keySet()) {
			if(c.getName().matches(toAdd.getName())) {
				isExist = true;
			}
		}
		
		return isExist;
	}

	public void addComponentAndScale(Component toAdd, double per) {//adds component and scales the other children according to the value added
		toAdd.setParent(this);
		if(children.isEmpty()) {
			children.put(toAdd,(double) 100);
		}
		else {
			double newTotal = 100 - per;
			
			double decimalTotal = newTotal / 100; //convert from percentage to decimal
			
			if(this.getSumScaleOfChildren()+per>100) {
				for (Component c : children.keySet()) {
					children.replace(c, (children.get(c) * decimalTotal));
				}
			}
			
			children.put(toAdd,(double) per);
			
		}
		
	}
	
	public void addComponent(Component toAdd) {//adds component worth 0% and doesn't scale
		//double per should be 0 if the application isn't going to exceed 100%
		//this can be used if the user wants to add a component and decide it's value later
		toAdd.setParent(this);
		children.put(toAdd,(double) 0);
	}
	
	private void addComponent(Component toAdd, double per) {//adds component and doesn't scale. MUST ONLY be used when it is verified that the total will become 100%
		//DO NOT USE WHEN TAKING THE USER INPUT, this will not have the percentages of a child component equal 100. This method is used by the deep copy
		//double per should be 0 if the application isn't going to exceed 100%
		toAdd.setParent(this);
		children.put(toAdd,(double) per);
		if(per != 0) {
			valid = isXPercent(100,children);
		}
		
	}
	

	
	public void setParent(Component p) {
		parent = p;
	}
	
	
	public void changePercentageAndScale(double newPer) {
		parent.changeChildPercentageAndScale(this,newPer);
	}
	
	public void changeChildPercentageAndScale(Component c, double newPer) {
		double difference = newPer - children.get(c);
		double decimalTotal = (100-difference) / 100;
		
		for (Component child : children.keySet()) {
			if (child == c) {
				children.replace(child, newPer);
			}
			else if(getSumScaleOfChildren()>100) {
				children.replace(child, (children.get(child) * decimalTotal));
			}
		}
	}
	
	public double getSumScaleOfChildren() {
		double sum = 0;
		for (Component child : children.keySet()) {
			sum += children.get(child);
		}
		return sum;
	}
	
	public LinkedHashMap<Component, Double> getChildren(){//use this to get the old values of all of the children, so you can display them as defaults when adding a new component and manually changing the old components. You should then make a keySet of the components in this HashMap, and create a new hashMap with all of the old keys, plus then new one, and use that set of keys, and the values that the user enters, to create a new hashmap to pass into changeAllChildren
		return children;
	}
	
	public boolean changeAllChildren(LinkedHashMap<Component, Double> newChildren) {//when changing all children, you need to manually set the new values in the map
		//This function CAN BE USED to add a new child, if the new children hashMap contains a new child, then it will be added here.
		//So, when using this function, create a new Component, call the getChildren method, and then add the new component to those children, then use this function.
		if(isXPercent(100,newChildren)) {
			valid = true;
			children = newChildren;
			return true;
		}
		valid = false;
		return false;
	}
	
	public static boolean isXPercent(double d, LinkedHashMap<Component, Double> newChildren){
		double total = 0;
		for (Double childPercentage : newChildren.values()) {
			total += childPercentage;
			if(total > d) {
				return false;
			}
		}
		if(total == d) {
			return true;
		}
		return false;
	}
	
	public double getPercentage() {//returns the percentage of this component, according to it's parent
		if(parent == null) {
			return 100.0;
		}
		if(parent.children.get(this)!=null) {
			return parent.children.get(this);
		}
		else return 0;
	}
	
	public String toString() {
		
		return name + " [" + getPercentage() + "%]";
		/***for debugging
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
	
	public boolean isLeaf(){
		return children.isEmpty();
	}

	public Component getParent() {
		return parent;
	}
	
	public void deleteComponent(Component c) {
		this.children.remove(c);
	}

	public void rename(String name){
		this.name=name;
	}



}
