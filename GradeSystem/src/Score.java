
public class Score {
	private String comment;
	private double percentageGot;
	private double percentage;
	
	public Score(double p, double pg) {
		this.percentage = p;
		this.percentageGot = pg;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public double getPercentageGot() {
		return percentageGot;
	}
	public void setPercentageGot(double percentageGot) {
		this.percentageGot = percentageGot;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public void ModifyComment(String c) {
		this.comment = c;
	}
}
