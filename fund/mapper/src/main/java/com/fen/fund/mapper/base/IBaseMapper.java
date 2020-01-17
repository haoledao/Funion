package com.fen.fund.mapper.base;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Mapper基类
 * @author Fei
 * @date 2020-01-09
 */
public interface IBaseMapper<E> {

    /**
     * 插入对象
     * @param entity 实体对象
     * @return 影响行数
     */
    @InsertProvider(method = "insert", type = BaseSqlProvider.class)
    @Options(useGeneratedKeys = true)
    int insert(E entity);

    /**
     * 删除对象信息
     * @param entity 实体对象
     * @return 影响行数
     */
    @DeleteProvider(method = "delete", type = BaseSqlProvider.class)
    int delete(E entity);

    /**
     * 根据主键获取实体对象
     * @param entity 实体对象
     * @return 实体对象
     */
    @SelectProvider(method = "get", type = BaseSqlProvider.class)
    E get(E entity);

    /**
     * 修改对象信息
     * @param entity 实体对象
     * @return 影响行数
     */
    @UpdateProvider(method = "update", type = BaseSqlProvider.class)
    int update(E entity);

    /**
     * 更新指定属性
     * @param entity 实体对象
     * @param properties 指定属性
     * @return 影响行数
     */
    @UpdateProvider(method = "updateOnly", type = BaseSqlProvider.class)
    int updateOnly(@Param("entity") E entity, @Param("properties") String... properties);

    /**
     * 获取列表集合
     * @param entity 实体对象
     * @return 实体列表
     */
    @SelectProvider(method = "getAll", type = BaseSqlProvider.class)
    List<E> getAll(E entity, String... orders);

}
