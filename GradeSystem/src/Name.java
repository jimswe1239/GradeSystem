
public class Name {//the name of a student
	private String fName;
	private String mName;
	private String lName;
	
	public Name(String f, String l) {
		fName = f;
		mName = "";
		lName = l;
	}
	
	public Name(String f, String m, String l) {
		fName = f;
		mName = m;
		lName = l;
	}
	
	public String toString() {
		return lName + ", " +fName + (mName.equals("")?"":(" "+mName.charAt(0)+ "."));
	}
}
