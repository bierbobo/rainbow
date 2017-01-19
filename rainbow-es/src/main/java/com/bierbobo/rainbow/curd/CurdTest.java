package com.bierbobo.rainbow.curd;

import com.bierbobo.rainbow.bean.ReportSkuDcMainDetail;
import com.bierbobo.rainbow.repository.EsRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.List;

/**
 * Created by lifubo on 2016/12/2.
 */
public class CurdTest {

    private static final String SKU_DC_MAIN_DETAIL_DC_INDEX = "";
    private static final String SKU_DC_MAIN_DETAIL_DC_TYPE = "";
    static   int scrollTimeInMillis = 2000;

    public static void main(String[] args) {


        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("/spring/elasticsearchRepository.xml");
        ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate) appContext.getBean("elasticsearchTemplate");
        EsRepository esRepository = (EsRepository) appContext.getBean("esRepository");
        updateByScroll(elasticsearchTemplate, esRepository);
        updateByCondition(elasticsearchTemplate, esRepository);


      //  EsRepository esRepository = (EsRepository) appContext.getBean("esRepository");

//        elasticsearchTemplate.deleteIndex(AppDashboardAnalysis.class);
//        elasticsearchTemplate.createIndex(AppDashboardAnalysis.class);
//        elasticsearchTemplate.putMapping(AppDashboardAnalysis.class);
//        elasticsearchTemplate.refresh(AppDashboardAnalysis.class);

//        Mysql2Es db2Es=new Mysql2Es();
//        db2Es.initData(jdbcTemplate,esRepository);
//        esRepository.deleteBybuyerErpId("limengmeng");
        //update(elasticsearchTemplate, esRepository);

    }

    private static void updateByScroll(ElasticsearchTemplate elasticsearchTemplate, EsRepository esRepository) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termsQuery("skuId", "1943952"));
        boolQueryBuilder.must(QueryBuilders.termQuery("biDataDate", "2016-11-27"));

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(boolQueryBuilder);
        searchQueryBuilder.withIndices(SKU_DC_MAIN_DETAIL_DC_INDEX);
        searchQueryBuilder.withTypes(SKU_DC_MAIN_DETAIL_DC_TYPE);

//        FetchSourceFilterBuilder sourceFilter = new FetchSourceFilterBuilder();
//        sourceFilter.withIncludes("skuId","distributionId","rdcId","whQt");
//        searchQueryBuilder.withSourceFilter(sourceFilter.build());

        PageRequest pageRequest= new PageRequest(0, 40);
        searchQueryBuilder.withPageable(pageRequest);


        SearchQuery searchQuery =searchQueryBuilder.build();

        String scrollId = elasticsearchTemplate.scan(searchQuery, scrollTimeInMillis, false, ReportSkuDcMainDetail.class);
        boolean hasRecords = true;
        while (hasRecords) {

            Page<ReportSkuDcMainDetail> page = elasticsearchTemplate.scroll(scrollId, scrollTimeInMillis, ReportSkuDcMainDetail.class);

            if (page.hasContent()) {
                List<ReportSkuDcMainDetail> skuDcMainDetails=page.getContent();
                for (ReportSkuDcMainDetail skuDcMainDetail : skuDcMainDetails) {

                    skuDcMainDetail.setRdcName("全国");
                    esRepository.save(skuDcMainDetail);

                }

            } else {
                hasRecords = false;
            }
        }

        elasticsearchTemplate.clearScroll(scrollId);
    }

    private static void updateByCondition(ElasticsearchTemplate elasticsearchTemplate, EsRepository esRepository) {

        for (ReportSkuDcMainDetail appDashboardAnalysis : esRepository.findByBiDataDate("2016-11-30")) {
            appDashboardAnalysis.setBiDataDate("2016-11-27");
            esRepository.save(appDashboardAnalysis);
        }
        elasticsearchTemplate.refresh(ReportSkuDcMainDetail.class);

    }

}
