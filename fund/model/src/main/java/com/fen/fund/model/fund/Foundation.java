package com.fen.fund.model.fund;

import com.fen.fund.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金表
 * @author Fei
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foundation extends BaseModel {

    /** 代码 */
    private String fundCode;

    /** 名称 */
    private String fundName;

    /** 基金类型 */
    private Integer typeId;

    /** 基金公司 */
    private Integer companyId;

    /** 风险等级 dict(risk_level) */
    private String riskLevel;

    /** 基金净值 */
    private BigDecimal fundNet;

    /** 基金涨幅 */
    private BigDecimal fundRise;

    /** 持有份额 */
    private BigDecimal share;

    /** 持有金额 */
    private BigDecimal amount;

    /** 持仓成本 */
    private BigDecimal cost;

    /** 收益率 */
    private BigDecimal incomeRate;

    /** 创建时间 */
    private Date createTime;

    /** 盈利状态: ProfitStatusEnum */
    private String profitStatus;

    /** 状态: FundStatusEnum */
    private String status;

}
