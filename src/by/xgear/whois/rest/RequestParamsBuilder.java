package by.xgear.whois.rest;

public interface RequestParamsBuilder {
	
    @UriFormat(format = "?method=tasteometer.compare&type1=user&type2=user&value1=%s&value2=%s&api_key=d86fd4069079544ac4dac58802636041&format=json")
    String compareTaste(String curUser, String partyUser);

    @UriFormat(format = "?method=user.getInfo&user=%s&api_key=d86fd4069079544ac4dac58802636041&format=json")
    String getUserInfo(String username);
}
