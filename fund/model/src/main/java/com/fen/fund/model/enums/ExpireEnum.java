package com.fen.fund.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 缓存过期时间枚举
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExpireEnum implements IEnum {
    //
    SECOND_10("10", "10秒"),
    SECOND_60("60", "60秒"),
    MINUTE_5("300", "5分钟"),
    DAY_1("86400", "1天"),
    DAY_30("2592000", "30天");

    private String code;
    private String memo;
}
