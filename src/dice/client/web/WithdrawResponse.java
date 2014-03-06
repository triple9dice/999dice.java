package dice.client.web;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.json.JsonObject;

public final class WithdrawResponse extends DiceResponse {
	BigDecimal withdrawalPending = BigDecimal.ZERO;
	boolean withdrawalTooSmall, insufficientFunds;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);

		if (resp.containsKey("TooSmall"))
			withdrawalTooSmall = true;
		else if (resp.containsKey("InsufficientFunds"))
			insufficientFunds = true;
		else if (resp.containsKey("Pending")) {
			withdrawalPending = resp.getJsonNumber("Pending").bigDecimalValue()
					.divide(new BigDecimal(100000000), MathContext.DECIMAL128);
			success = true;
		}
	}

	public BigDecimal getWithdrawalPending() {
		return withdrawalPending;
	}

	public boolean isWithdrawalTooSmall() {
		return withdrawalTooSmall;
	}

	public boolean isInsufficientFunds() {
		return insufficientFunds;
	}

}
