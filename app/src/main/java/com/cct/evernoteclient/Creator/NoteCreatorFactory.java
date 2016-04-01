package com.cct.evernoteclient.Creator;

import android.app.Activity;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public class NoteCreatorFactory {

    private final Activity activity;

    public enum TypeCreators {Keyboard, OCR}

    public NoteCreatorFactory(Activity activity) {
        this.activity = activity;
    }

    public NoteCreatorInterface getNoteCreator(TypeCreators type) {
        if (type == TypeCreators.OCR) {
            return new NoteCreatorOCR(activity);
        }
        return new NoteCreatorKeyboard(activity);
    }
}
