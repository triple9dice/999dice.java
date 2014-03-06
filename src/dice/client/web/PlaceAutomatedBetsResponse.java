package dice.client.web;

import java.math.BigDecimal;
import java.math.MathContext;

import javax.json.JsonArray;
import javax.json.JsonObject;

public final class PlaceAutomatedBetsResponse extends DiceResponse {
	private boolean chanceTooHigh, chanceTooLow, insufficientFunds,
			noPossibleProfit, maxPayoutExceeded;

	long[] betIds, secrets;
	BigDecimal[] payIns, payOuts;
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
		else if (resp.containsKey("BetId") && resp.containsKey("PayIns")
				&& resp.containsKey("PayOuts") && resp.containsKey("Secrets")
				&& resp.containsKey("StartingBalance")) {
			success = true;
			Long betId = (Long) resp.getJsonNumber("BetId").longValue();
			JsonArray pi = resp.getJsonArray("PayIns");
			JsonArray po = resp.getJsonArray("PayOuts");
			JsonArray s = resp.getJsonArray("Secrets");
			startingBalance = resp.getJsonNumber("StartingBalance")
					.bigDecimalValue()
					.divide(new BigDecimal(100000000), MathContext.DECIMAL128);

			payIns = new BigDecimal[s.size()];
			payOuts = new BigDecimal[s.size()];
			secrets = new long[s.size()];
			betIds = new long[s.size()];

			for (int x = 0; x < betIds.length; ++x) {
				payIns[x] = pi
						.getJsonNumber(x)
						.bigDecimalValue()
						.divide(new BigDecimal(100000000),
								MathContext.DECIMAL128);
				payOuts[x] = po
						.getJsonNumber(x)
						.bigDecimalValue()
						.divide(new BigDecimal(100000000),
								MathContext.DECIMAL128);
				secrets[x] = s.getJsonNumber(x).longValue();
				betIds[x] = x + betId;
			}
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

	public long[] getBetIds() {
		return betIds;
	}

	public long[] getSecrets() {
		return secrets;
	}

	public BigDecimal[] getPayIns() {
		return payIns;
	}

	public BigDecimal[] getPayOuts() {
		return payOuts;
	}

	public BigDecimal getStartingBalance() {
		return startingBalance;
	}

}
