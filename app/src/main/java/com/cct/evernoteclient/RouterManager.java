package com.cct.evernoteclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.evernote.client.android.EvernoteSession;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class RouterManager extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (EvernoteSession.getInstance().isLoggedIn()) {
            launchMainActivity();
        } else {
            launchLoginActivity();
        }
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void launchLoginActivity() {
        Intent intent = new Intent(this, Login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
