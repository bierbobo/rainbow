<#list data as bean>

public void ${bean.method}(String ${bean.args}) {

    try {
        if(StringUtils.isNotEmpty(${bean.args})){
            String[] dataArray= ${bean.args}.split(",");
            if (dataArray!=null) {
                if (dataArray.length <  ${bean.array?size}) {
                    <#assign methods=""/>
                    <#list bean.array as method>
                        <#if method_index==0>
                            if(dataArray.length== ${method_index+1}){
                                this.${method}(dataArray[${method_index}]);
                            }<#rt>
                        <#assign methods>this.${method}(dataArray[${method_index}]);</#assign>
                        <#elseif method_index!=bean.array?size-1>
                            <#lt>else if(dataArray.length== ${method_index+1}){
                                ${methods}
                                this.${method}(dataArray[${method_index}]);
                            }<#rt>
                            <#assign methods>
                                <#lt>${methods}
                                this.${method}(dataArray[${method_index}]); <#rt>
                            </#assign>
                        </#if>
                    </#list>

                }else{
                    <#list bean.array as method>
                    this.${method}(dataArray[${method_index}]);
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



<#list data as bean>

public void ${bean.method}(String ${bean.args}) {

try {
if(StringUtils.isNotEmpty(${bean.args})){
String[] dataArray= ${bean.args}.split(",");
if (dataArray!=null) {
if (dataArray.length <  ${bean.array?size}) {
    <#assign methods=""/>
    <#list bean.array as method>
        <#if method_index==0>
        if(dataArray.length== ${method_index+1}){
        this.${method}(dataArray[${method_index}]);
        }<#rt>
            <#assign methods>this.${method}(dataArray[${method_index}]);</#assign>
        <#elseif method_index!=bean.array?size-1>
            <#lt>else if(dataArray.length== ${method_index+1}){
        ${methods}
        this.${method}(dataArray[${method_index}]);
        }<#rt>
            <#assign methods>
                <#lt>${methods}
            this.${method}(dataArray[${method_index}]); <#rt>
            </#assign>
        </#if>
    </#list>

}else{
    <#list bean.array as method>
    this.${method}(dataArray[${method_index}]);
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

