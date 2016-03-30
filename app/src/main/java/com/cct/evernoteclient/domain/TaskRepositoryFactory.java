package com.cct.evernoteclient.domain;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class TaskRepositoryFactory {

    public TaskRepositoryFactoryInterface getRepository(){
        return new EvernoteRepository();
    }
}
