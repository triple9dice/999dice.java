package dice.client.web;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.json.JsonObject;

public final class BeginSessionResponse extends DiceResponse {
	SessionInfo session;
	boolean invalidApiKey, loginRequired, wrongUsernameOrPassword;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);
		if (resp.containsKey("InvalidApiKey")) {
			invalidApiKey = true;
			return;
		}
		if (resp.containsKey("LoginRequired")) {
			loginRequired = true;
			return;
		}
		if (resp.containsKey("LoginInvalid")) {
			wrongUsernameOrPassword = true;
			return;
		}
		if (resp.containsKey("SessionCookie") && !resp.isNull("SessionCookie")
				&& resp.containsKey("AccountId")
				&& resp.containsKey("MaxBetBatchSize")) {
			success = true;
			session = new SessionInfo(resp.getString("SessionCookie"), resp
					.getJsonNumber("AccountId").longValue(),
					resp.getInt("MaxBetBatchSize"));
			if (resp.containsKey("AccountCookie")
					&& !resp.isNull("AccountCookie"))
				session.accountCookie = resp.getString("AccountCookie");
			if (resp.containsKey("ClientSeed"))
				session.clientSeed = resp.getJsonNumber("ClientSeed")
						.longValue();
			if (resp.containsKey("BetCount"))
				session.betCount = resp.getJsonNumber("BetCount").longValue();
			if (resp.containsKey("BetPayIn"))
				session.betPayIn = resp
						.getJsonNumber("BetPayIn")
						.bigDecimalValue()
						.divide(new BigDecimal(100000000),
								MathContext.DECIMAL128);
			if (resp.containsKey("BetPayOut"))
				session.betPayOut = resp
						.getJsonNumber("BetPayOut")
						.bigDecimalValue()
						.divide(new BigDecimal(100000000),
								MathContext.DECIMAL128);
			if (resp.containsKey("BetWinCount"))
				session.betWinCount = resp.getJsonNumber("BetWinCount")
						.longValue();
			if (resp.containsKey("Balance"))
				session.balance = resp
						.getJsonNumber("Balance")
						.bigDecimalValue()
						.divide(new BigDecimal(100000000),
								MathContext.DECIMAL128);
			if (resp.containsKey("Email") && !resp.isNull("Email"))
				session.email = resp.getString("Email");
			if (resp.containsKey("EmergencyAddress")
					&& !resp.isNull("EmergencyAddress"))
				session.emergencyAddress = resp.getString("EmergencyAddress");
			if (resp.containsKey("DepositAddress")
					&& !resp.isNull("DepositAddress"))
				session.depositAddress = resp.getString("DepositAddress");
		}
	}

	public SessionInfo getSession() {
		return session;
	}

	public boolean isInvalidApiKey() {
		return invalidApiKey;
	}

	public boolean isLoginRequired() {
		return loginRequired;
	}

	public boolean isWrongUsernameOrPassword() {
		return wrongUsernameOrPassword;
	}

}
