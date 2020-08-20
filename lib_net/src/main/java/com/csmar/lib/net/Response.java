package com.csmar.lib.net;

/**
 * 响应体
 *
 * @param <T> 泛型bean
 * @author wsd
 * @since 2020-07-07
 */
public class Response<T> {
    private String code; // 返回的code
    private T data; // 具体的数据结果
    private String msg; // message 可用来返回接口的说明

    public String getCode() {
        return code;
    }

    public void setCode(String ret) {
        this.code = ret;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return "success".equalsIgnoreCase(code);
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
