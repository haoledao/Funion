package com.fen.fund.service.inte.fund;

import com.fen.fund.model.fund.FundType;
import com.fen.fund.service.support.BaseService;

import java.util.List;

/**
 * 基金类型业务接口
 * @author Fei
 * @date 2020-01-09
 */
public interface FundTypeService extends BaseService<FundType> {

    /**
     * 获取基金类型列表
     * @return 列表集合
     */
    List<FundType> getTypes();

}
