package com.bierbobo.rainbow.repository;


import com.bierbobo.rainbow.service.AppDashboardAnalysis;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EsRepository1 extends CrudRepository<AppDashboardAnalysis, Long> {

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