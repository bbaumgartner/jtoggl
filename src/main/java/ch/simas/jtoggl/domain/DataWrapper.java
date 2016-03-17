package ch.simas.jtoggl.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by hpa on 17.3.16.
 */
public class DataWrapper<T> {

    @JsonProperty("data")
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
