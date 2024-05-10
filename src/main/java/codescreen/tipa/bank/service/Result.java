package codescreen.tipa.bank.service;

import codescreen.tipa.bank.model.Error;
import codescreen.tipa.bank.model.ResponseCode;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private ResponseCode type = ResponseCode.APPROVED;
    private T payload;
    private Error error = new Error();

    public ResponseCode getType() {
        return type;
    }

    public boolean isSuccess() {
        return type == ResponseCode.APPROVED;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public void setError(String message, String code, ResponseCode type){
        error.setMessage(message);
        error.setCode(code);
        this.type = type;
    }
    public Error getError(){
        return error;
    }
}
