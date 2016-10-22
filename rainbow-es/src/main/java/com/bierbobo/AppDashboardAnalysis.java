package com.bierbobo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lifubo on 2016/9/1.
 */

@Document(indexName = "app_dashboard_analysis", type = "sku_analysis")
public class AppDashboardAnalysis {

    @Id
    private Long id;

    private	Long	skuId	;
    private	String	skuName	;
    private	Integer	itemFirstCateCd	;
    private	String	itemFirstCateName	;
    private	Integer	itemSecondCateCd	;
    private	String	itemSecondCateName	;
    private	Integer	itemThirdCateCd	;
    private	String	itemThirdCateName	;
    private	String	nationVendorCode	;
    private	String	nationVendorName	;
    private	Integer	brandCode	;
    private	String	brandName	;
    private	Integer	saleStatus	;
    private	Date	lastSaleTime	;
    private BigDecimal marketPrice	;
    private	Long	clickCount	;
    private	Long	clickNoStock	;
    private	String	orgNationClicksBand	;
    private	String	orgNationSaleNumBand	;
    private	Integer	saleCount	;
    private	String	salerErpId	;
    private	String	salerName	;
    private	String	buyerErpId	;
    private	String	buyerName	;
    private	Integer	isDropship	;
    private	Integer	isNational	;
    private	Integer	isParallel	;
    private	Integer	isStopNoStock	;
    private	Integer	isShelfLife	;
    private	Integer	isXnzt	;
    private	Date	shelvesTm	;
    private	Date	offSaleTime	;
    private	Date	firstIntoWhTm	;
    private	String	qgp	;
    private	Long	mainSkuId	;
    private	BigDecimal	length	;
    private	BigDecimal	width	;
    private	BigDecimal	height	;
    private	BigDecimal	weight	;
    private	Integer	distributionId	;
    private	String	distributionName	;
    private	BigDecimal	warehousePrice	;
    private	Integer	allowReserveFlag	;
    private	Integer	ytdSales	;
    private	Integer	sales7	;
    private	Integer	sales14	;
    private	Long	sales15	;
    private	Integer	sales28	;
    private	Integer	sales60	;
    private	Integer	sales90	;
    private	Integer	curMonthSales	;
    private	Integer	delvStoreId	;
    private	Integer	delvStoreName	;
    private	Integer	companyId	;
    private	Integer	companyName	;
    private	Integer	totalPVYtd	;
    private	Integer	totalPV7	;
    private	Integer	totalPV14	;
    private	Integer	totalPV28	;
    private	Integer	totalPV60	;
    private	Integer	totalPV90	;
    private	BigDecimal	pvCnvsRateYtd	;
    private	Integer	noStockPVytd	;
    private	Integer	noStockPV7	;
    private	Integer	noStockPV14	;
    private	Integer	noStockPV28	;
    private	Integer	noStockPV60	;
    private	Integer	noStockPV90	;
    private	String	regionBand	;
    private	Integer	shelvesQttyYtd	;
    private	Integer	shelvesQtty7	;
    private	Integer	shelvesQtty14	;
    private	Integer	shelvesQtty28	;
    private	Integer	shelvesQtty60	;
    private	Integer	shelvesQtty90	;
    private	Integer	saleQttyYtd	;
    private	Integer	saleQtty7	;
    private	Integer	saleQtty14	;
    private	Integer	saleQtty28	;
    private	Long	saleQtty30	;
    private	Integer	saleQtty60	;
    private	Integer	saleQtty90	;

    private	Long	deptID1	;
    private	String	deptName1	;
    private	Long	deptID2	;
    private	String	deptName2	;
    private	Long	deptID3	;
    private	String	deptName3	;
    private	Long	deptID4	;
    private	String	deptName4	;
    private	String	orgNationSaleAmtBand	;
    private	Integer	isNewFlag	;
    private	Integer	isVcFlag	;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getItemFirstCateCd() {
        return itemFirstCateCd;
    }

    public void setItemFirstCateCd(Integer itemFirstCateCd) {
        this.itemFirstCateCd = itemFirstCateCd;
    }

    public String getItemFirstCateName() {
        return itemFirstCateName;
    }

    public void setItemFirstCateName(String itemFirstCateName) {
        this.itemFirstCateName = itemFirstCateName;
    }

    public Integer getItemSecondCateCd() {
        return itemSecondCateCd;
    }

    public void setItemSecondCateCd(Integer itemSecondCateCd) {
        this.itemSecondCateCd = itemSecondCateCd;
    }

    public String getItemSecondCateName() {
        return itemSecondCateName;
    }

    public void setItemSecondCateName(String itemSecondCateName) {
        this.itemSecondCateName = itemSecondCateName;
    }

    public Integer getItemThirdCateCd() {
        return itemThirdCateCd;
    }

    public void setItemThirdCateCd(Integer itemThirdCateCd) {
        this.itemThirdCateCd = itemThirdCateCd;
    }

    public String getItemThirdCateName() {
        return itemThirdCateName;
    }

    public void setItemThirdCateName(String itemThirdCateName) {
        this.itemThirdCateName = itemThirdCateName;
    }

    public String getNationVendorCode() {
        return nationVendorCode;
    }

    public void setNationVendorCode(String nationVendorCode) {
        this.nationVendorCode = nationVendorCode;
    }

    public String getNationVendorName() {
        return nationVendorName;
    }

    public void setNationVendorName(String nationVendorName) {
        this.nationVendorName = nationVendorName;
    }

    public Integer getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(Integer brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(Integer saleStatus) {
        this.saleStatus = saleStatus;
    }

    public Date getLastSaleTime() {
        return lastSaleTime;
    }

    public void setLastSaleTime(Date lastSaleTime) {
        this.lastSaleTime = lastSaleTime;
    }



    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Long getClickNoStock() {
        return clickNoStock;
    }

    public void setClickNoStock(Long clickNoStock) {
        this.clickNoStock = clickNoStock;
    }

    public String getOrgNationClicksBand() {
        return orgNationClicksBand;
    }

    public void setOrgNationClicksBand(String orgNationClicksBand) {
        this.orgNationClicksBand = orgNationClicksBand;
    }

    public String getOrgNationSaleNumBand() {
        return orgNationSaleNumBand;
    }

    public void setOrgNationSaleNumBand(String orgNationSaleNumBand) {
        this.orgNationSaleNumBand = orgNationSaleNumBand;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public String getSalerErpId() {
        return salerErpId;
    }

    public void setSalerErpId(String salerErpId) {
        this.salerErpId = salerErpId;
    }

    public String getSalerName() {
        return salerName;
    }

    public void setSalerName(String salerName) {
        this.salerName = salerName;
    }

    public String getBuyerErpId() {
        return buyerErpId;
    }

    public void setBuyerErpId(String buyerErpId) {
        this.buyerErpId = buyerErpId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public Integer getIsDropship() {
        return isDropship;
    }

    public void setIsDropship(Integer isDropship) {
        this.isDropship = isDropship;
    }

    public Integer getIsNational() {
        return isNational;
    }

    public void setIsNational(Integer isNational) {
        this.isNational = isNational;
    }

    public Integer getIsParallel() {
        return isParallel;
    }

    public void setIsParallel(Integer isParallel) {
        this.isParallel = isParallel;
    }

    public Integer getIsStopNoStock() {
        return isStopNoStock;
    }

    public void setIsStopNoStock(Integer isStopNoStock) {
        this.isStopNoStock = isStopNoStock;
    }

    public Integer getIsShelfLife() {
        return isShelfLife;
    }

    public void setIsShelfLife(Integer isShelfLife) {
        this.isShelfLife = isShelfLife;
    }

    public Integer getIsXnzt() {
        return isXnzt;
    }

    public void setIsXnzt(Integer isXnzt) {
        this.isXnzt = isXnzt;
    }

    public Date getShelvesTm() {
        return shelvesTm;
    }

    public void setShelvesTm(Date shelvesTm) {
        this.shelvesTm = shelvesTm;
    }

    public Date getOffSaleTime() {
        return offSaleTime;
    }

    public void setOffSaleTime(Date offSaleTime) {
        this.offSaleTime = offSaleTime;
    }

    public Date getFirstIntoWhTm() {
        return firstIntoWhTm;
    }

    public void setFirstIntoWhTm(Date firstIntoWhTm) {
        this.firstIntoWhTm = firstIntoWhTm;
    }

    public String getQgp() {
        return qgp;
    }

    public void setQgp(String qgp) {
        this.qgp = qgp;
    }

    public Long getMainSkuId() {
        return mainSkuId;
    }

    public void setMainSkuId(Long mainSkuId) {
        this.mainSkuId = mainSkuId;
    }



    public Integer getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(Integer distributionId) {
        this.distributionId = distributionId;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }



    public Integer getAllowReserveFlag() {
        return allowReserveFlag;
    }

    public void setAllowReserveFlag(Integer allowReserveFlag) {
        this.allowReserveFlag = allowReserveFlag;
    }

    public Integer getYtdSales() {
        return ytdSales;
    }

    public void setYtdSales(Integer ytdSales) {
        this.ytdSales = ytdSales;
    }

    public Integer getSales7() {
        return sales7;
    }

    public void setSales7(Integer sales7) {
        this.sales7 = sales7;
    }

    public Integer getSales14() {
        return sales14;
    }

    public void setSales14(Integer sales14) {
        this.sales14 = sales14;
    }

    public Long getSales15() {
        return sales15;
    }

    public void setSales15(Long sales15) {
        this.sales15 = sales15;
    }

    public Integer getSales28() {
        return sales28;
    }

    public void setSales28(Integer sales28) {
        this.sales28 = sales28;
    }

    public Integer getSales60() {
        return sales60;
    }

    public void setSales60(Integer sales60) {
        this.sales60 = sales60;
    }

    public Integer getSales90() {
        return sales90;
    }

    public void setSales90(Integer sales90) {
        this.sales90 = sales90;
    }

    public Integer getCurMonthSales() {
        return curMonthSales;
    }

    public void setCurMonthSales(Integer curMonthSales) {
        this.curMonthSales = curMonthSales;
    }

    public Integer getDelvStoreId() {
        return delvStoreId;
    }

    public void setDelvStoreId(Integer delvStoreId) {
        this.delvStoreId = delvStoreId;
    }

    public Integer getDelvStoreName() {
        return delvStoreName;
    }

    public void setDelvStoreName(Integer delvStoreName) {
        this.delvStoreName = delvStoreName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyName() {
        return companyName;
    }

    public void setCompanyName(Integer companyName) {
        this.companyName = companyName;
    }

    public Integer getTotalPVYtd() {
        return totalPVYtd;
    }

    public void setTotalPVYtd(Integer totalPVYtd) {
        this.totalPVYtd = totalPVYtd;
    }

    public Integer getTotalPV7() {
        return totalPV7;
    }

    public void setTotalPV7(Integer totalPV7) {
        this.totalPV7 = totalPV7;
    }

    public Integer getTotalPV14() {
        return totalPV14;
    }

    public void setTotalPV14(Integer totalPV14) {
        this.totalPV14 = totalPV14;
    }

    public Integer getTotalPV28() {
        return totalPV28;
    }

    public void setTotalPV28(Integer totalPV28) {
        this.totalPV28 = totalPV28;
    }

    public Integer getTotalPV60() {
        return totalPV60;
    }

    public void setTotalPV60(Integer totalPV60) {
        this.totalPV60 = totalPV60;
    }

    public Integer getTotalPV90() {
        return totalPV90;
    }

    public void setTotalPV90(Integer totalPV90) {
        this.totalPV90 = totalPV90;
    }



    public Integer getNoStockPVytd() {
        return noStockPVytd;
    }

    public void setNoStockPVytd(Integer noStockPVytd) {
        this.noStockPVytd = noStockPVytd;
    }

    public Integer getNoStockPV7() {
        return noStockPV7;
    }

    public void setNoStockPV7(Integer noStockPV7) {
        this.noStockPV7 = noStockPV7;
    }

    public Integer getNoStockPV14() {
        return noStockPV14;
    }

    public void setNoStockPV14(Integer noStockPV14) {
        this.noStockPV14 = noStockPV14;
    }

    public Integer getNoStockPV28() {
        return noStockPV28;
    }

    public void setNoStockPV28(Integer noStockPV28) {
        this.noStockPV28 = noStockPV28;
    }

    public Integer getNoStockPV60() {
        return noStockPV60;
    }

    public void setNoStockPV60(Integer noStockPV60) {
        this.noStockPV60 = noStockPV60;
    }

    public Integer getNoStockPV90() {
        return noStockPV90;
    }

    public void setNoStockPV90(Integer noStockPV90) {
        this.noStockPV90 = noStockPV90;
    }

    public String getRegionBand() {
        return regionBand;
    }

    public void setRegionBand(String regionBand) {
        this.regionBand = regionBand;
    }

    public Integer getShelvesQttyYtd() {
        return shelvesQttyYtd;
    }

    public void setShelvesQttyYtd(Integer shelvesQttyYtd) {
        this.shelvesQttyYtd = shelvesQttyYtd;
    }

    public Integer getShelvesQtty7() {
        return shelvesQtty7;
    }

    public void setShelvesQtty7(Integer shelvesQtty7) {
        this.shelvesQtty7 = shelvesQtty7;
    }

    public Integer getShelvesQtty14() {
        return shelvesQtty14;
    }

    public void setShelvesQtty14(Integer shelvesQtty14) {
        this.shelvesQtty14 = shelvesQtty14;
    }

    public Integer getShelvesQtty28() {
        return shelvesQtty28;
    }

    public void setShelvesQtty28(Integer shelvesQtty28) {
        this.shelvesQtty28 = shelvesQtty28;
    }

    public Integer getShelvesQtty60() {
        return shelvesQtty60;
    }

    public void setShelvesQtty60(Integer shelvesQtty60) {
        this.shelvesQtty60 = shelvesQtty60;
    }

    public Integer getShelvesQtty90() {
        return shelvesQtty90;
    }

    public void setShelvesQtty90(Integer shelvesQtty90) {
        this.shelvesQtty90 = shelvesQtty90;
    }

    public Integer getSaleQttyYtd() {
        return saleQttyYtd;
    }

    public void setSaleQttyYtd(Integer saleQttyYtd) {
        this.saleQttyYtd = saleQttyYtd;
    }

    public Integer getSaleQtty7() {
        return saleQtty7;
    }

    public void setSaleQtty7(Integer saleQtty7) {
        this.saleQtty7 = saleQtty7;
    }

    public Integer getSaleQtty14() {
        return saleQtty14;
    }

    public void setSaleQtty14(Integer saleQtty14) {
        this.saleQtty14 = saleQtty14;
    }

    public Integer getSaleQtty28() {
        return saleQtty28;
    }

    public void setSaleQtty28(Integer saleQtty28) {
        this.saleQtty28 = saleQtty28;
    }

    public Long getSaleQtty30() {
        return saleQtty30;
    }

    public void setSaleQtty30(Long saleQtty30) {
        this.saleQtty30 = saleQtty30;
    }

    public Integer getSaleQtty60() {
        return saleQtty60;
    }

    public void setSaleQtty60(Integer saleQtty60) {
        this.saleQtty60 = saleQtty60;
    }

    public Integer getSaleQtty90() {
        return saleQtty90;
    }

    public void setSaleQtty90(Integer saleQtty90) {
        this.saleQtty90 = saleQtty90;
    }

    public Long getDeptID1() {
        return deptID1;
    }

    public void setDeptID1(Long deptID1) {
        this.deptID1 = deptID1;
    }

    public String getDeptName1() {
        return deptName1;
    }

    public void setDeptName1(String deptName1) {
        this.deptName1 = deptName1;
    }

    public Long getDeptID2() {
        return deptID2;
    }

    public void setDeptID2(Long deptID2) {
        this.deptID2 = deptID2;
    }

    public String getDeptName2() {
        return deptName2;
    }

    public void setDeptName2(String deptName2) {
        this.deptName2 = deptName2;
    }

    public Long getDeptID3() {
        return deptID3;
    }

    public void setDeptID3(Long deptID3) {
        this.deptID3 = deptID3;
    }

    public String getDeptName3() {
        return deptName3;
    }

    public void setDeptName3(String deptName3) {
        this.deptName3 = deptName3;
    }

    public Long getDeptID4() {
        return deptID4;
    }

    public void setDeptID4(Long deptID4) {
        this.deptID4 = deptID4;
    }

    public String getDeptName4() {
        return deptName4;
    }

    public void setDeptName4(String deptName4) {
        this.deptName4 = deptName4;
    }

    public String getOrgNationSaleAmtBand() {
        return orgNationSaleAmtBand;
    }

    public void setOrgNationSaleAmtBand(String orgNationSaleAmtBand) {
        this.orgNationSaleAmtBand = orgNationSaleAmtBand;
    }

    public Integer getIsNewFlag() {
        return isNewFlag;
    }

    public void setIsNewFlag(Integer isNewFlag) {
        this.isNewFlag = isNewFlag;
    }

    public Integer getIsVcFlag() {
        return isVcFlag;
    }

    public void setIsVcFlag(Integer isVcFlag) {
        this.isVcFlag = isVcFlag;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getWarehousePrice() {
        return warehousePrice;
    }

    public void setWarehousePrice(BigDecimal warehousePrice) {
        this.warehousePrice = warehousePrice;
    }

    public BigDecimal getPvCnvsRateYtd() {
        return pvCnvsRateYtd;
    }

    public void setPvCnvsRateYtd(BigDecimal pvCnvsRateYtd) {
        this.pvCnvsRateYtd = pvCnvsRateYtd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
