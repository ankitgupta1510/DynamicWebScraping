package models;


public class TermPolicy implements Policy {
    // ... (Fields and Constructor same as before)
    private String planName, lifeCover, coverUpto, claimSettled, monthlyPremium, logoUrl;

    public TermPolicy(String planName, String lifeCover, String coverUpto, String claimSettled, String monthlyPremium, String logoUrl) {
        this.planName = planName;
        this.lifeCover = lifeCover;
        this.coverUpto = coverUpto;
        this.claimSettled = claimSettled;
        this.monthlyPremium = monthlyPremium;
        this.logoUrl = logoUrl;
    }

    @Override
    public String[] toArray() {
        return new String[]{planName, lifeCover, coverUpto, claimSettled, monthlyPremium, logoUrl};
    }
}
