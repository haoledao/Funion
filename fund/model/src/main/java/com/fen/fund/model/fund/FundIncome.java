package com.fen.fund.model.fund;

import com.fen.fund.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金收益表
 * @author Fei
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundIncome extends BaseModel {

    /** 基金ID */
    private Integer fundId;
    /** 收益日期 */
    private Date incomeDate;
    /** 盈亏金额 */
    private BigDecimal amount;
    /** 日增长值 */
    private BigDecimal growValue;
    /** 日增长率 */
    private BigDecimal growRate;

}
