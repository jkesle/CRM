package jkesle.crm.server.service;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    private List<String> errorMessages = new ArrayList<>();
    private T payload;

    public Result(){}

    public boolean isSuccessful() {
        return errorMessages.size() == 0;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public T getPayload() {
        return payload;
    }

    public void addError(String message) {
        errorMessages.add(message);
    }

    public List<String> getErrors() {
        return errorMessages;
    }
}
