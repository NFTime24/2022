package com.nftime.app.util.asyncTasks;

public interface AsyncResponse<T> {
    void onAsyncSuccess(T result);
}
