package ch.simas.jtoggl.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response wrapper to help handle different root elements for request and response. For response it is always "data"
 * so we disable root element unwrapping and use this wrapper to dig requested object.
 */
public class ResponseDataWrapper<T> {

    @JsonProperty("data")
    private T data;

    /**
     *
     * @return Object requested from Toggl API.
     */
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
