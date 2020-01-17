package com.fen.fund.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 基金状态枚举
 * @author Fei
 * @date 2020-01-09
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FundStatusEnum implements IEnum {

    //
    ON("on", "上架"),
    OFF("off", "下架");

    private String code;
    private String memo;

}
