package com.fen.fund.model.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fen.fund.model.enums.CodeEnum;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一返回格式
 * @author Fei
 * @date 2020-01-09
 */
@NoArgsConstructor
public class ApiData implements Serializable {

    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = 1;

    /**
     * 0-正确, 1-错误
     */
    private Integer error;
    private String code;
    private String message;
    private Object data;
    /**
     * 扩展属性
     */
    private Object extend;
    @JsonIgnore
    private CodeEnum codeEnum;

    public static ApiData getInstance() {
        return new ApiData();
    }

    public ApiData(Object data) {
        this(SUCCESS, data);
    }

    public ApiData(Integer flag) {
        this.error = flag;
    }

    public ApiData(Integer error, Object data) {
        this.error = error;
        this.data = data;
    }

    public ApiData(CodeEnum codeEnum) {
        this(FAIL, codeEnum);
    }

    public ApiData(Integer error, CodeEnum codeEnum) {
        this.error = error;
        this.code = codeEnum.getCode();
        this.message = codeEnum.getMemo();
    }

    public static boolean isFail(ApiData apiJson) {
        return ApiData.FAIL.equals(apiJson.getError());
    }

    public Integer getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    /** 返回成功时调用 */
    public void setData(Object data) {
        this.error = SUCCESS;
        this.data = data;
    }

    public Object getExtend() {
        return extend;
    }

    public void setExtend(Object extend) {
        this.extend = extend;
    }

    public CodeEnum getCodeEnum() {
        return codeEnum;
    }

    /** 返回失败时调用 */
    public void setCodeEnum(CodeEnum codeEnum) {
        this.error = FAIL;
        this.codeEnum = codeEnum;
    }

}
