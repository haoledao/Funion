package com.fen.fund.service.impl.sys;

import com.fen.fund.mapper.sys.DictMapper;
import com.fen.fund.model.common.CacheKey;
import com.fen.fund.model.enums.ExpireEnum;
import com.fen.fund.model.sys.Dict;
import com.fen.fund.service.inte.sys.DictService;
import com.fen.fund.service.support.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据业务实现类
 * @author Fei
 * @date 2020-01-09
 */
@Service
public class DictServiceImpl extends BaseServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public List<Dict> getDictList() {
        List<Dict> list = mapper.getAll(new Dict(), "dict_type", "order_no");
        cacheTool.setList(CacheKey.CK_DICT, list, ExpireEnum.DAY_30.getCode());
        return list;
    }

}
