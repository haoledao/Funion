package com.fen.fund.service.impl.fund;

import com.fen.fund.mapper.fund.FundCompanyMapper;
import com.fen.fund.model.fund.FundCompany;
import com.fen.fund.service.inte.fund.FundCompanyService;
import com.fen.fund.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 基金公司业务实现类
 * @author Fei
 * @date 2020-01-09
 */
@Service
public class FundCompanyServiceImpl extends BaseServiceImpl<FundCompanyMapper, FundCompany> implements FundCompanyService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addCompany(FundCompany company) {
        company.setCreateTime(new Date());
        return mapper.insert(company);
    }

    @Override
    public List<FundCompany> getCompanies(FundCompany company) {
        return mapper.getCompanies(company);
    }

}
