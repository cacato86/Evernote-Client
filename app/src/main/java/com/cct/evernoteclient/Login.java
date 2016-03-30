package com.cct.evernoteclient;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.login.EvernoteLoginFragment;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class Login extends FragmentActivity implements EvernoteLoginFragment.ResultCallback, DialogInterface.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticateInEvernote();
    }

    private void authenticateInEvernote() {
        EvernoteSession.getInstance().authenticate(this);
    }

    @Override
    public void onLoginFinished(boolean successful) {
        if (successful) {
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        } else {
            createAdvisorDialog().show();
        }
    }

    private AlertDialog createAdvisorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setMessage(R.string.should_register)
                .setTitle(R.string.atention)
                .setPositiveButton(getResources().getString(R.string.retry), this)
                .setNegativeButton(getResources().getString(R.string.exit), this)
                .setCancelable(false);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int position) {
        if (position == -1) {
            authenticateInEvernote();
        } else {
            finish();
        }
    }
}
