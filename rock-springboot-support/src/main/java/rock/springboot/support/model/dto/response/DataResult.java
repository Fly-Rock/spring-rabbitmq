package com.oaup.support.model.dto.response;

import lombok.Data;

import java.util.Date;

/**
 * Created by lichuanjie on 2018/4/22.
 */
@Data
public class DataResult<T> {
    private int status;
    private String message;
    private T data;
    private Date time;

    public DataResult() {
    }

    public DataResult(int status, String message, T data) {
        this.data = data;
        this.status = status;
        this.message = message;
        this.setTime(new Date());
    }
    public static <V> DataResult<V> ok(Integer status, String message, V data) {
        DataResult<V> result = new DataResult();
        result.setStatus(status.intValue());
        result.setMessage(message);
        result.setData(data);
        result.setTime(new Date());
        return result;
    }
}
