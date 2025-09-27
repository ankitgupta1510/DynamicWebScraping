package models;

public class CarPolicy implements Policy{
	private String planType, planName, premium, logoUrl;

    public CarPolicy(String planType, String planName, String premium, String logoUrl) {
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
