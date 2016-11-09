package com.bierbobo.rainbow.service;



import java.math.BigDecimal;

/**
 * 采销大表中配送中心维度相关数据
 * @author tangling
 */
public class WareSlaveData {
	/**
	 * 可用库存
	 */
	private Integer availableStock;
	/**
	 * 现货库存
	 */
	private Integer inStockNum;
	/**
	 * 采购未到货
	 */
	private Integer notArriveStock;
	/**
	 * 采购已分箱
	 */
	private Integer fenxiangStock;
	/**
	 * 现货库存成本=现货*仓报价
	 */
	private Double inStockNumCost;
	/**
	 * 可订购数量
	 */
	private Integer canReserveNum;
	/**
	 * 可售天数
	 * 图书：可用库存/28天销量*28
	 * 非图书：可预订库存/28天销量*28
	 */
	private Double canSaleDays;
	/**
	 * vcs采购价
	 */
	private BigDecimal vcsPrice;
	/**
	 * 定价
	 */
	private BigDecimal fixPrice;
	

	
	/**
	 * 7日28日销量占比
	 */
	private String percentage728;
	
	private String isStockNO;     //是否断货
	
	/**
	 * 内配入
	 * @return
	 */
	private Integer transferInNum;
	
	/**
	 * 内配在途
	 * @return
	 */
	private Integer ztNum;
	
	/**
	 * 内配出
	 * @return
	 */
	private Integer transferOutNum;
	
	private String errorMsgRemind;


	private  Integer numOrderBooking;//表示订单预占数
	private  Integer numAppBooking;//表示订单锁定
	private  Integer numNosale;//不可销售库存 OrderAppNoSale


	private Double grossProfit;//毛利
	private String grossProfitRate;//毛利率

	public String getGrossProfitRate() {
		return grossProfitRate;
	}

	public void setGrossProfitRate(String grossProfitRate) {
		this.grossProfitRate = grossProfitRate;
	}

	public Double getGrossProfit() {
		return grossProfit;
	}

	public void setGrossProfit(Double grossProfit) {
		this.grossProfit = grossProfit;
	}

	public BigDecimal getFixPrice() {
		return fixPrice;
	}

	public void setFixPrice(BigDecimal fixPrice) {
		this.fixPrice = fixPrice;
	}

	public String getPercentage728() {
		return percentage728;
	}

	public Integer getNumNosale() {
		return numNosale;
	}

	public void setNumNosale(Integer numNosale) {
		this.numNosale = numNosale;
	}

	public Integer getNumAppBooking() {
		return numAppBooking;
	}

	public void setNumAppBooking(Integer numAppBooking) {
		this.numAppBooking = numAppBooking;
	}

	public Integer getNumOrderBooking() {
		return numOrderBooking;
	}

	public void setNumOrderBooking(Integer numOrderBooking) {
		this.numOrderBooking = numOrderBooking;
	}



	public Integer getAvailableStock() {
		return availableStock;
	}
	public void setAvailableStock(Integer availableStock) {
		this.availableStock = availableStock;
	}
	public Integer getInStockNum() {
		return inStockNum;
	}
	public void setInStockNum(Integer inStockNum) {
		this.inStockNum = inStockNum;
	}
	public void setInStockNumCost(Double inStockNumCost) {
		this.inStockNumCost = inStockNumCost;
	}
	public Integer getCanReserveNum() {
		return canReserveNum;
	}
	public void setCanReserveNum(Integer canReserveNum) {
		this.canReserveNum = canReserveNum;
	}
	public Double getCanSaleDays() {
		return null;
	}
	public void setCanSaleDays(Double canSaleDays) {
		this.canSaleDays = canSaleDays;
	}
	public BigDecimal getVcsPrice() {
		return null;
	}
	public void setVcsPrice(BigDecimal vcsPrice) {
		this.vcsPrice = vcsPrice;
	}

	public Integer getNotArriveStock() {
		return notArriveStock;
	}
	public void setNotArriveStock(Integer notArriveStock) {
		this.notArriveStock = notArriveStock;
	}
	public Integer getFenxiangStock() {
		return fenxiangStock;
	}
	public void setFenxiangStock(Integer fenxiangStock) {
		this.fenxiangStock = fenxiangStock;
	}
	public void setPercentage728(String percentage728) {
		this.percentage728 = percentage728;
	}
	public String getIsStockNO() {
		return isStockNO;
	}
	public void setIsStockNO(String isStockNO) {
		this.isStockNO = isStockNO;
	}
	public Integer getTransferInNum() {
		return transferInNum;
	}
	public void setTransferInNum(Integer transferInNum) {
		this.transferInNum = transferInNum;
	}
	public Integer getZtNum() {
		return ztNum;
	}
	public void setZtNum(Integer ztNum) {
		this.ztNum = ztNum;
	}
	public Integer getTransferOutNum() {
		return transferOutNum;
	}
	public void setTransferOutNum(Integer transferOutNum) {
		this.transferOutNum = transferOutNum;
	}
	public String getErrorMsgRemind() {
		return errorMsgRemind;
	}
	public void setErrorMsgRemind(String errorMsgRemind) {
		this.errorMsgRemind = errorMsgRemind;
	}
	
}
