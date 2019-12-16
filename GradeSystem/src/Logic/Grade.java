package Logic;
import java.util.HashMap;

public class Grade implements java.io.Serializable{//grade is a score map!
	HashMap<Component,Score> sMap;
	public HashMap<Component, Score> getsMap() {
		return sMap;
	}

	public void setsMap(HashMap<Component, Score> sMap) {
		this.sMap = sMap;
	}

	double endBonus;
	
	public Grade() {
		endBonus = 0;
		sMap = new HashMap<Component,Score>();
	}

	public void putScore(Component component, Score score) {
		sMap.put(component, score);
	}
	
	public double getFinalScore(Component root) {

		if(root.isLeaf()) {
			if(!sMap.containsKey(root)){
				return 0.0;
			}
			return sMap.get(root).getPercentage();
		}
		double score = 0;
		for(Component child : root.getChildren().keySet()) {
			score += (root.getChildren().get(child))*.01 * getFinalScore(child);
		}
		return score + ((root.getParent() == null)?endBonus:0);//add the endBonus but only if root is the actual template root
	}
	
	public void setEndBonus(double d) {
		endBonus = d;
	}

	public Grade deepCopy()
    {
        Grade copy = new Grade();
        for(Component component : sMap.keySet())
        {
            Score score = sMap.get(component);
            copy.sMap.put(component, score.deepCopy());
        }

        return copy;
    }

}
