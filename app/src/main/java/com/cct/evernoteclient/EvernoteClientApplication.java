package com.cct.evernoteclient;

import android.app.Application;

import com.evernote.client.android.EvernoteSession;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class EvernoteClientApplication extends Application {
    private static final String CONSUMER_KEY = "cacato86";
    private static final String CONSUMER_SECRET = "11ab48bdc45a1b54";
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;
    private static final boolean SUPPORT_APP_LINKED_NOTEBOOKS = false;

    @Override
    public void onCreate() {
        super.onCreate();
        new EvernoteSession.Builder(this)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(SUPPORT_APP_LINKED_NOTEBOOKS)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();
    }
}
