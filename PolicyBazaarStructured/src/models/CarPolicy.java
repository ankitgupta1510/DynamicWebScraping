package models;

public class CarPolicy implements Policy{
	private String planType, premium, logoUrl;

    public CarPolicy(String planType,  String premium, String logoUrl) {
        this.planType = planType;
       
        
        this.premium = premium;
        this.logoUrl = logoUrl;
    }

    @Override
    public String[] toArray() {
        return new String[]{planType, premium, logoUrl};
    }

}
