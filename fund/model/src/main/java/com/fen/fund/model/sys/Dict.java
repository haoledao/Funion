package com.fen.fund.model.sys;

import com.fen.fund.model.base.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 字典表
 * @author Fei
 * @date 2020-01-09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dict extends BaseModel {

    /** 模块 */
    private String module;

    /** 类型 */
    private String dictType;

    /** 代码 */
    private String dictCode;

    /** 描述 */
    private String dictMemo;

    /** 是否可用 */
    private Boolean active;

    /** 排序号 */
    private Integer orderNo;

}
