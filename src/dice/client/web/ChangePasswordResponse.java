package dice.client.web;

import javax.json.JsonObject;

public final class ChangePasswordResponse extends DiceResponse {
	boolean wrongPassword;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);

		if (resp.containsKey("WrongPassword"))
			wrongPassword = true;
	}

	public boolean isWrongPassword() {
		return wrongPassword;
	}

}
