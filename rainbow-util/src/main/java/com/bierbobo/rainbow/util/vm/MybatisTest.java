package com.bierbobo.rainbow.util.vm;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import junit.framework.Test;

/**
 * Created by lifubo on 2016/11/15.
 */
public class MybatisTest {

    public static void main(String[] args) {

        Configuration configuration = new Configuration();
        configuration.setObjectWrapper(new DefaultObjectWrapper());
        configuration.setTemplateLoader(new ClassTemplateLoader(MybatisTest.class, "/"));


        try {
            Template template = configuration.getTemplate("mybatis.ftl");
            StringWriter writer = new StringWriter();



//            String[] split = "storageInventory,storageInventory_num_stock,storageInventory_num_stock_amount,storageInventory_kc_flow,storageInventory_mx_stock,storageInventory_mx_amount,storageInventory_zx_stock,storageInventory_zx_amount,inventoryAgingDay_1_7,inventoryAgingDay_8_15,inventoryAgingDay_16_30,inventoryAgingDay_31_60,inventoryAgingDay_61_90,inventoryAgingDay_91_120,inventoryAgingDay_121_150,inventoryAgingDay_151_180,inventoryAgingDay_181_360,inventoryAgingYear_1_2,inventoryAgingYear_2_3,inventoryAgingYear_3_4,inventoryAgingYear_4_5,inventoryAgingYear_5_6,inventoryAgingYear_6_7,inventoryAgingYear7_,inventoryAgingDayAmount_1_7,inventoryAgingDayAmount_8_15,inventoryAgingDayAmount_16_30,inventoryAgingDayAmount_31_60,inventoryAgingDayAmount_61_90,inventoryAgingDayAmount_91_120,inventoryAgingDayAmount_121_150,inventoryAgingDayAmount_151_180,inventoryAgingDayAmount_181_360,inventoryAgingYearAmount_1_2,inventoryAgingYearAmount_2_3,inventoryAgingYearAmount_3_4,inventoryAgingYearAmount_4_5,inventoryAgingYearAmount_5_6,inventoryAgingYearAmount_6_7,inventoryAgingYearAmount7_,conversionRatePV1,conversionRatePV7,conversionRatePV14,conversionRatePV28,conversionRatePV30,conversionRatePV60,conversionRatePV90,noShowPV1,noShowPV7,noShowPV14,noShowPV28,noShowPV30,noShowPV60,noShowPV90,spotGoodsRatePV1,spotGoodsRatePV7,spotGoodsRatePV14,spotGoodsRatePV28,spotGoodsRatePV30,spotGoodsRatePV60,spotGoodsRatePV90".split(",");
            String[] split1 = "stock,availableNumOfDays7,availableNumOfDays14,availableNumOfDays28,availableNumOfDays30,availableNumOfDays60,availableNumOfDays90,purchaseQuantity1,purchaseQuantity7,purchaseQuantity14,purchaseQuantity28,purchaseQuantity30,purchaseQuantity60,purchaseQuantity90,vreturnQuantity1,vreturnQuantity7,vreturnQuantity14,vreturnQuantity28,vreturnQuantity30,vreturnQuantity60,vreturnQuantity90,totalPV1,totalPV7,totalPV14,totalPV28,totalPV30,totalPV60,totalPV90,noStockPV,noStockPV1,noStockPV7,noStockPV14,noStockPV28,noStockPV30,noStockPV60,noStockPV90".split(",");
//            String[] split =  "inventoryAgingDay_1_7,inventoryAgingDay_8_15,inventoryAgingDay_16_30,inventoryAgingDay_31_60,inventoryAgingDay_61_90,inventoryAgingDay_91_120,inventoryAgingDay_121_150,inventoryAgingDay_151_180,inventoryAgingDay_181_360,inventoryAgingYear_1_2,inventoryAgingYear_2_3,inventoryAgingYear_3_4,inventoryAgingYear_4_5,inventoryAgingYear_5_6,inventoryAgingYear_6_7,inventoryAgingYear7_".split(",");

//            String[] split =  "noShowPV1,noShowPV7,noShowPV14,noShowPV28,noShowPV30,noShowPV60,noShowPV90".split(",");
            String[] split =  "inStockNum,inStockNumCost,transferInNum,ztNum,transferOutNum,numOrderBooking,numAppBooking,numNosale,availableStock,notArriveStock,canReserveNum,numOrderTransfer".split(",");

            List<Map> list=new ArrayList<Map>();
            for (String field : split) {
                Map map=new HashMap<>();
                String method = field.substring(0, 1).toUpperCase() + field.substring(1);
                map.put("method",method);
                map.put("field",field);
                list.add(map);
            }


            Map<String, Object> context = new HashMap<String, Object>();
//            context.put("data", JsonUtil.generateData());
            context.put("data", list);
            template.process(context, writer);

            System.out.println(writer.toString());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
