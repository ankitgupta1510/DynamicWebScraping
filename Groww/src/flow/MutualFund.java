package flow;

public class MutualFund {
	private String fundName;
	private Double fundAUM;
	private Double fiveYrReturn;
	
	public MutualFund(String fundName,Double fundAUM,Double fiveYrReturn) {
		this.fundName = fundName;
		this.fundAUM = fundAUM;
		this.fiveYrReturn = fiveYrReturn;
	}
	@Override
	public String toString() {
	    return "MutualFund{name='" + fundName + "', aum=" + fundAUM+ ", fiveYrReturn=" + fiveYrReturn + "}";
	}
	
	public String getName() {
		return fundName;
	}
	
	public Double getAum() {
		return fundAUM;
	}
	public Double getReturn() {
		return fiveYrReturn;
	}

}
