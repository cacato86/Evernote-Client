package com.cct.evernoteclient;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.cct.evernoteclient.Domain.ErrorManager;
import com.googlecode.tesseract.android.TessBaseAPI;

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

    public static String detectText(Bitmap bitmap) {
        Log.e("TAM1",bitmap.getHeight()+" /");

        TessBaseAPI.ProgressNotifier progres = new TessBaseAPI.ProgressNotifier() {
            @Override
            public void onProgressValues(TessBaseAPI.ProgressValues progressValues) {
                Log.e("PROGRE",progressValues.getPercent()+" /");
            }
        };
        TessBaseAPI baseApi = new TessBaseAPI(progres);
        baseApi.setDebug(true);
        baseApi.init(Environment.getExternalStorageDirectory()+"/", "eng");

        //baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_CHAR);
        baseApi.setImage(bitmap);
        String recognizedText = baseApi.getUTF8Text();
        Log.e("Texto leido", "texto: "+recognizedText);
        baseApi.end();
        return recognizedText;
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.drawColor(Color.WHITE);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }
}
