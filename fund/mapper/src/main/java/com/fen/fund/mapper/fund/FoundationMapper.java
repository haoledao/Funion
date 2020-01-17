package com.fen.fund.mapper.fund;

import com.fen.fund.mapper.base.IBaseMapper;
import com.fen.fund.model.fund.Foundation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 基金Mapper
 * @author Fei
 * @date 2020-01-09
 */
@Mapper
public interface FoundationMapper extends IBaseMapper<Foundation> {

    /**
     * 获取基金列表
     * @param foundation Foundation
     * @return 基金列表
     */
    List<Foundation> getFoundations(Foundation foundation);

}
