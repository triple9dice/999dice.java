package dice.client.web;

import javax.json.JsonObject;

public final class CreateUserResponse extends DiceResponse {
	boolean accountAlreadyHasUser;
	boolean usernameAlreadyTaken;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);
		if (resp.containsKey("AccountHasUser"))
			accountAlreadyHasUser = true;
		if (resp.containsKey("UsernameTaken"))
			usernameAlreadyTaken = true;
	}

	public boolean isAccountAlreadyHasUser() {
		return accountAlreadyHasUser;
	}

	public boolean isUsernameAlreadyTaken() {
		return usernameAlreadyTaken;
	}

}
