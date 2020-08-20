package com.csmar.lib.net;

public class ApiException extends Exception{
    private int code;
    private String errMessage;
    private String showMessage;

    public ApiException(int code, String errMessage, String showMessage) {
        this.code = code;
        this.errMessage = errMessage;
        this.showMessage = showMessage;
    }

    public String getShowMessage() {
        return showMessage;
    }

    public void setShowMessage(String showMessage) {
        this.showMessage = showMessage;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
