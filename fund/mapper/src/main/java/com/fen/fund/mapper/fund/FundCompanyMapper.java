package com.fen.fund.mapper.fund;

import com.fen.fund.mapper.base.IBaseMapper;
import com.fen.fund.model.fund.FundCompany;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 基金公司Mapper
 * @author Fei
 * @date 2020-01-09
 */
@Mapper
public interface FundCompanyMapper extends IBaseMapper<FundCompany> {

    /**
     * 获取公司列表
     * @param company FundCompany
     * @return 公司列表
     */
    List<FundCompany> getCompanies(FundCompany company);

}
