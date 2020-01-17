package com.fen.fund.service.impl.fund;

import com.fen.fund.mapper.fund.FoundationMapper;
import com.fen.fund.model.enums.FundStatusEnum;
import com.fen.fund.model.enums.ProfitStatusEnum;
import com.fen.fund.model.fund.Foundation;
import com.fen.fund.service.inte.fund.FoundationService;
import com.fen.fund.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 基金业务实现类
 * @author Fei
 * @date 2020-01-09
 */
@Service
public class FoundationServiceImpl extends BaseServiceImpl<FoundationMapper, Foundation> implements FoundationService {

    @Override
    public List<Foundation> getFoundations(Foundation foundation) {
        return mapper.getFoundations(foundation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addFoundation(Foundation foundation) {
        foundation.setCreateTime(new Date());
        foundation.setProfitStatus(ProfitStatusEnum.STOP.getCode());
        foundation.setStatus(FundStatusEnum.ON.getCode());
        return mapper.insert(foundation);
    }

}
