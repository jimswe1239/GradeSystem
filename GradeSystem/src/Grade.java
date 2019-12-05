import java.util.Map;

public class Grade {
	private Map<Component, Score> scoreMap;
	private Course course;
	
	public Grade(Course course) {
		scoreMap.put(course.getRoot(), new Score(100,100));
		getNextLayerGrade();
	}
	public void getNextLayerGrade() {
		for (Component c: scoreMap.keySet()) {
			if(c.isLeaf()==false) {
				for(Component key: c.children.keySet()) {
					scoreMap.put(key, new Score(scoreMap.get(c).getPercentage()*c.children.get(key), 0));
				}
				scoreMap.remove(c);
			}
		}
	}
}
