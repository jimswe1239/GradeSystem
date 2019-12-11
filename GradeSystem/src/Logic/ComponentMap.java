package Logic;
import java.util.Collection;
import java.util.HashMap;

public class ComponentMap implements java.io.Serializable{

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
	
	public Component importComponent(Component c) {
		return c.deepCopy();
	}
	

	public Collection<Component> getComponents() {
		return cMap.values();
		
	}

}
