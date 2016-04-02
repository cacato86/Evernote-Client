package com.cct.evernoteclient;

import android.app.Application;

import com.cct.evernoteclient.Domain.TaskRepositoryFactory;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class EvernoteClientApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new TaskRepositoryFactory().getRepository().initializeSDK(this);

        Utils.copyFilesToSdCard(this);
    }
}
