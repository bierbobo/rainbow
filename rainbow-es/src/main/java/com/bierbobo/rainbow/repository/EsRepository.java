package com.bierbobo.rainbow.repository;


import com.bierbobo.rainbow.bean.ReportSkuDcMainDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EsRepository extends CrudRepository<ReportSkuDcMainDetail, String> {

    /*@Query("select u from User u")*/
    //Stream<Product> findAllByCustomQueryAndStream();

//    Stream<Product> readAllByNameNotNull();
//
//    Stream<Product> findByAvailableTrue();

    /*@Query("select u from User u")*/
    //Stream<Product> streamAllPaged(Pageable pageable);

//    Long deleteBybuyerErpId(String buyerErpId);
//    List<ReportSkuDcMainDetail> findByItemFirstCateCd(String itemFirstCateCd);
    List<ReportSkuDcMainDetail> findByBiDataDate(String biDataDate);




}