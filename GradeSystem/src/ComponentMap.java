import java.util.HashMap;

public class ComponentMap {

	private HashMap<String,Component> cMap;
	
	public ComponentMap(){
		cMap = new HashMap<String,Component>();
	}
	
	public void exportComponent(Component c) {
		Component deepCopy = c.deepCopy();
		cMap.put(deepCopy.name, deepCopy);
	}
	
	public Component importComponent(String name) {
		return cMap.get(name).deepCopy();
	}
}
