package com.fen.fund.web;

import com.fen.fund.model.enums.CodeEnum;
import com.fen.fund.model.fund.Foundation;
import com.fen.fund.service.inte.fund.FoundationService;
import com.fen.fund.model.common.ApiData;
import com.fen.fund.model.common.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fei
 * @date 2020-01-09
 */
@RestController
@RequestMapping(Route.Fund.ROOT)
public class FoundationController {

    private FoundationService foundationService;

    @PostMapping(Route.Fund.ADD)
    public ApiData addFoundation(Foundation foundation) {
        int result = foundationService.addFoundation(foundation);

        if (result > 0) {
            return new ApiData(ApiData.SUCCESS, CodeEnum.OP000);
        } else {
            return new ApiData(CodeEnum.OP001);
        }
    }

    @GetMapping(Route.Fund.LIST)
    public ApiData getFoundations(Foundation foundation) {
        ApiData data = new ApiData();
        data.setData(foundationService.getFoundations(foundation));
        return data;
    }

    @Autowired
    public void setFoundationService(FoundationService foundationService) {
        this.foundationService = foundationService;
    }

}
