/* ===========================================================
 * TradeManager : a application to trade strategies for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2011-2011, by Simon Allen and Contributors.
 *
 * Project Info:  org.trade
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 *
 * [Java is a trademark or registered trademark of Oracle, Inc.
 * in the United States and other countries.]
 *
 * (C) Copyright 2011-2011, by Simon Allen and Contributors.
 *
 * Original Author:  Simon Allen;
 * Contributor(s):   -;
 *
 * Changes
 * -------
 *
 */
package org.trade.persistent.dao;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.trade.core.dao.Aspect;
import org.trade.core.util.CoreUtils;
import org.trade.dictionary.valuetype.TradestrategyStatus;
import org.trade.strategy.data.CandleDataset;
import org.trade.strategy.data.CandleSeries;
import org.trade.strategy.data.StrategyData;

/**
 * Tradestrategy generated by hbm2java
 * 
 * @author Simon Allen
 * @version $Revision: 1.0 $
 */
@Entity
@Table(name = "tradestrategy")
public class Tradestrategy extends Aspect implements Serializable, Cloneable {

	private static final long serialVersionUID = -2181676329258092177L;
	private Integer chartDays;
	private Integer barSize;
	private Contract contract;
	private Tradingday tradingday;
	private Strategy strategy;
	private Portfolio portfolio;
	private String tier;
	private String status;
	private String side;
	private BigDecimal riskAmount;
	private Boolean trade = new Boolean(false);
	private List<Trade> trades = new ArrayList<Trade>(0);
	private StrategyData datasetContainer = null;
	private TradestrategyStatus tradestrategyStatus = new TradestrategyStatus();

	public Tradestrategy() {
	}

	/**
	 * Constructor for Tradestrategy.
	 * 
	 * @param contract
	 *            Contract
	 * @param tradingday
	 *            Tradingday
	 * @param strategy
	 *            Strategy
	 * @param account
	 *            Account
	 * @param riskAmount
	 *            BigDecimal
	 * @param side
	 *            String
	 * @param tier
	 *            String
	 * @param trade
	 *            Boolean
	 * @param chartDays
	 *            Integer
	 * @param barSize
	 *            Integer
	 */
	public Tradestrategy(Contract contract, Tradingday tradingday,
			Strategy strategy, Portfolio portfolio, BigDecimal riskAmount,
			String side, String tier, Boolean trade, Integer chartDays,
			Integer barSize) {
		this.setBarSize(barSize);
		this.chartDays = chartDays;
		this.contract = contract;
		this.strategy = strategy;
		this.tradingday = tradingday;
		this.portfolio = portfolio;
		this.riskAmount = riskAmount;
		this.side = side;
		this.tier = tier;
		this.trade = trade;
		super.setDirty(true);
	}

	/**
	 * Method getIdTradeStrategy.
	 * 
	 * @return Integer
	 */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idTradeStrategy", unique = true, nullable = false)
	public Integer getIdTradeStrategy() {
		return this.id;
	}

	/**
	 * Method setIdTradeStrategy.
	 * 
	 * @param idTradeStrategy
	 *            Integer
	 */
	public void setIdTradeStrategy(Integer idTradeStrategy) {
		this.id = idTradeStrategy;
	}

	/**
	 * Method getBarSize.
	 * 
	 * @return Integer
	 */
	@Column(name = "barSize")
	public Integer getBarSize() {
		return this.barSize;
	}

	/**
	 * Method setBarSize.
	 * 
	 * @param barSize
	 *            Integer
	 */
	public void setBarSize(Integer barSize) {
		this.barSize = barSize;
		if (null != barSize && barSize == 1) {
			int daySeconds = (int) ((this.getTradingday().getClose().getTime() - this
					.getTradingday().getOpen().getTime()) / 1000);
			this.barSize = daySeconds * barSize;
		}
	}

	/**
	 * Method getChartDays.
	 * 
	 * @return Integer
	 */
	@Column(name = "chartDays")
	public Integer getChartDays() {
		return this.chartDays;
	}

	/**
	 * Method setChartDays.
	 * 
	 * @param chartDays
	 *            Integer
	 */
	public void setChartDays(Integer chartDays) {
		this.chartDays = chartDays;
	}

	/**
	 * Method getStatus.
	 * 
	 * @return String
	 */
	@Column(name = "status", length = 20)
	public String getStatus() {
		return this.status;
	}

	/**
	 * Method setStatus.
	 * 
	 * @param status
	 *            String
	 */
	public void setStatus(String status) {
		this.status = status;
		if (null != this.status) {
			tradestrategyStatus.setValue(this.status);
		}
	}

	/**
	 * Method getTradestrategyStatus.
	 * 
	 * @return TradestrategyStatus
	 */
	@Transient
	public TradestrategyStatus getTradestrategyStatus() {
		return tradestrategyStatus;
	}

	/**
	 * Method getRiskAmount.
	 * 
	 * @return BigDecimal
	 */
	@Column(name = "riskAmount", nullable = false, precision = 10)
	public BigDecimal getRiskAmount() {
		return this.riskAmount;
	}

	/**
	 * Method setRiskAmount.
	 * 
	 * @param riskAmount
	 *            BigDecimal
	 */
	public void setRiskAmount(BigDecimal riskAmount) {
		this.riskAmount = riskAmount;
	}

	/**
	 * Method getSide.
	 * 
	 * @return String
	 */
	@Column(name = "side", length = 3)
	public String getSide() {
		return this.side;
	}

	/**
	 * Method setSide.
	 * 
	 * @param side
	 *            String
	 */
	public void setSide(String side) {
		this.side = side;
	}

	/**
	 * Method getTier.
	 * 
	 * @return String
	 */
	@Column(name = "tier", length = 1)
	public String getTier() {
		return this.tier;
	}

	/**
	 * Method setTier.
	 * 
	 * @param tier
	 *            String
	 */
	public void setTier(String tier) {
		this.tier = tier;
	}

	/**
	 * Method getContract.
	 * 
	 * @return Contract
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "idContract", insertable = true, updatable = true, nullable = false)
	public Contract getContract() {
		return this.contract;
	}

	/**
	 * Method setContract.
	 * 
	 * @param contract
	 *            Contract
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	/**
	 * Method getTradingday.
	 * 
	 * @return Tradingday
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH })
	@JoinColumn(name = "idTradingDay", insertable = true, updatable = true, nullable = false)
	public Tradingday getTradingday() {
		return this.tradingday;
	}

	/**
	 * Method setTradingday.
	 * 
	 * @param tradingday
	 *            Tradingday
	 */
	public void setTradingday(Tradingday tradingday) {
		this.tradingday = tradingday;
	}

	/**
	 * Method getStrategy.
	 * 
	 * @return Strategy
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idStrategy", insertable = true, updatable = true, nullable = false)
	public Strategy getStrategy() {
		return this.strategy;
	}

	/**
	 * Method setStrategy.
	 * 
	 * @param strategy
	 *            Strategy
	 */
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Method getPortfolio.
	 * 
	 * @return Portfolio
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idPortfolio", insertable = true, updatable = true, nullable = false)
	public Portfolio getPortfolio() {
		return this.portfolio;
	}

	/**
	 * Method setPortfolio.
	 * 
	 * @param portfolio
	 *            Portfolio
	 */
	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	/**
	 * Method getTrade.
	 * 
	 * @return Boolean
	 */
	@Column(name = "trade", length = 1)
	public Boolean getTrade() {

		return this.trade;
	}

	/**
	 * Method setTrade.
	 * 
	 * @param trade
	 *            Boolean
	 */
	public void setTrade(Boolean trade) {
		this.trade = trade;
	}

	/**
	 * Method getVersion.
	 * 
	 * @return Integer
	 */
	@Version
	@Column(name = "version")
	public Integer getVersion() {
		return this.version;
	}

	/**
	 * Method setVersion.
	 * 
	 * @param version
	 *            Integer
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Method getTrades.
	 * 
	 * @return List<Trade>
	 */
	@OneToMany(mappedBy = "tradestrategy", fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
	public List<Trade> getTrades() {
		return this.trades;
	}

	/**
	 * Method setTrades.
	 * 
	 * @param trades
	 *            List<Trade>
	 */
	public void setTrades(List<Trade> trades) {
		this.trades = trades;
	}

	/**
	 * Method addTrade.
	 * 
	 * @param trade
	 *            Trade
	 */
	public void addTrade(Trade trade) {
		int index = 0;
		for (Trade currTrade : this.trades) {
			if (currTrade.getIdTrade().equals(trade.getIdTrade())) {
				index = this.trades.indexOf(currTrade);
				break;
			}
		}
		if (index > 0)
			this.trades.remove(index);

		this.trades.add(trade);
	}

	/**
	 * Method getDatasetContainer.
	 * 
	 * @return StrategyData
	 */
	@Transient
	public StrategyData getDatasetContainer() {
		/*
		 * Add the dataset container if non exists. If tradestrategy is dirty we
		 * do not want to create a StrategyData object as Strategy/Contract have
		 * not yet been fully populated.
		 */
		if (null == this.datasetContainer) {
			CandleDataset candleDataset = new CandleDataset();
			CandleSeries candleSeries = new CandleSeries(getContract()
					.getSymbol(), getContract(), getBarSize(), this
					.getTradingday().getOpen(), this.getTradingday().getClose());
			candleDataset.addSeries(candleSeries);
			this.datasetContainer = new StrategyData(getStrategy(),
					candleDataset);
		}
		return this.datasetContainer;
	}

	/**
	 * Method setDatasetContainer.
	 * 
	 * @param datasetContainer
	 *            StrategyData
	 */
	public void setDatasetContainer(StrategyData datasetContainer) {
		if (null != this.datasetContainer) {
			if (this.datasetContainer.isRunning())
				this.datasetContainer.cancel();
			this.datasetContainer.clearBaseCandleSeries();
		}
		this.datasetContainer = datasetContainer;
	}

	/**
	 * Method setDirty.
	 * 
	 * @param dirty
	 *            boolean
	 */
	public void setDirty(boolean dirty) {
		super.setDirty(dirty);
		if (dirty)
			this.getTradingday().setDirty(dirty);
	}

	public static final Comparator<Tradestrategy> DATE_ORDER = new Comparator<Tradestrategy>() {
		public int compare(Tradestrategy o1, Tradestrategy o2) {
			m_ascending = true;
			int returnVal = 0;

			if (CoreUtils.nullSafeComparator(o1.getTradingday().getOpen(), o2
					.getTradingday().getOpen()) == 0) {
				if (CoreUtils.nullSafeComparator(o1.getSide(), o2.getSide()) == 0) {
					returnVal = CoreUtils.nullSafeComparator(o1.getTier(),
							o2.getTier());
				} else {
					returnVal = CoreUtils.nullSafeComparator(o1.getSide(),
							o2.getSide());
				}

			} else {
				returnVal = CoreUtils.nullSafeComparator(o1.getTradingday()
						.getOpen(), o2.getTradingday().getOpen());

			}

			if (m_ascending.equals(Boolean.FALSE)) {
				returnVal = returnVal * -1;
			}
			return returnVal;
		}
	};

	public static final Comparator<Tradestrategy> TRADINGDAY_CONTRACT = new Comparator<Tradestrategy>() {
		public int compare(Tradestrategy o1, Tradestrategy o2) {
			m_ascending = true;
			int returnVal = 0;

			if (CoreUtils.nullSafeComparator(o1.getTradingday().getOpen(), o2
					.getTradingday().getOpen()) == 0) {
				if (o1.getContract().equals(o2.getContract())) {
					if (CoreUtils.nullSafeComparator(o1.getBarSize(),
							o2.getBarSize()) == 0) {
						returnVal = CoreUtils.nullSafeComparator(
								o1.getChartDays(), o2.getChartDays());
					} else {
						returnVal = CoreUtils.nullSafeComparator(
								o1.getBarSize(), o2.getBarSize());
					}
				} else {
					returnVal = o1.getContract().getSymbol()
							.compareTo(o2.getContract().getSymbol());
				}

			} else {
				returnVal = CoreUtils.nullSafeComparator(o1.getTradingday()
						.getOpen(), o2.getTradingday().getOpen());

			}

			if (m_ascending.equals(Boolean.FALSE)) {
				returnVal = returnVal * -1;
			}
			return returnVal;
		}
	};

	/**
	 * Method toString.
	 * 
	 * @return String
	 */
	public String toString() {
		if (null != this.getContract()) {
			return this.getContract().getSymbol().toUpperCase();
		}
		return super.toString();
	}

	/**
	 * Method equals.
	 * 
	 * @param objectToCompare
	 *            Object
	 * @return boolean
	 */
	public boolean equals(Object objectToCompare) {

		if (super.equals(objectToCompare))
			return true;

		if (objectToCompare instanceof Tradestrategy) {
			Tradestrategy tradestrategy = (Tradestrategy) objectToCompare;
			if (this.getContract().equals(tradestrategy.getContract())) {
				if (this.getTradingday().getOpen()
						.compareTo(tradestrategy.getTradingday().getOpen()) == 0) {
					if (this.getStrategy().getName()
							.equals(tradestrategy.getStrategy().getName())) {
						if (this.getPortfolio().getName()
								.equals(tradestrategy.getPortfolio().getName())) {
							if (this.getBarSize().equals(
									tradestrategy.getBarSize())) {
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	/*

	 */
	/**
	 * Method getOpenTrade. Each trade position should have at least one open
	 * trade if no trades are open close this strategy manager as it has nothing
	 * to do.
	 * 
	 * @return Trade The open trade. Null mean no open position so close the
	 *         Strategy.
	 * 
	 */
	@Transient
	public Trade getOpenTrade() {

		for (Trade trade : this.getTrades()) {
			if (trade.getIsOpen()) {
				return trade;
			}
		}
		return null;
	}

	/**
	 * Method clone.
	 * 
	 * @return Object
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {

		Tradestrategy tradestrategy = (Tradestrategy) super.clone();
		Contract contract = (Contract) this.getContract().clone();
		tradestrategy.setContract(contract);
		Tradingday tradingday = (Tradingday) this.getTradingday().clone();
		tradestrategy.setTradingday(tradingday);
		Portfolio portfolio = (Portfolio) this.getPortfolio().clone();
		tradestrategy.setPortfolio(portfolio);
		Strategy strategy = (Strategy) this.getStrategy().clone();
		tradestrategy.setStrategy(strategy);
		List<Trade> trade = new ArrayList<Trade>(0);
		tradestrategy.setTrades(trade);
		return tradestrategy;
	}

}
