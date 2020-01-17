package com.fen.fund.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 基金盈利状态枚举
 * @author Fei
 * @date 2020-01-09
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProfitStatusEnum implements IEnum {

    //
    STOP("stop", "停"),
    RISE("rise", "涨"),
    FALL("fall", "跌");

    private String code;
    private String memo;

}
