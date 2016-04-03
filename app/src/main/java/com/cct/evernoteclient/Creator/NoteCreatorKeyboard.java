package com.cct.evernoteclient.Creator;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cct.evernoteclient.Domains.ErrorManager;
import com.cct.evernoteclient.Domains.TaskRepositoryFactory;
import com.cct.evernoteclient.Domains.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.R;
import com.cct.evernoteclient.Utils;
import com.evernote.client.android.EvernoteUtil;

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
                    }
                });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (title.getText().toString().trim().equals("") || content.getText().toString().trim().equals("")) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.write_something), Toast.LENGTH_LONG).show();
                } else {
                    fillNote(title.getText().toString(), content.getText().toString(), dialog);
                }
            }
        });
    }

    private void fillNote(String title, String content, final AlertDialog dialog) {
        final Note note = new Note();
        note.setTitle(title);
        note.setContent(EvernoteUtil.NOTE_PREFIX + content + EvernoteUtil.NOTE_SUFFIX);
        note.setUpdate(actualTimeStamp);
        note.setCreated(actualTimeStamp);
        note.setAuthor(Utils.getUserName(activity));
        new TaskRepositoryFactory().getRepository().createNote(note, new TaskResultInterface<Note>() {
            @Override
            public void onSucces(Note result) {
                callback.onSucces(result);
                dialog.dismiss();
            }

            @Override
            public void onError(ErrorManager error) {
                callback.onError(error);
            }
        });
    }
}
