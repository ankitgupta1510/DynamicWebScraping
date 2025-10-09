package com.grow.gradle;

public class MutualFund {
    private String fundName;
    private String fundAUM;
    private String fiveYrReturn;
    private String logourl;

    public MutualFund(String fundName, String fundAUM, String fiveYrReturn, String logourl) {
        this.fundName = fundName;
        this.fundAUM = fundAUM;
        this.fiveYrReturn = fiveYrReturn;
        this.logourl = logourl;
    }

    @Override
    public String toString() {
        return "MutualFund{" + "name='" + fundName + '\'' + ", aum=" + fundAUM + ", fiveYrReturn=" + fiveYrReturn
                + ", url='" + logourl + '\'' + '}';
    }


    public String getName() {
        return fundName;
    }

    public String getAum() {
        return fundAUM;
    }

    public String getReturn() {
        return fiveYrReturn;
    }

    public String getLogo() {
        return logourl;
    }

}
