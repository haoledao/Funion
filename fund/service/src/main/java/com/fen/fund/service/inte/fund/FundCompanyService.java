package com.fen.fund.service.inte.fund;

import com.fen.fund.model.fund.FundCompany;
import com.fen.fund.service.support.BaseService;

import java.util.List;

/**
 * 基金公司业务接口
 * @author Fei
 * @date 2020-01-09
 */
public interface FundCompanyService extends BaseService<FundCompany> {

    /**
     * 新增公司
     * @param company FundCompany
     * @return 影响结果
     */
    int addCompany(FundCompany company);

    /**
     * 查找公司
     * @param company FundCompany
     * @return 公司列表
     */
    List<FundCompany> getCompanies(FundCompany company);

}
