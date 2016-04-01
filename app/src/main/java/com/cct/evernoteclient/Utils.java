package com.cct.evernoteclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.cct.evernoteclient.Domain.ErrorManager;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public class Utils {

    public static ErrorManager generateError(String errorMessage) {
        ErrorManager error = new ErrorManager();
        errorMessage = (errorMessage != null) ? errorMessage : "";
        error.setReason(errorMessage);
        return error;
    }

    public static void saveUserName(Activity activity, String name) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userName", name);
        editor.commit();
    }

    public static String getUserName(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString("userName", "Unknown");
    }
}
