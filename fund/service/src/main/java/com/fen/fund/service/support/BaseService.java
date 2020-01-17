package com.fen.fund.service.support;

import java.util.List;

/**
 * 业务处理基类接口
 * @author Fei
 * @date 2020-01-09
 */
public interface BaseService<E> {

    /**
     * 新增
     * @param entity 对象实体
     * @return 对象
     */
    int insert(E entity);

    /**
     * 删除
     * @param entity 对象实体
     * @return 影响行数
     */
    int delete(E entity);

    /**
     * 修改
     * @param entity 对象实体
     * @return 影响行数
     */
    int update(E entity);

    /**
     * 查询
     * @param entity 实体对象
     * @return 对象
     */
    E get(E entity);

    /**
     * 修改指定属性
     * @param entity 实体对象
     * @param properties 指定属性
     * @return 影响行数
     */
    int updateOnly(E entity, String... properties);

    /**
     * 获取实体列表
     * @param entity 实体对象
     * @return 列表集合
     */
    List<E> getAll(E entity, String... orders);

}
