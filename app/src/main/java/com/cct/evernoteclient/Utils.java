package com.cct.evernoteclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.cct.evernoteclient.Domain.ErrorManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    public static File getFile(Activity activity) throws IOException {
        AssetManager am = activity.getAssets();
        InputStream inputStream = am.open("a");
        return createFileFromInputStream(inputStream);
    }

    private static File createFileFromInputStream(InputStream inputStream) {

        try {
            File f = new File("file");
            OutputStream outputStream = new FileOutputStream(f);
            byte buffer[] = new byte[1024];
            int length = 0;

            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        } catch (IOException e) {
            //Logging exception
        }

        return null;
    }


}
