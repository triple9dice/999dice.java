package dice.client.web;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.json.JsonObject;

public final class PlaceBetResponse extends DiceResponse {
	boolean chanceTooHigh, chanceTooLow, insufficientFunds, noPossibleProfit,
			maxPayoutExceeded;
	long betId, secret;
	BigDecimal payOut = BigDecimal.ZERO;
	BigDecimal startingBalance;

	@Override
	void setRawResponse(JsonObject resp) {
		super.setRawResponse(resp);

		if (resp.containsKey("ChanceTooHigh"))
			chanceTooHigh = true;
		else if (resp.containsKey("ChanceTooLow"))
			chanceTooLow = true;
		else if (resp.containsKey("InsufficientFunds"))
			insufficientFunds = true;
		else if (resp.containsKey("NoPossibleProfit"))
			noPossibleProfit = true;
		else if (resp.containsKey("maxPayoutExceeded"))
			maxPayoutExceeded = true;
		else if (resp.containsKey("BetId") && resp.containsKey("PayOut")
				&& resp.containsKey("Secret")
				&& resp.containsKey("StartingBalance")) {
			success = true;
			betId = resp.getJsonNumber("BetId").longValue();
			payOut = resp.getJsonNumber("PayOut").bigDecimalValue()
					.divide(new BigDecimal(100000000), MathContext.DECIMAL128);
			secret = resp.getJsonNumber("Secret").longValue();
			startingBalance = resp.getJsonNumber("StartingBalance")
					.bigDecimalValue()
					.divide(new BigDecimal(100000000), MathContext.DECIMAL128);
		}
	}

	public boolean isChanceTooHigh() {
		return chanceTooHigh;
	}

	public boolean isChanceTooLow() {
		return chanceTooLow;
	}

	public boolean isInsufficientFunds() {
		return insufficientFunds;
	}

	public boolean isNoPossibleProfit() {
		return noPossibleProfit;
	}

	public boolean isMaxPayoutExceeded() {
		return maxPayoutExceeded;
	}

	public long getBetId() {
		return betId;
	}

	public long getSecret() {
		return secret;
	}

	public BigDecimal getPayOut() {
		return payOut;
	}

	public BigDecimal getStartingBalance() {
		return startingBalance;
	}

}
