package com.fen.fund.service.inte.fund;

import com.fen.fund.model.fund.Foundation;
import com.fen.fund.service.support.BaseService;

import java.util.List;

/**
 * 基金业务接口
 * @author Fei
 * @date 2020-01-09
 */
public interface FoundationService extends BaseService<Foundation> {

    /**
     * 获取基金列表
     * @param foundation 基金
     * @return 基金列表
     */
    List<Foundation> getFoundations(Foundation foundation);

    /**
     * 新增基金
     * @param foundation Foundation
     * @return 影响行数
     */
    int addFoundation(Foundation foundation);

}
