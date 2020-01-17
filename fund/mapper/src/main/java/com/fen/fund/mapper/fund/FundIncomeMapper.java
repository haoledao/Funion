package com.fen.fund.mapper.fund;

import com.fen.fund.mapper.base.IBaseMapper;
import com.fen.fund.model.fund.FundIncome;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 基金收益Mapper
 * @author Fei
 * @date 2020-01-09
 */
@Mapper
public interface FundIncomeMapper extends IBaseMapper<FundIncome> {

    /**
     * 获取收益列表
     * @param income FundIncome
     * @return 收益列表
     */
    List<FundIncome> getIncomes(FundIncome income);

}
