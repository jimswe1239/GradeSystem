
public class Score {
	private double percentage;
	private String comment;
	
	public Score() {
		percentage = 0;
		comment = "";
	}
	
	public Score(double p) {
		percentage = p;
		comment = "";
	}
	
	public Score(double p, String s) {
		percentage = p;
		comment = s;
	}
	
	public void setPercentage(double p) {
		percentage = p;
	}
	
	public double getPercentage() {
		return percentage;
	}
	
	public void setComment(String s) {
		comment = s;
	}
	
	public void removeComment() {
		comment = "";
	}
	
	public String getComment() {
		return comment;
	}
	
	public boolean hasComment() {
		return !comment.equals("");
	}
	
	public String toString() {
		return String.valueOf(percentage) + "%" + (this.hasComment()?"*":"");
	}

}
