package com.bierbobo.rainbow.data.nosql.es.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactory;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

/**
 *  自定义工厂
 * @author: zonggf
 * @date: 2016年1月18日-下午2:16:18
 */
public class CustomerJpaRepositoryFactoryBean<T extends Repository<S, ID>, S, ID extends Serializable>
		extends ElasticsearchRepositoryFactoryBean<T, S, ID> {



	@Override
	protected RepositoryFactorySupport createRepositoryFactory() {
		return new ElasticsearchRepositoryFactory(null);
	}





	private static class CustomerRepositoryFactory extends ElasticsearchRepositoryFactory {


		public CustomerRepositoryFactory(ElasticsearchOperations elasticsearchOperations) {
			super(elasticsearchOperations);
		}

		/*private EntityManager entityManager;

		public CustomerRepositoryFactory(EntityManager entityManager) {
			super(entityManager);
			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata) {
			return new IBaseDaoImpl<T, I>((Class<T>) metadata.getDomainType(), entityManager);
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
			return IBaseDao.class;
		}*/
	}
}