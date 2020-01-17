package com.fen.fund.service.support;

import com.fen.fund.mapper.base.IBaseMapper;
import com.fen.fund.model.common.CacheTool;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 业务处理基类实现类
 * @author Fei
 * @date 2020-01-09
 */
@Service
@SuppressWarnings({"unchecked"})
public class BaseServiceImpl<M extends IBaseMapper, E> implements BaseService<E>, InitializingBean {

    private SqlSessionTemplate template;
    protected M mapper;
    @Resource
    protected CacheTool cacheTool;

    @Override
    public void afterPropertiesSet() {
        Class clazz = getClass();
        if (clazz.getName().equals(BaseServiceImpl.class.getName())) {
            return;
        }
        Class<M> mapperClass = (Class<M>) MapperInfo.getSuperClass(clazz, 0);
        mapper = template.getMapper(mapperClass);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(E entity) {
        return mapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int delete(E entity) {
        return mapper.delete(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(E entity) {
        return mapper.update(entity);
    }

    @Override
    public E get(E entity) {
        return (E) mapper.get(entity);
    }

    @Override
    public int updateOnly(E entity, String... properties) {
        return mapper.updateOnly(entity, properties);
    }

    @Override
    public List<E> getAll(E entity, String... orders) {
        return mapper.getAll(entity, orders);
    }

    @Autowired
    public void setTemplate(SqlSessionTemplate template) {
        this.template = template;
    }

}
