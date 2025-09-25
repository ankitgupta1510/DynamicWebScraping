package models;

public class HealthPolicy implements Policy {
 
    private String category, planName, coverAmount, monthlyPremium, logoUrl;

    public HealthPolicy(String category, String planName, String coverAmount, String monthlyPremium, String logoUrl) {
        this.category = category;
        this.planName = planName;
        this.coverAmount = coverAmount;
        this.monthlyPremium = monthlyPremium;
        this.logoUrl = logoUrl;
    }

    @Override
    public String[] toArray() {
        return new String[]{category, planName, coverAmount, monthlyPremium, logoUrl};
    }
}