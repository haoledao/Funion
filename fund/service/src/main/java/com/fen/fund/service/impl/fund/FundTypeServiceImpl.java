package com.fen.fund.service.impl.fund;

import com.fen.fund.mapper.fund.FundTypeMapper;
import com.fen.fund.model.common.CacheKey;
import com.fen.fund.model.enums.ExpireEnum;
import com.fen.fund.model.fund.FundType;
import com.fen.fund.service.inte.fund.FundTypeService;
import com.fen.fund.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基金类型业务实现类
 * @author Fei
 * @date 2020-01-09
 */
@Service
public class FundTypeServiceImpl extends BaseServiceImpl<FundTypeMapper, FundType> implements FundTypeService {

    @Override
    public List<FundType> getTypes() {
        List<FundType> types = mapper.getAll(new FundType(), "id");
        cacheTool.setList(CacheKey.CK_FUND_TYPE, types, ExpireEnum.DAY_1.getCode());
        return types;
    }

}
