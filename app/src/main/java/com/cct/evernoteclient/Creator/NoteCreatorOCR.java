package com.cct.evernoteclient.Creator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.R;
import com.cct.evernoteclient.Utils;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.IOException;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public class NoteCreatorOCR implements NoteCreatorInterface {

    private final Activity activity;

    public NoteCreatorOCR(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void createNote(TaskResultInterface<Note> callback) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap icon = BitmapFactory.decodeResource(activity.getResources(), R.drawable.c, options);
        String letter = Utils.detectText(icon);
    }

}
