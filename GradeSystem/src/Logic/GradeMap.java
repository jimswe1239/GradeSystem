package Logic;
import java.util.HashMap;

public class GradeMap implements java.io.Serializable{
	HashMap<Student,Grade> gMap;
	
	public GradeMap() {
		gMap = new HashMap<Student,Grade>();
	}
	
	public void putScore(Student student, Score score, Component component) {
		
		if(!component.isLeaf()) {
			for(Component child : component.getChildren().keySet()) {//ONLY children will be stored in the GradeMap
				this.putScore(student, score, child);
			}
		}
		else {
			if(gMap.containsKey(student)) {
				gMap.get(student).putScore(component,score);
			}
			else {
				gMap.put(student, new Grade());
				gMap.get(student).putScore(component,score);
			}
		}
		
	}
	
	public Grade getGrade(Student s) {
		return gMap.get(s);
	}
}
