package com.fen.fund.model.fund;

import com.fen.fund.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 基金类型
 * @author Fei
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundType extends BaseModel {

    /** 类型名称 */
    private String typeName;
    /** 类型描述 */
    private String typeMemo;

}
