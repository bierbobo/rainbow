package com.bierbobo.rainbow.curd;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lifubo on 2016/11/9.
 */

@Document(indexName = "app_dashboard_report_sku_dc_main_detail_dc", type = "report_center")
public class ReportSkuDcMainDetail1 {

    private static String LOGGER_BEGIN = "【skuDcMain维度数据解析】";
    Logger logger = Logger.getLogger(ReportSkuDcMainDetail1.class);

    @Id
    private String id;//商品编号
    private String biDataDate;//数据灌入时间
    private Long skuId;//商品编号

    //商品信息
    private Integer distributionId;//配送中心
    private String distributionName;//配送中心名称
    private Integer storeId;//库房编号
    private String storeName;//库房名称
    private Integer rdcId;//区域RDC仓ID
    private String rdcName;//区域RDC仓名称
    private String vendorCode;//供应商简码(配送中心)
    private String vendorName;//供应商名称(配送中心)

    //价格相关
    private BigDecimal whQt;//仓报价
    private BigDecimal purchasePrice;//采购价

    //Band
    private String orgSaleNumBand71;//区域7天一级销量Band
    private String orgSaleNumBand73;//区域7天三级销量Band

    //SKU标示
    private Byte isNosales30;//是否30日不动销
    private Byte isNosales60;//是否60日不动销
    private Byte isNosales90;//是否90日不动销
    private Byte isUnsalablenventory;//是否件数滞销


    //收货地有效销量
    private String orderSalesQuantity;//收货地有效销量


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //出库地出库销量
    private String wmsRecQtty;//出库地出库销量

    public void setOrderSalesQuantity(String orderSalesQuantity) {
        this.orderSalesQuantity = orderSalesQuantity;
    }

    public void setWmsRecQtty(String wmsRecQtty) {
        this.wmsRecQtty = wmsRecQtty;
    }

    public String getOrderSalesQuantity() {
        return orderSalesQuantity;
    }

    public String getWmsRecQtty() {
        return wmsRecQtty;
    }

    public String getBiDataDate() {
        return biDataDate;
    }

    public void setBiDataDate(String biDataDate) {
        this.biDataDate = biDataDate;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
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

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getRdcId() {
        return rdcId;
    }

    public void setRdcId(Integer rdcId) {
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

    public BigDecimal getWhQt() {
        return whQt;
    }

    public void setWhQt(BigDecimal whQt) {
        this.whQt = whQt;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
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

    public Byte getIsNosales30() {
        return isNosales30;
    }

    public void setIsNosales30(Byte isNosales30) {
        this.isNosales30 = isNosales30;
    }

    public Byte getIsNosales60() {
        return isNosales60;
    }

    public void setIsNosales60(Byte isNosales60) {
        this.isNosales60 = isNosales60;
    }

    public Byte getIsNosales90() {
        return isNosales90;
    }

    public void setIsNosales90(Byte isNosales90) {
        this.isNosales90 = isNosales90;
    }

    public Byte getIsUnsalablenventory() {
        return isUnsalablenventory;
    }

    public void setIsUnsalablenventory(Byte isUnsalablenventory) {
        this.isUnsalablenventory = isUnsalablenventory;
    }




}
