package models;

public class BikePolicy implements Policy{
	private String planType, planName, premium, logoUrl;

    public BikePolicy(String planType, String planName, String premium, String logoUrl) {
        this.planType = planType;
        this.planName = planName;
        
        this.premium = premium;
        this.logoUrl = logoUrl;
    }

    @Override
    public String[] toArray() {
        return new String[]{planType, planName, premium, logoUrl};
    }
}
