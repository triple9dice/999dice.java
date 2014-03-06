package dice.client.web;

import java.math.BigDecimal;
import java.math.MathContext;

public class AutomatedBetsSettings {
	private BigDecimal basePayIn, maxAllowedPayIn, increaseOnWinPercent, increaseOnLosePercent,
		stopMaxBalance, stopMinBalance, startingPayIn;
	private long guessLow, guessHigh;
	private int maxBets;
	private boolean resetOnWin, resetOnLose, resetOnLoseMaxBet, stopOnLoseMaxBet;
	
	private static BigDecimal forceNotNull(BigDecimal num)
	{
		return num==null ? BigDecimal.ZERO : num;
	}
	private static BigDecimal forceNegative(BigDecimal num)
	{
		num = forceNotNull(num);
		return num.signum() > 0 ? num.negate() : num;
	}
	private static BigDecimal round6(BigDecimal num)
	{
		return forceNotNull(num).movePointLeft(6).round(MathContext.DECIMAL128).movePointRight(6);
	}
	
	public BigDecimal getBasePayIn() {
		return forceNegative(basePayIn);
	}
	public void setBasePayIn(BigDecimal basePayIn) {
		this.basePayIn = basePayIn;
	}
	public BigDecimal getMaxAllowedPayIn() {
		return forceNegative(maxAllowedPayIn);
	}
	public void setMaxAllowedPayIn(BigDecimal maxAllowedPayIn) {
		this.maxAllowedPayIn = maxAllowedPayIn;
	}
	public BigDecimal getIncreaseOnWinPercent() {
		return round6(increaseOnWinPercent);
	}
	public void setIncreaseOnWinPercent(BigDecimal increaseOnWinPercent) {
		this.increaseOnWinPercent = increaseOnWinPercent;
	}
	public BigDecimal getIncreaseOnLosePercent() {
		return round6(increaseOnLosePercent);
	}
	public void setIncreaseOnLosePercent(BigDecimal increaseOnLosePercent) {
		this.increaseOnLosePercent = increaseOnLosePercent;
	}
	public BigDecimal getStopMaxBalance() {
		return forceNotNull(stopMaxBalance);
	}
	public void setStopMaxBalance(BigDecimal stopMaxBalance) {
		this.stopMaxBalance = stopMaxBalance;
	}
	public BigDecimal getStopMinBalance() {
		return forceNotNull(stopMinBalance);
	}
	public void setStopMinBalance(BigDecimal stopMinBalance) {
		this.stopMinBalance = stopMinBalance;
	}
	public BigDecimal getStartingPayIn() {
		BigDecimal num = forceNegative(startingPayIn);
		return num.signum()==0 ? getBasePayIn() : num;
	}
	public void setStartingPayIn(BigDecimal startingPayIn) {
		this.startingPayIn = startingPayIn;
	}
	public long getGuessLow() {
		return guessLow;
	}
	public void setGuessLow(long guessLow) {
		this.guessLow = guessLow;
	}
	public long getGuessHigh() {
		return guessHigh;
	}
	public void setGuessHigh(long guessHigh) {
		this.guessHigh = guessHigh;
	}
	public int getMaxBets() {
		return maxBets;
	}
	public void setMaxBets(int maxBets) {
		this.maxBets = maxBets;
	}
	public boolean isResetOnWin() {
		return resetOnWin;
	}
	public void setResetOnWin(boolean resetOnWin) {
		this.resetOnWin = resetOnWin;
	}
	public boolean isResetOnLose() {
		return resetOnLose;
	}
	public void setResetOnLose(boolean resetOnLose) {
		this.resetOnLose = resetOnLose;
	}
	public boolean isResetOnLoseMaxBet() {
		return resetOnLoseMaxBet;
	}
	public void setResetOnLoseMaxBet(boolean resetOnLoseMaxBet) {
		this.resetOnLoseMaxBet = resetOnLoseMaxBet;
	}
	public boolean isStopOnLoseMaxBet() {
		return stopOnLoseMaxBet;
	}
	public void setStopOnLoseMaxBet(boolean stopOnLoseMaxBet) {
		this.stopOnLoseMaxBet = stopOnLoseMaxBet;
	}	
}
