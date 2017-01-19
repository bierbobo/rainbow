package com.bierbobo.rainbow.util.codeGeneral.beanClass;


import java.io.Serializable;

public class StockNumJdVO implements Serializable {
    private Long skuId;
    private Integer dcId;
    private Integer sid;
    private Integer numOrderBooking;
    private Integer numAppBooking;
    private Integer numStock;
    private Integer numOrderTransfer;
    private Integer numZtStock;
    private Integer numTransferPlanIn;
    private Integer numPurchasePlan;
    private Integer numTransferPlanOut;
    private Integer reserve;
    private Integer purchase;
    private Integer numNosale;
    private Integer numStockOffNoSale;

    public StockNumJdVO() {
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getDcId() {
        return this.dcId;
    }

    public void setDcId(Integer dcId) {
        this.dcId = dcId;
    }

    public Integer getSid() {
        return this.sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getNumOrderBooking() {
        return this.numOrderBooking;
    }

    public void setNumOrderBooking(Integer numOrderBooking) {
        this.numOrderBooking = numOrderBooking;
    }

    public Integer getNumAppBooking() {
        return this.numAppBooking;
    }

    public void setNumAppBooking(Integer numAppBooking) {
        this.numAppBooking = numAppBooking;
    }

    public Integer getNumStock() {
        return this.numStock;
    }

    public void setNumStock(Integer numStock) {
        this.numStock = numStock;
    }

    public Integer getNumOrderTransfer() {
        return this.numOrderTransfer;
    }

    public void setNumOrderTransfer(Integer numOrderTransfer) {
        this.numOrderTransfer = numOrderTransfer;
    }

    public Integer getNumZtStock() {
        return this.numZtStock;
    }

    public void setNumZtStock(Integer numZtStock) {
        this.numZtStock = numZtStock;
    }

    public Integer getNumTransferPlanIn() {
        return this.numTransferPlanIn;
    }

    public void setNumTransferPlanIn(Integer numTransferPlanIn) {
        this.numTransferPlanIn = numTransferPlanIn;
    }

    public Integer getNumPurchasePlan() {
        return this.numPurchasePlan;
    }

    public void setNumPurchasePlan(Integer numPurchasePlan) {
        this.numPurchasePlan = numPurchasePlan;
    }

    public Integer getNumTransferPlanOut() {
        return this.numTransferPlanOut;
    }

    public void setNumTransferPlanOut(Integer numTransferPlanOut) {
        this.numTransferPlanOut = numTransferPlanOut;
    }

    public Integer getReserve() {
        return this.reserve;
    }

    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    public Integer getPurchase() {
        return this.purchase;
    }

    public void setPurchase(Integer purchase) {
        this.purchase = purchase;
    }

    public Integer getNumNosale() {
        return this.numNosale;
    }

    public void setNumNosale(Integer numNosale) {
        this.numNosale = numNosale;
    }

    public Integer getNumStockOffNoSale() {
        return this.numStockOffNoSale;
    }

    public void setNumStockOffNoSale(Integer numStockOffNoSale) {
        this.numStockOffNoSale = numStockOffNoSale;
    }
}