/*
package com.bierbobo;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by lifubo on 2016/9/12.
 *//*

public class EsTest {

    private static Logger logger = Logger.getLogger(Main.class);
    public static final String DISTRIBUTION_SORT = "633,631,630,628,625,619,617,616,615,545,682,608,605,609,7,610,316,8,9,5,4,10,3,6,-1"; //配送中心排序，按照倒序进行，列表之外的排在后面

    public static void main(String[] args) {

        int i=1;
        i+=2;
        System.out.println(i);

*/
/*
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
                "/spring/TransportClient_elasticsearchTemplate.xml");
        ElasticsearchTemplate elasticsearchTemplate = (ElasticsearchTemplate) appContext
                .getBean("elasticsearchTemplate");
        logger.info("elasticsearchTemplate：" + elasticsearchTemplate);


        AppDashboardBaseSkuInfo appDashboardAnalysis=new AppDashboardBaseSkuInfo();
        appDashboardAnalysis.setBiDataDate("2016-10-17");
        appDashboardAnalysis.setSkuId(1000336L);
        appDashboardAnalysis.setFirstIntoWhTm("2016-10-14 15:38:29");
        appDashboardAnalysis.setIsParallel(new Byte("0"));

        IndexQuery indexQuery = new IndexQuery();
        indexQuery.setId( "1000336_12312312");
        indexQuery.setObject(appDashboardAnalysis);
        elasticsearchTemplate.index(indexQuery);*//*



//        query(elasticsearchTemplate);


//        countTest(elasticsearchTemplate);
//        aggsTest(elasticsearchTemplate);
//        delTest(elasticsearchTemplate,AppDashboardBaseSkuInfo.class);
//        delTest(elasticsearchTemplate,AppDashboardBiipSales.class);
//        delTest(elasticsearchTemplate,AppDashboardBiipInOutStore.class);
//        delTest(elasticsearchTemplate,AppDashboardBiipTraffic.class);
//        addIndex(elasticsearchTemplate);
//        agg2(elasticsearchTemplate);
//        aggs1(elasticsearchTemplate);

        */
/*
        for (int i = DISTRIBUTION_SORT.split(",").length - 1; i >= 0; i--) {




        }
*//*


    }

    private static void query(ElasticsearchTemplate elasticsearchTemplate) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withIndices("app_dashboard_base_sku_info");
        searchQueryBuilder.withTypes("sku_info");
        searchQueryBuilder.withQuery(boolQueryBuilder);

        SearchQuery searchQuery =searchQueryBuilder.build();
        List<SkuInfo> skuInfos = elasticsearchTemplate.queryForList(searchQuery, SkuInfo.class);
        System.out.println(skuInfos);
    }

    private static void agg2(ElasticsearchTemplate elasticsearchTemplate) {
        List<String> result=new ArrayList<String>();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        //先对销量band进行group by
        TermsBuilder orgNationSaleNumBandTerms = AggregationBuilders.terms("orgNationSaleNumBandTerms").field("orgNationSaleNumBand");
        orgNationSaleNumBandTerms.size(0);

        //再对三级品类id进行group by
        TermsBuilder itemThirdCateCdTerms = AggregationBuilders.terms("itemThirdCateCdTerms").field("itemThirdCateCd");
        itemThirdCateCdTerms.size(0);
        orgNationSaleNumBandTerms.subAggregation(itemThirdCateCdTerms);

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.addAggregation(orgNationSaleNumBandTerms);
        searchQueryBuilder.withQuery(boolQueryBuilder);

        SearchQuery searchQuery =searchQueryBuilder.build();
        Aggregations aggregations =  elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        int i=1;
        StringTerms subjects = (StringTerms) aggregations.asMap().get("orgNationSaleNumBandTerms");
        for (Terms.Bucket bucket : subjects.getBuckets()) {

            String orgNationSaleNumBand= (String) bucket.getKey();
            StringTerms b = bucket.getAggregations().get("itemThirdCateCdTerms");
            for (Terms.Bucket bucket1 : b.getBuckets()) {
                String itemThirdCateCd = (String) bucket1.getKey();
                result.add(orgNationSaleNumBand+"_"+itemThirdCateCd);
                System.out.println(i++ +": "+orgNationSaleNumBand+"_"+itemThirdCateCd);
            }
        }
    }

    private static void aggs1(ElasticsearchTemplate elasticsearchTemplate) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termQuery("biDataDate", "2016-10-11"));

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(boolQueryBuilder);
        searchQueryBuilder.withIndices("app_dashboard_base_sku_info");

//        TermsBuilder termsBuilder = AggregationBuilders.terms("disList").field("skuId");
//        termsBuilder.order(Terms.Order.term(false));
//        termsBuilder.size(0);
//        searchQueryBuilder.addAggregation(termsBuilder);

        TermsBuilder ermsBuilder = AggregationBuilders.terms("a").field("orgNationSaleNumBand");//先对销量band进行group by
        TermsBuilder field = AggregationBuilders.terms("b").field("skuId");
        field.size(0);
        ermsBuilder.subAggregation(field);//再对三级品类id进行group by
        searchQueryBuilder.addAggregation(ermsBuilder);


        SearchQuery searchQuery =searchQueryBuilder.build();
        Aggregations aggregations =  elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        List<String> list=new ArrayList<>();
        StringTerms subjects = (StringTerms) aggregations.asMap().get("a");
        int i=1;
        for (Terms.Bucket bucket : subjects.getBuckets()) {

           String Key= (String) bucket.getKey();
            LongTerms b = bucket.getAggregations().get("b");
            for (Terms.Bucket bucket1 : b.getBuckets()) {
                String bucketKey =  bucket1.getKey().toString();
                list.add(Key+"_"+bucketKey);
                System.out.println(i++ +": "+Key+"_"+bucketKey);
            }
        }
//        System.out.println(list);
    }

    private static void addIndex(ElasticsearchTemplate elasticsearchTemplate) {
        for (int i = 0; i < 5; i++) {
            AppDashboardBaseSkuInfo appDashboardAnalysis=new AppDashboardBaseSkuInfo();
            appDashboardAnalysis.setBiDataDate("2016-10-0"+i);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(i + "");
            indexQuery.setObject(appDashboardAnalysis);
            elasticsearchTemplate.index(indexQuery);
        }

        for (int i = 0; i < 5; i++) {
            AppDashboardBiipInOutStore appDashboardAnalysis=new AppDashboardBiipInOutStore();
            appDashboardAnalysis.setBiDataDate("2016-10-0"+i);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(i + "");
            indexQuery.setObject(appDashboardAnalysis);
            elasticsearchTemplate.index(indexQuery);
        }

        for (int i = 0; i < 5; i++) {
            AppDashboardBiipSales appDashboardAnalysis=new AppDashboardBiipSales();
            appDashboardAnalysis.setBiDataDate("2016-10-0"+i);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(i + "");
            indexQuery.setObject(appDashboardAnalysis);
            elasticsearchTemplate.index(indexQuery);
        }
        for (int i = 0; i < 5; i++) {
            AppDashboardBiipTraffic appDashboardAnalysis=new AppDashboardBiipTraffic();
            appDashboardAnalysis.setBiDataDate("2016-10-0"+i);
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(i + "");
            indexQuery.setObject(appDashboardAnalysis);
            elasticsearchTemplate.index(indexQuery);
        }
    }

    private static void delTest(ElasticsearchTemplate elasticsearchTemplate,Class clazz) {
        // when
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(QueryBuilders.matchAllQuery());
//        deleteQuery.setQuery(QueryBuilders.termQuery("biDataDate", "2016-10-11"));
        elasticsearchTemplate.delete(deleteQuery, clazz);

//        elasticsearchTemplate.delete(AppDashboardBiipTraffic.class,"AVe3vA5ITSEBm1Z-yqcf");
    }

    private static void countTest(ElasticsearchTemplate elasticsearchTemplate) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        logger.info("query:"+nativeSearchQuery.getQuery());

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        SearchQuery searchQuery =searchQueryBuilder.build();
        logger.info("查询es语句"+searchQuery);
        long count =elasticsearchTemplate.count(searchQuery,AppDashboardAnalysis.class);
        logger.info("count:"+count);
    }

    private static void aggsTest(ElasticsearchTemplate elasticsearchTemplate) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        boolQueryBuilder.must(QueryBuilders.termQuery("biDataDate", "2016-10-11"));

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
        searchQueryBuilder.withQuery(boolQueryBuilder);
        searchQueryBuilder.withIndices("app_dashboard_base_sku_info");

        TermsBuilder termsBuilder = AggregationBuilders.terms("disList").field("skuId");
        termsBuilder.order(Terms.Order.term(false));
        termsBuilder.size(0);
        searchQueryBuilder.addAggregation(termsBuilder);


        SearchQuery searchQuery =searchQueryBuilder.build();
        Aggregations aggregations =  elasticsearchTemplate.query(searchQuery, new ResultsExtractor<Aggregations>() {
            @Override
            public Aggregations extract(SearchResponse response) {
                return response.getAggregations();
            }
        });

        LongTerms subjects = (LongTerms) aggregations.asMap().get("disList");
        int i=1;
        for (Terms.Bucket bucket : subjects.getBuckets()) {
            Long key = (Long) bucket.getKey();
            System.out.println(i++ +":"+ key);
        }
    }
}
*/
