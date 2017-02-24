package com.bierbobo.rainbow.bean;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "app_dashboard_report_sku_dc_main_detail_rdc", type = "report_center")
public class ReportSkuDcMainDetail {

private String id;//商品编号



}


<#--

<#list data as bean>
public Integer get${bean.method}() {
    if (${bean.field}!=null) {
        return ${bean.field};
    }else{
        return 0;
    }
}
</#list>

<#list data as bean>
public String get${bean.method}() {
    if (StringUtils.isNotBlank(${bean.field})) {
        return ${bean.field};
    }else{
        return "0";
    }
}
</#list>

-->
