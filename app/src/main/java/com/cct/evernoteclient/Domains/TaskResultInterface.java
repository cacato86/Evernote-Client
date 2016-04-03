package com.cct.evernoteclient.Domains;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public interface TaskResultInterface<T> {
    void onSucces(T result);
    void onError(ErrorManager error);
}
