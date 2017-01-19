package com.bierbobo.rainbow.repository;


import com.bierbobo.rainbow.bean.ReportSkuDcMainDetail;
import com.bierbobo.rainbow.bean.ReportSkuInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SkuRepository extends CrudRepository<ReportSkuInfo, String> {

    /*@Query("select u from User u")*/
    //Stream<Product> findAllByCustomQueryAndStream();

//    Stream<Product> readAllByNameNotNull();
//
//    Stream<Product> findByAvailableTrue();

    /*@Query("select u from User u")*/
    //Stream<Product> streamAllPaged(Pageable pageable);

//    Long deleteBybuyerErpId(String buyerErpId);
//    List<ReportSkuDcMainDetail> findByItemFirstCateCd(String itemFirstCateCd);
    List<ReportSkuInfo> findByBuyerErpId(String buyerErpId);
    List<ReportSkuInfo> findBySalerErpId(String salerErpId);

}