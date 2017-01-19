<#list data as bean>

public void ${bean.method}(String ${bean.args}) {

    try {
        if(StringUtils.isNotEmpty(${bean.args})){
            String[] dataArray= ${bean.args}.split(",");
            if (dataArray!=null) {
                if (dataArray.length <  ${bean.array?size}) {
                    <#assign methods=""/>
                    <#list bean.array as method>
                        <#assign setMethod>this.${method}(Double.valueOf(dataArray[${method_index}]));</#assign>
                        <#if method_index==0>
                            if(dataArray.length== ${method_index+1}){
                                ${setMethod}
                            }<#rt>
                        <#assign methods>${setMethod}</#assign>
                        <#elseif method_index!=bean.array?size-1>
                            <#lt>else if(dataArray.length== ${method_index+1}){
                                ${methods}
                                ${setMethod}
                            }<#rt>
                            <#assign methods>
                                <#lt>${methods}
                                ${setMethod}<#rt>
                            </#assign>
                        </#if>
                    </#list>

                }else{
                    <#list bean.array as method>
                        this.${method}(Double.valueOf(dataArray[${method_index}]));
                    </#list>
                }
            }
        }
        this.${bean.args} = ${bean.args};
    } catch (Exception e) {
        logger.error(LOGGER_BEGIN+"字段 ${bean.field}对应的值"+${bean.args}+"解析出错",e);
    }
}

</#list>
<#--

${bean_}   ${bean.method}
    <#list bean.array as data>
    ${args}
    </#list>
</#list>

<#if data_index==1>

</#if>
<#if data_index==bean.array?size>

</#if>-->
