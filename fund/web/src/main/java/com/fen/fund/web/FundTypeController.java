package com.fen.fund.web;

import com.fen.fund.model.common.ApiData;
import com.fen.fund.model.common.CacheKey;
import com.fen.fund.model.common.Route;
import com.fen.fund.model.fund.FundType;
import com.fen.fund.service.inte.fund.FundTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Fei
 * @date 2020-01-09
 */
@RestController
@RequestMapping(Route.Type.ROOT)
public class FundTypeController extends BaseController {

    private FundTypeService typeService;

    @GetMapping(value = Route.Type.LIST)
    public ApiData getTypes() {
        List<FundType> types = cacheTool.getFundTypes(CacheKey.CK_FUND_TYPE);
        if (types == null) {
            types = typeService.getTypes();
        }
        return new ApiData(types);
    }

    @Autowired
    public void setTypeService(FundTypeService typeService) {
        this.typeService = typeService;
    }

}
