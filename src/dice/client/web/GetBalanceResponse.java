package dice.client.web;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.json.JsonObject;

public final class GetBalanceResponse extends DiceResponse {
	BigDecimal balance = BigDecimal.ZERO;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);

		if (resp.containsKey("Balance")) {
			success = true;
			balance = resp.getJsonNumber("Balance").bigDecimalValue()
					.divide(new BigDecimal(100000000), MathContext.DECIMAL128);
		}
	}

	public BigDecimal getBalance() {
		return balance;
	}
}
