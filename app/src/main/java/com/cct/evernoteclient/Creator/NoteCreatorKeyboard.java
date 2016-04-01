package com.cct.evernoteclient.Creator;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.cct.evernoteclient.Domain.ErrorManager;
import com.cct.evernoteclient.Domain.TaskRepositoryFactory;
import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.cct.evernoteclient.R;
import com.cct.evernoteclient.Utils;
import com.evernote.client.android.EvernoteUtil;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteAttributes;

import java.util.Date;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public class NoteCreatorKeyboard implements NoteCreatorInterface {

    private Activity activity;
    private long actualTimeStamp = new Date().getTime();
    private TaskResultInterface<Note> callback;

    public NoteCreatorKeyboard(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void createNote(final TaskResultInterface<Note> callback) {
        this.callback = callback;
        createNoteDialog();
    }

    private void createNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        View view = inflater.inflate(R.layout.create_note_keyboard_dialog, null);

        final EditText title = (EditText) view.findViewById(R.id.title);
        final EditText content = (EditText) view.findViewById(R.id.content);

        builder.setView(view)
                .setPositiveButton(R.string.create_note, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        fillNote(title.getText().toString(), content.getText().toString());
                    }
                });
        builder.create().show();
    }

    private void fillNote(String title, String content) {
        final Note note = new Note();
        note.setTitle(title);
        note.setContent(EvernoteUtil.NOTE_PREFIX + content + EvernoteUtil.NOTE_SUFFIX);
        note.setUpdated(actualTimeStamp);
        note.setCreated(actualTimeStamp);
        NoteAttributes attributes = new NoteAttributes();
        new TaskRepositoryFactory().getRepository().createNote(note, new TaskResultInterface<Note>() {
            @Override
            public void onSucces(Note result) {
                callback.onSucces(result);
            }

            @Override
            public void onError(ErrorManager error) {
                callback.onError(error);
            }
        });
    }
}
