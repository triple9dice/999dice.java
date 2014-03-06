package dice.client.web;

import java.math.BigDecimal;

public final class SessionInfo {
	String sessionCookie, accountCookie, email, emergencyAddress,
			depositAddress, username;
	long accountId, clientSeed, betCount, betWinCount;
	int maxBetBatchSize;
	BigDecimal betPayIn = BigDecimal.ZERO;
	BigDecimal betPayOut = BigDecimal.ZERO;
	BigDecimal balance = BigDecimal.ZERO;

	SessionInfo(String _sessionCookie, long _accountId, int _maxBetBatchSize) {
		sessionCookie = _sessionCookie;
		accountId = _accountId;
		maxBetBatchSize = _maxBetBatchSize;
	}

	private static BigDecimal forceNotNull(BigDecimal num) {
		return num == null ? BigDecimal.ZERO : num;
	}

	public String getSessionCookie() {
		return sessionCookie;
	}

	public String getAccountCookie() {
		return accountCookie;
	}

	public String getEmail() {
		return email;
	}

	public String getEmergencyAddress() {
		return emergencyAddress;
	}

	public String getDepositAddress() {
		return depositAddress;
	}

	public String getUsername() {
		return username;
	}

	public long getAccountId() {
		return accountId;
	}

	public long getClientSeed() {
		return clientSeed;
	}

	public long getBetCount() {
		return betCount;
	}

	public long getBetWinCount() {
		return betWinCount;
	}

	public int getMaxBetBatchSize() {
		return maxBetBatchSize;
	}

	public BigDecimal getBetPayIn() {
		return forceNotNull(betPayIn);
	}

	public BigDecimal getBetPayOut() {
		return forceNotNull(betPayOut);
	}

	public BigDecimal getBalance() {
		return forceNotNull(balance);
	}

}
