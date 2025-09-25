package models;

public class InternationalTravelPolicy implements Policy {
	 private String category,planName, insurer, logoUrl, startingAt, personalAccident;

	    public InternationalTravelPolicy(String category,String planName, String insurer, String logoUrl, String startingAt, String personalAccident) {
	    	this.category=category;
	        this.planName = planName;
	        this.insurer = insurer;
	        this.logoUrl = logoUrl;
	        this.startingAt = startingAt;
	        this.personalAccident = personalAccident;
	    }

	    @Override
	    public String[] toArray() {
	        return new String[]{category,planName, insurer, logoUrl, startingAt, personalAccident};
	    }

}
