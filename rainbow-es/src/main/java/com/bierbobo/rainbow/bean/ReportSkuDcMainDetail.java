package com.bierbobo.rainbow.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "app_dashboard_report_sku_dc_main_detail_rdc", type = "report_center")
public class ReportSkuDcMainDetail {

    @Id
    private String id;//商品编号
    private String biDataDate;//数据灌入时间
    private String skuId;//商品编号

    //商品信息
    private String distributionId;//配送中心
    private String distributionName;//配送中心名称
    private String storeId;//库房编号
    private String storeName;//库房名称
    private String rdcId;//区域RDC仓ID
    private String rdcName;//区域RDC仓名称
    private String vendorCode;//供应商简码(配送中心)
    private String vendorName;//供应商名称(配送中心)

    //价格相关
    private String whQt;//仓报价
    private String purchasePrice;//采购价

    //Band
    private String orgSaleNumBand71;//区域7天一级销量Band
    private String orgSaleNumBand73;//区域7天三级销量Band

    //SKU标示
    private String isNosales30;//是否30日不动销
    private String isNosales60;//是否60日不动销
    private String isNosales90;//是否90日不动销
    private String isUnsalablenventory;//是否件数滞销


    //收货地有效销量
    private String orderSalesQuantity;//收货地有效销量

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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getDistributionId() {
        return distributionId;
    }

    public void setDistributionId(String distributionId) {
        this.distributionId = distributionId;
    }

    public String getDistributionName() {
        return distributionName;
    }

    public void setDistributionName(String distributionName) {
        this.distributionName = distributionName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRdcId() {
        return rdcId;
    }

    public void setRdcId(String rdcId) {
        this.rdcId = rdcId;
    }

    public String getRdcName() {
        return rdcName;
    }

    public void setRdcName(String rdcName) {
        this.rdcName = rdcName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getWhQt() {
        return whQt;
    }

    public void setWhQt(String whQt) {
        this.whQt = whQt;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getOrgSaleNumBand71() {
        return orgSaleNumBand71;
    }

    public void setOrgSaleNumBand71(String orgSaleNumBand71) {
        this.orgSaleNumBand71 = orgSaleNumBand71;
    }

    public String getOrgSaleNumBand73() {
        return orgSaleNumBand73;
    }

    public void setOrgSaleNumBand73(String orgSaleNumBand73) {
        this.orgSaleNumBand73 = orgSaleNumBand73;
    }

    public String getIsNosales30() {
        return isNosales30;
    }

    public void setIsNosales30(String isNosales30) {
        this.isNosales30 = isNosales30;
    }

    public String getIsNosales60() {
        return isNosales60;
    }

    public void setIsNosales60(String isNosales60) {
        this.isNosales60 = isNosales60;
    }

    public String getIsNosales90() {
        return isNosales90;
    }

    public void setIsNosales90(String isNosales90) {
        this.isNosales90 = isNosales90;
    }

    public String getIsUnsalablenventory() {
        return isUnsalablenventory;
    }

    public void setIsUnsalablenventory(String isUnsalablenventory) {
        this.isUnsalablenventory = isUnsalablenventory;
    }

    public String getOrderSalesQuantity() {
        return orderSalesQuantity;
    }

    public void setOrderSalesQuantity(String orderSalesQuantity) {
        this.orderSalesQuantity = orderSalesQuantity;
    }
}
