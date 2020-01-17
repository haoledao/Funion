package com.fen.fund.model.base;

import com.fen.fund.tool.annotation.PrimaryKey;
import lombok.Data;

import java.io.Serializable;

/**
 * 模型基类
 * @author Fei
 * @date 2020-01-09
 */
@Data
public class BaseModel implements Serializable {

    @PrimaryKey
    public Integer id;

}