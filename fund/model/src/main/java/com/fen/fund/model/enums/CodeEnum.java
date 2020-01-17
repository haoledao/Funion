package com.fen.fund.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 错误代号枚举
 * @author Fei
 * @date 2020-01-09
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum CodeEnum implements IEnum {

    //
    OP000("OP000", "操作成功"),
    OP001("OP001", "操作失败"),

    // 服务异常
    CE000("CE000", "服务忙，请稍后再试"),
    CE001("CE001", "参数异常")
    ;

    private String code;
    private String memo;

}
