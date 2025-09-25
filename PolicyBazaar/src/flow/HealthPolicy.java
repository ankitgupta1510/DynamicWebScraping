package flow;

public class HealthPolicy {
	private String category;
	private String planName;
	private String logoUrl;
	private String coverAmount;
	private String monthlyPremium;
	
	public HealthPolicy(String category, String planName, String coverAmount, String monthlyPremium, String logoUrl) {
        this.category = category;
        this.planName = planName;
        this.coverAmount = coverAmount;
        this.monthlyPremium = monthlyPremium;
        this.logoUrl = logoUrl;
    }
	
	public String[] toArray() {
        return new String[]{this.category, this.planName, this.coverAmount, this.monthlyPremium, this.logoUrl};
    }

}
