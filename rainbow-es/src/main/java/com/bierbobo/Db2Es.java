package com.bierbobo;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by lifubo on 2016/9/13.
 */
public class Db2Es {

    public static void main(String[] args) {


        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring/elasticsearchRepository.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) appContext.getBean("jdbcTemplate");
        ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate) appContext.getBean("elasticsearchTemplate");
        EsRepository esRepository = (EsRepository) appContext.getBean("esRepository");

        Db2Es db2Es=new Db2Es();
//        db2Es.initData(jdbcTemplate,esRepository);
       // esRepository.deleteBybuyerErpId("limengmeng");

        for (AppDashboardAnalysis appDashboardAnalysis : esRepository.findByItemFirstCateCd("11729")) {

//            appDashboardAnalysis.setBuyerErpId("limengmeng");
//            appDashboardAnalysis.setSalerErpId("limengmeng");

            Random random=new Random();

            Integer distributionId = appDashboardAnalysis.getDistributionId();
            if(distributionId==null ){
                appDashboardAnalysis.setDistributionId(random.nextInt(1000));
            }
            esRepository.save(appDashboardAnalysis);
//            System.out.println(appDashboardAnalysis);
        }
        elasticsearchTemplate.refresh(AppDashboardAnalysis.class);

    }



    private void initData( JdbcTemplate jdbcTemplate, EsRepository streamingQueryRepository) {

//        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select w.wid as skuId  ,wname    as  skuName  ," +
//                        "level_1_id   as  itemFirstCateCd ,level_2_id   as  itemSecondCateCd ,level_3_id   as  itemThirdCateCd ,level_1_name  as  itemFirstCateName ,level_2_name  as  itemSecondCateName ,level_3_name  as  itemThirdCateName ," +
//                        "nation_vendor_code as  nationVendorCode ,nation_vendor_name as  nationVendorName ,brand_id   as  brandCode  ,brand_name   as  brandName  ,sale_status   as  saleStatus  ,last_sale_time  as  lastSaleTime  ,market_price  as  marketPrice  ,click_count   as  clickCount  ,click_no_stock  as  clickNoStock  ,click_band   as  clickBand  ,sale_band   as  saleBand   ,sale_count   as  saleCount  ,saler_erp_id  as  salerErpId  ,saler_name   as  salerName  ,buyer_erp_id  as  buyerErpId  ,buyer_name   as  buyerName  ,is_dropship   as  isDropship  ,is_national   as  isNational  ,is_shelf_life   as  isShelfLife  ,is_stop_no_stock  as  isStopNoStock ,   distribution_id  as  distributionId ,distribution_name  as  distributionName ,warehouse_price  as  warehousePrice  ,allow_reserve_flag as  allowReserveFlag ,ytd_sales   as  ytdSales   ,sales_7   as  sales7   ,sales_14   as  sales14  ,sales_28   as  sales28  ,sales_60   as  sales60  ,sales_90   as  sales90  ,cur_month_sales  as  curMonthSales  " +
//                        "from ware w left join sales s on w.wid=s.wid where w.bi_data_date='2015-11-09' and  " +
//                        "w.wid in('100001','100003','100004','100005','100006','100007','100008','100009','100010','100011','100012','100013','100014','100015','100016','100017','100018','100019','100020','100021','100022','100023','100024','100025','100026','100027','100028','100029','100030','100031','100032','100033')  "
//        );

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select w.wid as skuId  ,wname    as  skuName  ," +
                        "level_1_id   as  itemFirstCateCd ,level_2_id   as  itemSecondCateCd ,level_3_id   as  itemThirdCateCd ,level_1_name  as  itemFirstCateName ,level_2_name  as  itemSecondCateName ,level_3_name  as  itemThirdCateName ," +
                        "nation_vendor_code as  nationVendorCode ,nation_vendor_name as  nationVendorName ,brand_id   as  brandCode  ,brand_name   as  brandName  ,sale_status   as  saleStatus  ,last_sale_time  as  lastSaleTime  ,market_price  as  marketPrice  ,click_count   as  clickCount  ,click_no_stock  as  clickNoStock  ,click_band   as  clickBand  ,sale_band   as  saleBand   ,sale_count   as  saleCount  ,saler_erp_id  as  salerErpId  ,saler_name   as  salerName  ,buyer_erp_id  as  buyerErpId  ,buyer_name   as  buyerName  ,is_dropship   as  isDropship  ,is_national   as  isNational  ,is_shelf_life   as  isShelfLife  ,is_stop_no_stock  as  isStopNoStock ,   distribution_id  as  distributionId ,distribution_name  as  distributionName ,warehouse_price  as  warehousePrice  ,allow_reserve_flag as  allowReserveFlag ,ytd_sales   as  ytdSales   ,sales_7   as  sales7   ,sales_14   as  sales14  ,sales_28   as  sales28  ,sales_60   as  sales60  ,sales_90   as  sales90  ,cur_month_sales  as  curMonthSales  " +
                        "from ware w left join sales s on w.wid=s.wid where w.bi_data_date='2015-11-09' and  " +
                       " ( w.buyer_erp_id in('limengmeng') or w.saler_erp_id  in('limengmeng') )  "
        );

        System.out.println(maps.size());

        List<AppDashboardAnalysis> list=new ArrayList<AppDashboardAnalysis>();
        for (Map<String, Object> map : maps) {
            AppDashboardAnalysis app = (AppDashboardAnalysis) convertMap(AppDashboardAnalysis.class, map);
            list.add(app);
        }


//        Tool.ES_TEMPLATE.deleteIndex(AppDashboardAnalysis.class);
//        Tool.ES_TEMPLATE.createIndex(AppDashboardAnalysis.class);
//        Tool.ES_TEMPLATE.putMapping(AppDashboardAnalysis.class);
//        Tool.ES_TEMPLATE.refresh(AppDashboardAnalysis.class);

        streamingQueryRepository.save(list);
    }

    public static Object convertMap(Class type, Map map)  {


        Object obj = null; // 创建 JavaBean 对象
        String propertyName=null;
        String typeName=null;

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
            obj = type.newInstance();

            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                propertyName = descriptor.getName();

                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);

                    if(value!=null){

                        typeName=value.getClass().getName();

                        Object[] args = new Object[1];
                        args[0] = value;

                        descriptor.getWriteMethod().invoke(obj, args);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("转换属性"+propertyName+"时类型不匹配,所属类型"+typeName);
            e.printStackTrace();
        }


        return obj;
    }
}
