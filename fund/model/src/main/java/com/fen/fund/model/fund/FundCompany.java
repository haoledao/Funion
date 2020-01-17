package com.fen.fund.model.fund;

import com.fen.fund.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 基金公司表
 * @author Fei
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundCompany extends BaseModel {

    /** 公司简称 */
    private String shortName;
    /** 公司名称 */
    private String companyName;
    /** 公司LOGO */
    private String companyLogo;
    /** 公司官网 */
    private String companyUrl;
    /** 公司简介 */
    private String companyMemo;
    /** 创建时间 */
    private Date createTime;

}
