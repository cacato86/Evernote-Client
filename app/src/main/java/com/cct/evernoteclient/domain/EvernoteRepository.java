package com.cct.evernoteclient.domain;

import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.asyncclient.EvernoteNoteStoreClient;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public class EvernoteRepository implements TaskRepositoryFactoryInterface {

    private String notLoggedError = "You should register in Evernote for use this app";

    private ErrorManager generateError(String errorMessage) {
        ErrorManager error = new ErrorManager();
        error.setReason(errorMessage);
        return error;
    }

    private boolean isLogged() {
        return EvernoteSession.getInstance().isLoggedIn();
    }

    @Override
    public void login(TaskResultInterface<Boolean> taskResult) {

    }

    @Override
    public void getNotes(final TaskResultInterface<ArrayList<Note>> taskResult) {
        /*if (isLogged()) {
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            noteStoreClient.listNotebooksAsync(new EvernoteCallback<List<Notebook>>() {
                @Override
                public void onSuccess(List<Notebook> result) {
                    taskResult.onSucces(result);
                }

                @Override
                public void onException(Exception exception) {
                    taskResult.onError(generateError("Error retrieving notebooks"));
                }
            });
        } else {
            taskResult.onError(generateError(notLoggedError));
        }*/
    }

    @Override
    public void getNoteBooks(final TaskResultInterface<ArrayList<Notebook>> taskResult) {
        if (isLogged()) {
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            noteStoreClient.listNotebooksAsync(new EvernoteCallback<List<Notebook>>() {
                @Override
                public void onSuccess(List<Notebook> result) {
                    taskResult.onSucces((ArrayList<Notebook>) result);
                }

                @Override
                public void onException(Exception exception) {
                    taskResult.onError(generateError("Error retrieving notebooks"));
                }
            });
        } else {
            taskResult.onError(generateError(notLoggedError));
        }
    }

    @Override
    public void getNoteDetail(TaskResultInterface<Note> taskResult) {

    }

    @Override
    public void createNote(TaskResultInterface<Note> taskResult) {

    }
}
