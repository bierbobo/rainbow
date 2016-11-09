package com.bierbobo.rainbow.service;



import java.math.BigDecimal;
import java.util.List;

/**
 * 采销大表，sku维度数据
 * @author tangling
 * 修改历史： 修改人   修改时间   修改内容
 *            程相栋  2015-10-15  增加前台（主站）价格
 */
public class WareData {
	
	/**
	 * 直供商库存
	 */
	private Integer directVendorStock;
	/**
	 * 中间商库存
	 */
	private Integer middlemanStock;
	/**
	 * 全国可用库存
	 */
	private Integer nationAvailableStock;
	
	private BigDecimal salePrice;//商品售价

	/**
	 * 和配送中心相关的商品信息列表
	 */
	private List<WareSlaveData> wareSlaveList;

	/**
	 * 前台（主站）价格
	 * 如果接口没有查询到，则为-2，
	 * 如果商品下柜，则为-1
	 */
	private double frontPrice = -2;

	public List<WareSlaveData> getWareSlaveList() {
		return wareSlaveList;
	}

	public void setWareSlaveList(List<WareSlaveData> wareSlaveList) {
		this.wareSlaveList = wareSlaveList;
	}

	public Integer getDirectVendorStock() {
		return directVendorStock;
	}

	public void setDirectVendorStock(Integer directVendorStock) {
		this.directVendorStock = directVendorStock;
	}

	public Integer getMiddlemanStock() {
		return middlemanStock;
	}

	public void setMiddlemanStock(Integer middlemanStock) {
		this.middlemanStock = middlemanStock;
	}

	public Integer getNationAvailableStock() {
		return nationAvailableStock;
	}

	public void setNationAvailableStock(Integer nationAvailableStock) {
		this.nationAvailableStock = nationAvailableStock;
	}
	public BigDecimal getSalePrice() {
		return null;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public double getFrontPrice() {
		return frontPrice;
	}

	public void setFrontPrice(double frontPrice) {
		this.frontPrice = frontPrice;
	}
}
