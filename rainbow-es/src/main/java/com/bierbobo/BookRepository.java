package com.bierbobo;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * Title: BookRepository
 * Description: BookRepository
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/7/14
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public interface BookRepository extends Repository<SampleEntity, String> {

    List<SampleEntity> findByNameAndPrice(String name, Integer price);

    List<SampleEntity> findByNameOrPrice(String name, Integer price);

    Page<SampleEntity> findByName(String name, Pageable page);

    Page<SampleEntity> findByNameNot(String name, Pageable page);

    Page<SampleEntity> findByPriceBetween(int price, Pageable page);

    Page<SampleEntity> findByNameLike(String name, Pageable page);

    @Query("{\"bool\" : {\"must\" : {\"term\" : {\"message\" : \"?0\"}}}}")
    Page<SampleEntity> findByMessage(String message, Pageable pageable);
}
