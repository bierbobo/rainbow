package com.bierbobo.rainbow.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "app_dashboard_base_sku_info", type = "sku_info")
public class ReportSkuInfo {

    @Id
    private String id;//商品编号

    private String biDataDate;//数据灌入时间

    //排序字段
    private String nationwideSales7;//全国7日有效销量 订单-收货地口径  全国七日销量  biip_sale表
    private String nationTotalPV;//全国昨日总PV
    private String nationNoStockPV;//全国昨日无货PV
    private String nationNumStock;//全国现货库存
    private String nationNumStockAmount;//全国现货库存成本
    private String nationUnsalableStockAmount;//全国滞销库存金额


    //商品信息
    private String skuId;// 商品ID
    private String mainSkuId;//主商品sku
    private String skuName;//商品名称

    private String itemFirstCateCd;//一级分类ID
    private String itemFirstCateName;//一级分类名称
    private String itemSecondCateCd;//二级分类ID
    private String itemSecondCateName;//二级分类名称
    private String itemThirdCateCd;//三级分类ID
    private String itemThirdCateName;//三级分类名称
    private String deptID1;// 1级部门ID
    private String deptName1;// 1级部门名称
    private String deptID2;// 2级部门ID
    private String deptName2;// 2级部门名称
    private String deptID3;// 3级部门ID
    private String deptName3;// 3级部门名称
    private String deptID4;// 4级部门ID
    private String deptName4;// 4级部门名称
    private String workPostCd;// 末级部门ID

    private String nationVendorCode;// 全国供应商简码 fdm_vcs_ware_base_price_mysql_chain
    private String nationVendorName;//全国供应商名称
    private String brandCode;//品牌编码
    private String brandName;//品牌名称

    private String itemType; //商品类型
    private String saleStatus;//上下柜状态
    private String offSaleReason;//下柜原因

    private String salerErpId;//销售员ERP
    private String salerName;//销售员名称
    private String buyerErpId;//采购员erp
    private String buyerName;//采购员名称

    private String reserveState;//预售状态
    private String bookingStatus;//预订状态
    private String length;//商品长度
    private String width;//商品宽度
    private String height;//商品高度
    private String weight;//商品体积
    private String wt;//商品重量


    //特殊属性
    private String isDropship;//是否厂商直送
    private String isNational;//是否全国发货
    private String isStopNoStock;//是否售完即止
    private String isXnzt;//是否虚拟组套
    private String isShelfLife;//是否保质期商品
    private String isParallel;//是否平行库存
    private String isNewFlag;//是否新品
    private String isVcFlag;//是否虚拟品类
    private String isAllowReserveFlag;//是否预售


    //时间相关
    private String shelvesTm;//上架时间
    private String lastSaleTime;//最近上柜时间
    private String offSaleTime;//最近下柜时间
    private String firstIntoWhTm;//首次入库时间
    private String lastIntoWhTm;//末次入库时间
    private String qgp;//保质期

    //价格相关
    private String jdPrice;//
    private String mktPrice;//市场价
    private String nationWhqtPrice ;//全国最大仓报价

    //Band
    private String orgNationClicksBand;//全国一级品类7日点击量BAND
    private String orgNationClicksBand37;//全国三级品类7日点击量BAND
    private String orgNationSaleNumBand;//全国一级品类7日销量BAND
    private String orgNationSaleNumBand37;//全国三级品类7日销量BAND
    //private String orgNationSaleAmtBand;//全国一级品类7日销售额BAND


    //财务库存
    private String financingInventory;//财务库存(全国月至今平均库存金额,全国月至今累计仓报价成本,全国财务存货周转,全国月至今累计退货仓报价成本)
    private String avgMonthStoreMoney;//全国月至今日均库存金额
    private String costNorebateAmountS;//全国月至今累计仓报价成本
    private String storeReturnFinance;//全国财务存货周转
    private String returnCostS;//全国月至今累计退货仓报价成本

    //SKU标示
    private String isNationNosales30;//是否全国30日不动销
    private String isNationNosales60;//是否全国60日不动销
    private String isNationNosales90;//是否全国90日不动销
    private String isUnsalablenventory;//是否全国件数滞销

    //其他------------------------------
    private String clickNoStock;//无货点击量 非图书为0
    private String clickCount;//点击量
    private String saleCount;//7日全国销量(从band表中取的，用于band排名的)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBiDataDate() {
        return biDataDate;
    }

    public void setBiDataDate(String biDataDate) {
        this.biDataDate = biDataDate;
    }

    public String getNationwideSales7() {
        return nationwideSales7;
    }

    public void setNationwideSales7(String nationwideSales7) {
        this.nationwideSales7 = nationwideSales7;
    }

    public String getNationTotalPV() {
        return nationTotalPV;
    }

    public void setNationTotalPV(String nationTotalPV) {
        this.nationTotalPV = nationTotalPV;
    }

    public String getNationNoStockPV() {
        return nationNoStockPV;
    }

    public void setNationNoStockPV(String nationNoStockPV) {
        this.nationNoStockPV = nationNoStockPV;
    }

    public String getNationNumStock() {
        return nationNumStock;
    }

    public void setNationNumStock(String nationNumStock) {
        this.nationNumStock = nationNumStock;
    }

    public String getNationNumStockAmount() {
        return nationNumStockAmount;
    }

    public void setNationNumStockAmount(String nationNumStockAmount) {
        this.nationNumStockAmount = nationNumStockAmount;
    }

    public String getNationUnsalableStockAmount() {
        return nationUnsalableStockAmount;
    }

    public void setNationUnsalableStockAmount(String nationUnsalableStockAmount) {
        this.nationUnsalableStockAmount = nationUnsalableStockAmount;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getMainSkuId() {
        return mainSkuId;
    }

    public void setMainSkuId(String mainSkuId) {
        this.mainSkuId = mainSkuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getItemFirstCateCd() {
        return itemFirstCateCd;
    }

    public void setItemFirstCateCd(String itemFirstCateCd) {
        this.itemFirstCateCd = itemFirstCateCd;
    }

    public String getItemFirstCateName() {
        return itemFirstCateName;
    }

    public void setItemFirstCateName(String itemFirstCateName) {
        this.itemFirstCateName = itemFirstCateName;
    }

    public String getItemSecondCateCd() {
        return itemSecondCateCd;
    }

    public void setItemSecondCateCd(String itemSecondCateCd) {
        this.itemSecondCateCd = itemSecondCateCd;
    }

    public String getItemSecondCateName() {
        return itemSecondCateName;
    }

    public void setItemSecondCateName(String itemSecondCateName) {
        this.itemSecondCateName = itemSecondCateName;
    }

    public String getItemThirdCateCd() {
        return itemThirdCateCd;
    }

    public void setItemThirdCateCd(String itemThirdCateCd) {
        this.itemThirdCateCd = itemThirdCateCd;
    }

    public String getItemThirdCateName() {
        return itemThirdCateName;
    }

    public void setItemThirdCateName(String itemThirdCateName) {
        this.itemThirdCateName = itemThirdCateName;
    }

    public String getDeptID1() {
        return deptID1;
    }

    public void setDeptID1(String deptID1) {
        this.deptID1 = deptID1;
    }

    public String getDeptName1() {
        return deptName1;
    }

    public void setDeptName1(String deptName1) {
        this.deptName1 = deptName1;
    }

    public String getDeptID2() {
        return deptID2;
    }

    public void setDeptID2(String deptID2) {
        this.deptID2 = deptID2;
    }

    public String getDeptName2() {
        return deptName2;
    }

    public void setDeptName2(String deptName2) {
        this.deptName2 = deptName2;
    }

    public String getDeptID3() {
        return deptID3;
    }

    public void setDeptID3(String deptID3) {
        this.deptID3 = deptID3;
    }

    public String getDeptName3() {
        return deptName3;
    }

    public void setDeptName3(String deptName3) {
        this.deptName3 = deptName3;
    }

    public String getDeptID4() {
        return deptID4;
    }

    public void setDeptID4(String deptID4) {
        this.deptID4 = deptID4;
    }

    public String getDeptName4() {
        return deptName4;
    }

    public void setDeptName4(String deptName4) {
        this.deptName4 = deptName4;
    }

    public String getWorkPostCd() {
        return workPostCd;
    }

    public void setWorkPostCd(String workPostCd) {
        this.workPostCd = workPostCd;
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

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getOffSaleReason() {
        return offSaleReason;
    }

    public void setOffSaleReason(String offSaleReason) {
        this.offSaleReason = offSaleReason;
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

    public String getReserveState() {
        return reserveState;
    }

    public void setReserveState(String reserveState) {
        this.reserveState = reserveState;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWt() {
        return wt;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getIsDropship() {
        return isDropship;
    }

    public void setIsDropship(String isDropship) {
        this.isDropship = isDropship;
    }

    public String getIsNational() {
        return isNational;
    }

    public void setIsNational(String isNational) {
        this.isNational = isNational;
    }

    public String getIsStopNoStock() {
        return isStopNoStock;
    }

    public void setIsStopNoStock(String isStopNoStock) {
        this.isStopNoStock = isStopNoStock;
    }

    public String getIsXnzt() {
        return isXnzt;
    }

    public void setIsXnzt(String isXnzt) {
        this.isXnzt = isXnzt;
    }

    public String getIsShelfLife() {
        return isShelfLife;
    }

    public void setIsShelfLife(String isShelfLife) {
        this.isShelfLife = isShelfLife;
    }

    public String getIsParallel() {
        return isParallel;
    }

    public void setIsParallel(String isParallel) {
        this.isParallel = isParallel;
    }

    public String getIsNewFlag() {
        return isNewFlag;
    }

    public void setIsNewFlag(String isNewFlag) {
        this.isNewFlag = isNewFlag;
    }

    public String getIsVcFlag() {
        return isVcFlag;
    }

    public void setIsVcFlag(String isVcFlag) {
        this.isVcFlag = isVcFlag;
    }

    public String getIsAllowReserveFlag() {
        return isAllowReserveFlag;
    }

    public void setIsAllowReserveFlag(String isAllowReserveFlag) {
        this.isAllowReserveFlag = isAllowReserveFlag;
    }

    public String getShelvesTm() {
        return shelvesTm;
    }

    public void setShelvesTm(String shelvesTm) {
        this.shelvesTm = shelvesTm;
    }

    public String getLastSaleTime() {
        return lastSaleTime;
    }

    public void setLastSaleTime(String lastSaleTime) {
        this.lastSaleTime = lastSaleTime;
    }

    public String getOffSaleTime() {
        return offSaleTime;
    }

    public void setOffSaleTime(String offSaleTime) {
        this.offSaleTime = offSaleTime;
    }

    public String getFirstIntoWhTm() {
        return firstIntoWhTm;
    }

    public void setFirstIntoWhTm(String firstIntoWhTm) {
        this.firstIntoWhTm = firstIntoWhTm;
    }

    public String getLastIntoWhTm() {
        return lastIntoWhTm;
    }

    public void setLastIntoWhTm(String lastIntoWhTm) {
        this.lastIntoWhTm = lastIntoWhTm;
    }

    public String getQgp() {
        return qgp;
    }

    public void setQgp(String qgp) {
        this.qgp = qgp;
    }

    public String getJdPrice() {
        return jdPrice;
    }

    public void setJdPrice(String jdPrice) {
        this.jdPrice = jdPrice;
    }

    public String getMktPrice() {
        return mktPrice;
    }

    public void setMktPrice(String mktPrice) {
        this.mktPrice = mktPrice;
    }

    public String getNationWhqtPrice() {
        return nationWhqtPrice;
    }

    public void setNationWhqtPrice(String nationWhqtPrice) {
        this.nationWhqtPrice = nationWhqtPrice;
    }

    public String getOrgNationClicksBand() {
        return orgNationClicksBand;
    }

    public void setOrgNationClicksBand(String orgNationClicksBand) {
        this.orgNationClicksBand = orgNationClicksBand;
    }

    public String getOrgNationClicksBand37() {
        return orgNationClicksBand37;
    }

    public void setOrgNationClicksBand37(String orgNationClicksBand37) {
        this.orgNationClicksBand37 = orgNationClicksBand37;
    }

    public String getOrgNationSaleNumBand() {
        return orgNationSaleNumBand;
    }

    public void setOrgNationSaleNumBand(String orgNationSaleNumBand) {
        this.orgNationSaleNumBand = orgNationSaleNumBand;
    }

    public String getOrgNationSaleNumBand37() {
        return orgNationSaleNumBand37;
    }

    public void setOrgNationSaleNumBand37(String orgNationSaleNumBand37) {
        this.orgNationSaleNumBand37 = orgNationSaleNumBand37;
    }

    public String getFinancingInventory() {
        return financingInventory;
    }

    public void setFinancingInventory(String financingInventory) {
        this.financingInventory = financingInventory;
    }

    public String getAvgMonthStoreMoney() {
        return avgMonthStoreMoney;
    }

    public void setAvgMonthStoreMoney(String avgMonthStoreMoney) {
        this.avgMonthStoreMoney = avgMonthStoreMoney;
    }

    public String getCostNorebateAmountS() {
        return costNorebateAmountS;
    }

    public void setCostNorebateAmountS(String costNorebateAmountS) {
        this.costNorebateAmountS = costNorebateAmountS;
    }

    public String getStoreReturnFinance() {
        return storeReturnFinance;
    }

    public void setStoreReturnFinance(String storeReturnFinance) {
        this.storeReturnFinance = storeReturnFinance;
    }

    public String getReturnCostS() {
        return returnCostS;
    }

    public void setReturnCostS(String returnCostS) {
        this.returnCostS = returnCostS;
    }

    public String getIsNationNosales30() {
        return isNationNosales30;
    }

    public void setIsNationNosales30(String isNationNosales30) {
        this.isNationNosales30 = isNationNosales30;
    }

    public String getIsNationNosales60() {
        return isNationNosales60;
    }

    public void setIsNationNosales60(String isNationNosales60) {
        this.isNationNosales60 = isNationNosales60;
    }

    public String getIsNationNosales90() {
        return isNationNosales90;
    }

    public void setIsNationNosales90(String isNationNosales90) {
        this.isNationNosales90 = isNationNosales90;
    }

    public String getIsUnsalablenventory() {
        return isUnsalablenventory;
    }

    public void setIsUnsalablenventory(String isUnsalablenventory) {
        this.isUnsalablenventory = isUnsalablenventory;
    }

    public String getClickNoStock() {
        return clickNoStock;
    }

    public void setClickNoStock(String clickNoStock) {
        this.clickNoStock = clickNoStock;
    }

    public String getClickCount() {
        return clickCount;
    }

    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    public String getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(String saleCount) {
        this.saleCount = saleCount;
    }

    @Override
    public String toString() {
        return "ReportSkuInfo{" +
                "id='" + id + '\'' +
                ", biDataDate='" + biDataDate + '\'' +
                ", nationwideSales7='" + nationwideSales7 + '\'' +
                ", nationTotalPV='" + nationTotalPV + '\'' +
                ", nationNoStockPV='" + nationNoStockPV + '\'' +
                ", nationNumStock='" + nationNumStock + '\'' +
                ", nationNumStockAmount='" + nationNumStockAmount + '\'' +
                ", nationUnsalableStockAmount='" + nationUnsalableStockAmount + '\'' +
                ", skuId='" + skuId + '\'' +
                ", mainSkuId='" + mainSkuId + '\'' +
                ", skuName='" + skuName + '\'' +
                ", itemFirstCateCd='" + itemFirstCateCd + '\'' +
                ", itemFirstCateName='" + itemFirstCateName + '\'' +
                ", itemSecondCateCd='" + itemSecondCateCd + '\'' +
                ", itemSecondCateName='" + itemSecondCateName + '\'' +
                ", itemThirdCateCd='" + itemThirdCateCd + '\'' +
                ", itemThirdCateName='" + itemThirdCateName + '\'' +
                ", deptID1='" + deptID1 + '\'' +
                ", deptName1='" + deptName1 + '\'' +
                ", deptID2='" + deptID2 + '\'' +
                ", deptName2='" + deptName2 + '\'' +
                ", deptID3='" + deptID3 + '\'' +
                ", deptName3='" + deptName3 + '\'' +
                ", deptID4='" + deptID4 + '\'' +
                ", deptName4='" + deptName4 + '\'' +
                ", workPostCd='" + workPostCd + '\'' +
                ", nationVendorCode='" + nationVendorCode + '\'' +
                ", nationVendorName='" + nationVendorName + '\'' +
                ", brandCode='" + brandCode + '\'' +
                ", brandName='" + brandName + '\'' +
                ", itemType='" + itemType + '\'' +
                ", saleStatus='" + saleStatus + '\'' +
                ", offSaleReason='" + offSaleReason + '\'' +
                ", salerErpId='" + salerErpId + '\'' +
                ", salerName='" + salerName + '\'' +
                ", buyerErpId='" + buyerErpId + '\'' +
                ", buyerName='" + buyerName + '\'' +
                ", reserveState='" + reserveState + '\'' +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", length='" + length + '\'' +
                ", width='" + width + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", wt='" + wt + '\'' +
                ", isDropship='" + isDropship + '\'' +
                ", isNational='" + isNational + '\'' +
                ", isStopNoStock='" + isStopNoStock + '\'' +
                ", isXnzt='" + isXnzt + '\'' +
                ", isShelfLife='" + isShelfLife + '\'' +
                ", isParallel='" + isParallel + '\'' +
                ", isNewFlag='" + isNewFlag + '\'' +
                ", isVcFlag='" + isVcFlag + '\'' +
                ", isAllowReserveFlag='" + isAllowReserveFlag + '\'' +
                ", shelvesTm='" + shelvesTm + '\'' +
                ", lastSaleTime='" + lastSaleTime + '\'' +
                ", offSaleTime='" + offSaleTime + '\'' +
                ", firstIntoWhTm='" + firstIntoWhTm + '\'' +
                ", lastIntoWhTm='" + lastIntoWhTm + '\'' +
                ", qgp='" + qgp + '\'' +
                ", jdPrice='" + jdPrice + '\'' +
                ", mktPrice='" + mktPrice + '\'' +
                ", nationWhqtPrice='" + nationWhqtPrice + '\'' +
                ", orgNationClicksBand='" + orgNationClicksBand + '\'' +
                ", orgNationClicksBand37='" + orgNationClicksBand37 + '\'' +
                ", orgNationSaleNumBand='" + orgNationSaleNumBand + '\'' +
                ", orgNationSaleNumBand37='" + orgNationSaleNumBand37 + '\'' +
                ", financingInventory='" + financingInventory + '\'' +
                ", avgMonthStoreMoney='" + avgMonthStoreMoney + '\'' +
                ", costNorebateAmountS='" + costNorebateAmountS + '\'' +
                ", storeReturnFinance='" + storeReturnFinance + '\'' +
                ", returnCostS='" + returnCostS + '\'' +
                ", isNationNosales30='" + isNationNosales30 + '\'' +
                ", isNationNosales60='" + isNationNosales60 + '\'' +
                ", isNationNosales90='" + isNationNosales90 + '\'' +
                ", isUnsalablenventory='" + isUnsalablenventory + '\'' +
                ", clickNoStock='" + clickNoStock + '\'' +
                ", clickCount='" + clickCount + '\'' +
                ", saleCount='" + saleCount + '\'' +
                '}';
    }
}
