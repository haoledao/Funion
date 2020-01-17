package com.fen.fund.web;

import com.fen.fund.model.common.ApiData;
import com.fen.fund.model.common.Route;
import com.fen.fund.model.enums.CodeEnum;
import com.fen.fund.model.fund.FundCompany;
import com.fen.fund.service.inte.fund.FundCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fei
 * @date 2020-01-10
 */
@RestController
@RequestMapping(Route.Company.ROOT)
public class FundCompanyController {

    private FundCompanyService companyService;

    /**
     * 新增公司
     * @param company FundCompany
     * @return 结果
     */
    @PostMapping(Route.Company.ADD)
    public ApiData addCompany(FundCompany company) {
        int result = companyService.addCompany(company);
        if (result > 0) {
            return new ApiData(ApiData.SUCCESS, CodeEnum.OP000);
        } else {
            return new ApiData(CodeEnum.OP001);
        }
    }

    /**
     * 查找公司
     * @param company FundCompany
     * @return 公司列表
     */
    @GetMapping(Route.Company.LIST)
    public ApiData getCompanies(FundCompany company) {
        List<FundCompany> companies = companyService.getCompanies(company);
        return new ApiData(companies);
    }

    @Autowired
    public void setCompanyService(FundCompanyService companyService) {
        this.companyService = companyService;
    }

}
