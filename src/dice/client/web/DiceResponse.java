package dice.client.web;

import javax.json.JsonObject;

public abstract class DiceResponse {
	boolean success;
	int webStatusCode;
	String errorMessage;
	private JsonObject rawResponse;
	boolean rateLimited;
	
	DiceResponse(){}
	void setRawResponse(JsonObject resp)
	{
		rawResponse = resp;
		if (resp.containsKey("error") && !resp.isNull("error"))
			errorMessage=resp.getString("error");
		if (resp.containsKey("TooFast"))
			rateLimited=true;
		if (resp.containsKey("success"))
			success=true;		
	}
	public boolean isSuccess() {
		return success;
	}
	public int getWebStatusCode() {
		return webStatusCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public JsonObject getRawResponse() {
		return rawResponse;
	}
	public boolean isRateLimited() {
		return rateLimited;
	}
}
