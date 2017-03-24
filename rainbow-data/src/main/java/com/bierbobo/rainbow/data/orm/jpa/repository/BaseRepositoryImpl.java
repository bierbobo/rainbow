package com.bierbobo.rainbow.data.orm.jpa.repository;

import javax.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.io.Serializable;

public class BaseRepositoryImpl<T, ID extends Serializable>
    extends SimpleJpaRepository<T, ID>
    implements BaseRepository<T, ID> {

    private final Class<T> domainClass;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
    }


    @Override
    public boolean support(String modelType) {
        return domainClass.getName().equals(modelType);
    }



/*


    public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
    public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";

    private final EntityManager em;
    private final JpaEntityInformation<M, ID> entityInformation;

    private final RepositoryHelper repositoryHelper;

    private LockMetadataProvider lockMetadataProvider;

    private Class<M> entityClass;
    private String entityName;
    private String idName;


    */
/**
     * 查询所有的QL
     *//*

    private String findAllQL;
    */
/**
     * 统计QL
     *//*

    private String countAllQL;

    private QueryJoin[] joins;

    private SearchCallback searchCallback = SearchCallback.DEFAULT;

    public SimpleBaseRepository(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.entityName = this.entityInformation.getEntityName();
        this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
        this.em = entityManager;

        repositoryHelper = new RepositoryHelper(entityClass);

        findAllQL = String.format(FIND_QUERY_STRING, entityName);
        countAllQL = String.format(COUNT_QUERY_STRING, entityName);
    }
*/



}

