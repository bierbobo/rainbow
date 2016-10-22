package com.bierbobo;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Title: StreamingQueryRepository
 * Description: StreamingQueryRepository
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/7/19
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public interface EsRepository extends CrudRepository<AppDashboardAnalysis, Long> {

    /*@Query("select u from User u")*/
    //Stream<Product> findAllByCustomQueryAndStream();

//    Stream<Product> readAllByNameNotNull();
//
//    Stream<Product> findByAvailableTrue();

    /*@Query("select u from User u")*/
    //Stream<Product> streamAllPaged(Pageable pageable);

    Long deleteBybuyerErpId(String buyerErpId);


    List<AppDashboardAnalysis> findByItemFirstCateCd(String itemFirstCateCd);

}