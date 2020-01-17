package com.fen.fund.service.support;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Fei
 * @date 2020-01-09
 */
class MapperInfo {

    private static final Logger LOG = Logger.getLogger(MapperInfo.class);

    /**
     * 获取指定类的父类的泛型参数的实际类型
     */
    static Class<?> getSuperClass(Class<?> clazz, int index) {
        if (clazz == Object.class) {
            return null;
        }

        Type genType = clazz.getGenericSuperclass();
        if (genType == null) {
            Type[] genericInterfaces = clazz.getGenericInterfaces();
            if (ArrayUtils.isNotEmpty(genericInterfaces)) {
                genType = genericInterfaces[0];
            }
        }

        // 如果没有实现ParameterizedType接口，即不支持泛型，直接返回Object.class
        if (!(genType instanceof ParameterizedType)) {
            Class<?> superclass = clazz.getSuperclass();
            if (superclass == Object.class) {
                return Object.class;
            } else {
                return getSuperClass(superclass, index);
            }
        }

        // 返回表示此类型实际类型参数的Type对象的数组,数组里放的都是对应类型的Class,
        // 如NameAndAge extends Pair<String, Integer>就返回String和Integer类型
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index < 0 || index >= params.length) {
            LOG.error("输入的索引" + (index < 0 ? "不能小于0" : "超出了参数的总数"));
            return null;
        }

        if (!(params[index] instanceof Class)) {
            return Object.class;
        }

        return (Class<?>) params[index];
    }

}
