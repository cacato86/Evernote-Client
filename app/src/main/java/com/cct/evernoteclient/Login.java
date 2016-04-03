package com.cct.evernoteclient;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.cct.evernoteclient.Domains.TaskRepositoryFactory;
import com.evernote.client.android.EvernoteSession;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class Login extends Activity implements DialogInterface.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticateInEvernote();
    }

    private void authenticateInEvernote() {
        new TaskRepositoryFactory().getRepository().login(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EvernoteSession.REQUEST_CODE_LOGIN:
                if (resultCode == Activity.RESULT_OK) {
                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                } else {
                    createAdvisorDialog().show();
                }
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
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
