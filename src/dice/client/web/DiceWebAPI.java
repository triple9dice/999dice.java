package dice.client.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;

public class DiceWebAPI {
	static private final String WebUri = "https://www.999dice.com/api/web.aspx";
	static private final long GuessSpan = 1000000;
	static private final BigDecimal HousePayout = new BigDecimal("0.999");

	static private final JsonReaderFactory jsonReaderFactoryInstance = Json
			.createReaderFactory(null);

	private static DiceResponse Request(Map<String, String> formData,
			DiceResponse response) {
		try {
			URL url = new URL(WebUri);
			StringBuilder sb = new StringBuilder();
			for (Entry<String, String> kvp : formData.entrySet()) {
				if (sb.length() > 0)
					sb.append('&');
				sb.append(URLEncoder.encode(kvp.getKey(), "UTF-8"));
				sb.append("=");
				sb.append(URLEncoder.encode(kvp.getValue(), "UTF-8"));
			}
			String form = sb.toString();
			HttpURLConnection client = (HttpURLConnection) url.openConnection();
			try {
				client.setRequestProperty("Content-type",
						"application/x-www-form-urlencoded; charset=UTF-8");
				client.setRequestProperty("Content-length",String.valueOf(form.getBytes().length));
				client.setRequestMethod("POST");
				client.setAllowUserInteraction(false);
				client.setUseCaches(false);
				client.setDoInput(true);
				client.setDoOutput(true);
				client.connect();
				try (OutputStreamWriter w = new OutputStreamWriter(
						client.getOutputStream())) {
					w.write(form);
				}
				response.webStatusCode = client.getResponseCode();
				try (InputStream cis = client.getInputStream();
						JsonReader r = jsonReaderFactoryInstance
								.createReader(cis)) {
					response.setRawResponse(r.readObject());
				}
			} finally {
				client.disconnect();
			}
		} catch (MalformedURLException e) {
			response.errorMessage = e.getMessage();
		} catch (IOException e) {
			response.errorMessage = e.getMessage();
		}
		return response;
	}

	public static long toSatoshis(BigDecimal amount) {
		return amount.multiply(new BigDecimal(100000000),
				MathContext.DECIMAL128).longValue();
	}

	static Map<String, String> GetFormDataCreateAccount(String apiKey) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "CreateAccount");
		x.put("Key", apiKey);
		return x;
	}

	static Map<String, String> GetFormDataBeginSession(String apiKey,
			String accountCookie) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "BeginSession");
		x.put("Key", apiKey);
		x.put("AccountCookie", accountCookie);
		return x;
	}

	static Map<String, String> GetFormDataLogin(String apiKey, String username,
			String password) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "Login");
		x.put("Key", apiKey);
		x.put("Username", username);
		x.put("Password", password);
		return x;
	}

	static Map<String, String> GetFormDataCreateUser(String sessionCookie,
			String username, String password) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "CreateUser");
		x.put("s", sessionCookie);
		x.put("Username", username);
		x.put("Password", password);
		return x;
	}

	static Map<String, String> GetFormDataWithdraw(String sessionCookie,
			BigDecimal amount, String address) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "Withdraw");
		x.put("s", sessionCookie);
		x.put("Amount", String.valueOf(toSatoshis(amount)));
		x.put("Address", address);
		return x;
	}

	static Map<String, String> GetFormDataChangePassword(String sessionCookie,
			String oldPassword, String newPassword) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "ChangePassword");
		x.put("s", sessionCookie);
		x.put("OldPassword", oldPassword);
		x.put("NewPassword", newPassword);
		return x;
	}

	static Map<String, String> GetFormDataGetServerSeedHash(String sessionCookie) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "GetServerSeedHash");
		x.put("s", sessionCookie);
		return x;
	}

	static Map<String, String> GetFormDataSetClientSeed(String sessionCookie,
			long seed) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "SetClientSeed");
		x.put("s", sessionCookie);
		x.put("Seed", String.valueOf(seed));
		return x;
	}

	static Map<String, String> GetFormDataUpdateEmail(String sessionCookie,
			String email) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "UpdateEmail");
		x.put("s", sessionCookie);
		x.put("Email", email);
		return x;
	}

	static Map<String, String> GetFormDataUpdateEmergencyAddress(
			String sessionCookie, String address) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "UpdateEmergencyAddress");
		x.put("s", sessionCookie);
		x.put("Address", address);
		return x;
	}

	static Map<String, String> GetFormDataGetBalance(String sessionCookie) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "GetBalance");
		x.put("s", sessionCookie);
		return x;
	}

	static Map<String, String> GetFormDataGetDepositAddress(String sessionCookie) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "GetDepositAddress");
		x.put("s", sessionCookie);
		return x;
	}

	static Map<String, String> GetFormDataPlaceBet(String sessionCookie,
			BigDecimal payIn, long guessLow, long guessHigh) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "PlaceBet");
		x.put("s", sessionCookie);
		x.put("PayIn", String.valueOf(toSatoshis(payIn)));
		x.put("Low", String.valueOf(guessLow));
		x.put("High", String.valueOf(guessHigh));
		return x;
	}

	static Map<String, String> GetFormDataPlaceAutomatedBets(
			String sessionCookie, AutomatedBetsSettings settings) {
		Map<String, String> x = new HashMap<String, String>();
		x.put("a", "PlaceAutomatedBets");
		x.put("s", sessionCookie);
		x.put("BasePayIn",String.valueOf(toSatoshis(settings.getBasePayIn())));
		x.put("Low", String.valueOf(settings.getGuessLow()));
		x.put("High", String.valueOf(settings.getGuessHigh()));
		x.put("MaxBets", String.valueOf(settings.getMaxBets()));
		x.put("ResetOnWin", settings.isResetOnWin() ? "1" : "0");
		x.put("ResetOnLose", settings.isResetOnLose() ? "1" : "0");
		x.put("IncreaseOnWinPercent", settings.getIncreaseOnWinPercent()
				.toPlainString());
		x.put("IncreaseOnLosePercent", settings.getIncreaseOnLosePercent()
				.toPlainString());
		x.put("MaxPayIn",String.valueOf(toSatoshis(settings.getMaxAllowedPayIn())));
		x.put("ResetOnLoseMaxBet", settings.isResetOnLoseMaxBet() ? "1" : "0");
		x.put("StopOnLoseMaxBet", settings.isStopOnLoseMaxBet() ? "1" : "0");
		x.put("StopMaxBalance",String.valueOf(toSatoshis(settings.getStopMaxBalance())));
		x.put("StopMinBalance",String.valueOf(toSatoshis(settings.getStopMinBalance())));
		x.put("StartingPayIn",String.valueOf(toSatoshis(settings.getStartingPayIn())));
		return x;
	}

	static BeginSessionResponse Process(BeginSessionResponse res,
			String username) {
		if (res.isSuccess() && res.getSession() != null)
			res.getSession().username = username;
		return res;
	}

	static CreateUserResponse Process(SessionInfo session,
			CreateUserResponse res, String username) {
		if (res.isSuccess())
			session.username = username;
		return res;
	}

	static GetBalanceResponse Process(SessionInfo session,
			GetBalanceResponse res) {
		if (res.isSuccess())
			session.balance = res.getBalance();
		return res;
	}

	static WithdrawResponse Process(SessionInfo session, WithdrawResponse res) {
		if (res.isSuccess())
			session.balance = session.balance.subtract(res.withdrawalPending);
		return res;
	}

	static SetClientSeedResponse Process(SessionInfo session,
			SetClientSeedResponse res, long seed) {
		if (res.isSuccess())
			session.clientSeed = seed;
		return res;
	}

	static UpdateEmailResponse Process(SessionInfo session,
			UpdateEmailResponse res, String email) {
		if (res.isSuccess())
			session.email = email;
		return res;
	}

	static UpdateEmergencyAddressResponse Process(SessionInfo session,
			UpdateEmergencyAddressResponse res, String emergencyAddress) {
		if (res.isSuccess())
			session.emergencyAddress = emergencyAddress;
		return res;
	}

	static GetDepositAddressResponse Process(SessionInfo session,
			GetDepositAddressResponse res) {
		if (res.isSuccess())
			session.depositAddress = res.depositAddress;
		return res;
	}

	static PlaceBetResponse Process(SessionInfo session, PlaceBetResponse res,
			BigDecimal payIn, long guessLow, long guessHigh) {
		if (res.isSuccess()) {
			++session.betCount;
			session.betPayIn = session.betPayIn.add(payIn,
					MathContext.DECIMAL128);
			session.betPayOut = session.betPayOut.add(res.payOut,
					MathContext.DECIMAL128);
			session.balance = res.startingBalance.add(res.payOut,
					MathContext.DECIMAL128).add(payIn, MathContext.DECIMAL128);
			if (res.secret >= guessLow && res.secret <= guessHigh)
				++session.betWinCount;
		}
		return res;
	}

	static PlaceAutomatedBetsResponse Process(SessionInfo session,
			PlaceAutomatedBetsResponse res, AutomatedBetsSettings settings) {
		if (res.isSuccess()) {
			session.balance = res.startingBalance;
			for (int x = 0; x < res.betIds.length; ++x) {
				session.betPayIn = session.betPayIn.add(res.payIns[x],
						MathContext.DECIMAL128);
				session.betPayOut = session.betPayOut.add(res.payOuts[x],
						MathContext.DECIMAL128);
				session.balance = session.balance.add(res.payIns[x],
						MathContext.DECIMAL128).add(res.payOuts[x],
						MathContext.DECIMAL128);
				if (res.secrets[x] >= settings.getGuessLow()
						&& res.secrets[x] <= settings.getGuessHigh())
					++session.betWinCount;
			}
			session.betCount += res.betIds.length;
		}
		return res;
	}

	private static boolean isEmpty(String s) {
		return s == null || s == "" || s.trim() == "";
	}

	static void Validate(String apiKey) throws IllegalArgumentException {
		if (isEmpty(apiKey))
			throw new IllegalArgumentException();
	}

	static void Validate(String apiKey, String other) {
		if (isEmpty(apiKey) || isEmpty(other))
			throw new IllegalArgumentException();
	}

	static void Validate(SessionInfo session) {
		if (session == null)
			throw new IllegalArgumentException();
	}

	static void Validate(SessionInfo session, String other) {
		if (session == null || isEmpty(other))
			throw new IllegalArgumentException();
	}

	static void Validate(SessionInfo session, String other1, String other2) {
		if (session == null || isEmpty(other1) || isEmpty(other2))
			throw new IllegalArgumentException();
	}

	static void Validate(SessionInfo session, long guessLow, long guessHigh) {
		if (session == null)
			throw new IllegalArgumentException();
		if (guessLow < 0 || guessLow > guessHigh || guessHigh >= GuessSpan)
			throw new IllegalArgumentException("0 <= GuessLow <= GuessHigh <= "
					+ String.valueOf(GuessSpan));
	}

	static void Validate(SessionInfo session, AutomatedBetsSettings settings) {
		Validate(session, settings.getGuessLow(), settings.getGuessHigh());
		if (settings.getMaxBets() < 1
				|| settings.getIncreaseOnWinPercent().signum() < 0
				|| settings.getIncreaseOnLosePercent().signum() < 0)
			throw new IllegalArgumentException();
	}

	static void Validate(BigDecimal maxAllowedPayIn, BigDecimal basePayIn) {
		if (maxAllowedPayIn.signum() != 0
				&& maxAllowedPayIn.compareTo(basePayIn) > 0)
			throw new IllegalArgumentException();
	}

	public static BigDecimal TruncateSatoshis(BigDecimal amt) {
		if (amt.signum() < 0)
			return new BigDecimal(amt.multiply(new BigDecimal(-100000000),
					MathContext.DECIMAL128).longValue()).divide(new BigDecimal(
					-100000000), MathContext.DECIMAL128);
		return new BigDecimal(amt.multiply(new BigDecimal(100000000),
				MathContext.DECIMAL128).longValue()).divide(new BigDecimal(
				100000000), MathContext.DECIMAL128);
	}

	public static BigDecimal CalculateWinPayout(BigDecimal payIn,
			long guessLow, long guessHigh) {
		if (payIn.signum() < 0)
			payIn = payIn.negate();
		payIn = TruncateSatoshis(payIn);
		BigDecimal mul = CalculatePayoutMultiplier(guessLow, guessHigh);
		BigDecimal payout = payIn.multiply(mul, MathContext.DECIMAL128);
		return TruncateSatoshis(payout);
	}

	public static BigDecimal CalculateWinProfit(BigDecimal payIn,
			long guessLow, long guessHigh) {
		if (payIn.signum() < 0)
			payIn = payIn.negate();
		payIn = TruncateSatoshis(payIn);
		BigDecimal payout = CalculateWinPayout(payIn, guessLow, guessHigh);
		return payout.subtract(payIn, MathContext.DECIMAL128);
	}

	public static BigDecimal CalculateChanceToWin(long guessLow, long guessHigh) {
		BigDecimal odds = new BigDecimal(guessHigh - guessLow + 1);
		return odds.divide(new BigDecimal(GuessSpan), MathContext.DECIMAL128);
	}

	public static BigDecimal CalculatePayoutMultiplier(long guessLow,
			long guessHigh) {
		return HousePayout.divide(CalculateChanceToWin(guessLow, guessHigh),
				MathContext.DECIMAL128);
	}

	public static BeginSessionResponse BeginSession(String apiKey) {
		Validate(apiKey);
		return (BeginSessionResponse) Request(GetFormDataCreateAccount(apiKey),
				new BeginSessionResponse());
	}

	public static BeginSessionResponse BeginSession(String apiKey,
			String accountCookie) {
		Validate(apiKey, accountCookie);
		return (BeginSessionResponse) Request(
				GetFormDataBeginSession(apiKey, accountCookie),
				new BeginSessionResponse());
	}

	public static BeginSessionResponse BeginSession(String apiKey,
			String username, String password) {
		Validate(apiKey, username);
		return Process(
				(BeginSessionResponse) Request(
						GetFormDataLogin(apiKey, username, password),
						new BeginSessionResponse()), username);
	}

	public static CreateUserResponse CreateUser(SessionInfo session,
			String username, String password) {
		Validate(session, username, password);
		username = username.trim();
		return Process(
				session,
				(CreateUserResponse) Request(
						GetFormDataCreateUser(session.sessionCookie, username,
								password), new CreateUserResponse()), username);
	}

	public static GetBalanceResponse GetBalance(SessionInfo session) {
		Validate(session);
		return Process(
				session,
				(GetBalanceResponse) Request(
						GetFormDataGetBalance(session.sessionCookie),
						new GetBalanceResponse()));
	}

	public static WithdrawResponse WithdrawAll(SessionInfo session,
			String address) {
		return Withdraw(session, BigDecimal.ZERO, address);
	}

	public static WithdrawResponse Withdraw(SessionInfo session,
			BigDecimal amount, String address) {
		Validate(session, address);
		return Process(
				session,
				(WithdrawResponse) Request(
						GetFormDataWithdraw(session.sessionCookie, amount,
								address), new WithdrawResponse()));
	}

	public static ChangePasswordResponse ChangePassword(SessionInfo session,
			String oldPassword, String newPassword) {
		Validate(session);
		return (ChangePasswordResponse) Request(
				GetFormDataChangePassword(session.sessionCookie, oldPassword,
						newPassword), new ChangePasswordResponse());
	}

	public static GetServerSeedHashResponse GetServerSeedHash(
			SessionInfo session) {
		Validate(session);
		return (GetServerSeedHashResponse) Request(
				GetFormDataGetServerSeedHash(session.sessionCookie),
				new GetServerSeedHashResponse());
	}

	public static SetClientSeedResponse SetClientSeed(SessionInfo session,
			long seed) {
		Validate(session);
		return Process(
				session,
				(SetClientSeedResponse) Request(
						GetFormDataSetClientSeed(session.sessionCookie, seed),
						new SetClientSeedResponse()), seed);
	}

	public static UpdateEmailResponse UpdateEmail(SessionInfo session,
			String email) {
		Validate(session);
		if (email != null)
			email = email.trim();
		return Process(
				session,
				(UpdateEmailResponse) Request(
						GetFormDataUpdateEmail(session.sessionCookie, email),
						new UpdateEmailResponse()), email);
	}

	public static UpdateEmergencyAddressResponse UpdateEmergencyAddress(
			SessionInfo session, String emergencyAddress) {
		Validate(session);
		if (emergencyAddress != null)
			emergencyAddress = emergencyAddress.trim();
		return Process(
				session,
				(UpdateEmergencyAddressResponse) Request(
						GetFormDataUpdateEmergencyAddress(
								session.sessionCookie, emergencyAddress),
						new UpdateEmergencyAddressResponse()), emergencyAddress);
	}

	public static GetDepositAddressResponse GetDepositAddress(
			SessionInfo session) {
		Validate(session);
		return Process(
				session,
				(GetDepositAddressResponse) Request(
						GetFormDataGetDepositAddress(session.sessionCookie),
						new GetDepositAddressResponse()));
	}

	public static PlaceBetResponse PlaceBet(SessionInfo session,
			BigDecimal payIn, long guessLow, long guessHigh) {
		Validate(session, guessLow, guessHigh);
		if (payIn.signum() > 0)
			payIn = payIn.negate();
		return Process(
				session,
				(PlaceBetResponse) Request(
						GetFormDataPlaceBet(session.sessionCookie, payIn,
								guessLow, guessHigh), new PlaceBetResponse()),
				payIn, guessLow, guessHigh);
	}

	public static PlaceAutomatedBetsResponse PlaceAutomatedBets(
			SessionInfo session, AutomatedBetsSettings settings) {
		Validate(session, settings);
		return Process(
				session,
				(PlaceAutomatedBetsResponse) Request(
						GetFormDataPlaceAutomatedBets(session.sessionCookie,
								settings), new PlaceAutomatedBetsResponse()),
				settings);
	}

}
