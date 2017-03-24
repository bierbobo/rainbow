package com.bierbobo.rainbow.data.orm.jpa.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
    extends PagingAndSortingRepository<T, ID> {

    boolean support(String modelType);

}

