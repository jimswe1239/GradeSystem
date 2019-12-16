package Logic;

public class Name implements java.io.Serializable{//the name of a student
	private String fName;
	private String mName;
	private String lName;
	
	public String getfName() {
		return fName;
	}

	public String getlName() {
		return lName;
	}

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

	public Name deepCopy()
	{
		Name copy = new Name(fName, mName, lName);
		return copy;
	}
}
