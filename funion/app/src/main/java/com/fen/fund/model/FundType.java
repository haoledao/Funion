package com.fen.fund.model;

import com.fen.db.annotation.Table;

import lombok.Data;

@Data
@Table(name = "fund_type")
public class FundType extends BaseModel {

    /** 类型名称 */
    private String typeName;
    /** 类型描述 */
    private String typeMemo;

}
