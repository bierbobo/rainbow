
<#list data as bean>
public String get${bean.method}() {
    return FormatUtil.roundHalfUp(${bean.field}, 2)+"";
}
</#list>



<#--<#list data as bean>-->
<#--public Integer get${bean.method}() {-->
    <#--if (${bean.field}!=null) {-->
        <#--return ${bean.field};-->
    <#--}else{-->
        <#--return 0;-->
    <#--}-->
<#--}-->
<#--</#list>-->

<#--
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
