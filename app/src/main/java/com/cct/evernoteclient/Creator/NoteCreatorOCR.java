package com.cct.evernoteclient.Creator;

import android.app.Activity;
import android.util.Log;

import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.Utils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

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
        File imageFile = null;
        try {
            imageFile = Utils.getFile(activity);
            Log.e("IMAGE", imageFile.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //File imageFile = new File("eurotext.tif");
        ITesseract instance = new Tesseract();  // JNA Interface Mapping
        // ITesseract instance = new Tesseract1(); // JNA Direct Mapping

        try {
            String result = instance.doOCR(imageFile);
            Log.e("RESULT", result);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}
