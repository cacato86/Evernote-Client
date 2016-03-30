package com.cct.evernoteclient.Domain;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public interface TaskResultInterface<T> {
    void onSucces(T result);
    void onError(ErrorManager error);
}
