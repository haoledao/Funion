package com.fen.fund.service.inte.sys;

import com.fen.fund.model.sys.Dict;
import com.fen.fund.service.support.BaseService;

import java.util.List;

/**
 * 字典业务接口
 * @author Fei
 * @date 2020-01-09
 */
public interface DictService extends BaseService<Dict> {

    /**
     * 获取字典数据列表
     * @return 数据列表
     */
    List<Dict> getDictList();

}
