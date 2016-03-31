package com.cct.evernoteclient.Domain;

import android.app.Activity;
import android.util.Log;

import com.cct.evernoteclient.Models.Filter;
import com.cct.evernoteclient.Models.Filter.FilterBuilder.FilterOrder;
import com.cct.evernoteclient.Models.Filter.FilterBuilder.FilterParameters;
import com.cct.evernoteclient.Utils;
import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.asyncclient.EvernoteNoteStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by carloscarrasco on 30/3/16.
 */
public class EvernoteRepository implements TaskRepositoryFactoryInterface {

    private String notLoggedError = "You should register in Evernote for use this app";
    private int MAX_NOTES = 100;

    private boolean isLogged() {
        return EvernoteSession.getInstance().isLoggedIn();
    }

    @Override
    public void login(Activity activity) {
        EvernoteSession.getInstance().authenticate(activity);
    }

    @Override
    public void getNotes(Filter filter, final TaskResultInterface<ArrayList<Note>> taskResult) {
        if (isLogged()) {
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            NoteFilter noteFilter = castFilterToEvernoteFilter(filter);
            noteStoreClient.findNotesAsync(noteFilter, 0, MAX_NOTES, new EvernoteCallback<NoteList>() {
                @Override
                public void onSuccess(NoteList result) {
                    taskResult.onSucces((ArrayList<Note>) result.getNotes());
                }

                @Override
                public void onException(Exception exception) {
                    taskResult.onError(Utils.generateError(exception.toString()));
                }
            });
        } else {
            taskResult.onError(Utils.generateError(notLoggedError));
        }
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
                    taskResult.onError(Utils.generateError(exception.toString()));
                }
            });
        } else {
            taskResult.onError(Utils.generateError(notLoggedError));
        }
    }

    @Override
    public void getNoteDetail(Note note, final TaskResultInterface<Note> taskResult) {
        if (isLogged()) {
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            noteStoreClient.getNoteAsync(note.getGuid(), true, true, true, true, new EvernoteCallback<Note>() {
                @Override
                public void onSuccess(Note result) {
                    taskResult.onSucces(result);
                }

                @Override
                public void onException(Exception exception) {
                    taskResult.onError(Utils.generateError(exception.toString()));
                }
            });
        } else {
            taskResult.onError(Utils.generateError(notLoggedError));
        }
    }

    @Override
    public void createNote(Note note, final TaskResultInterface<Note> taskResult) {
        if (isLogged()) {
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            noteStoreClient.createNoteAsync(note, new EvernoteCallback<Note>() {
                @Override
                public void onSuccess(Note result) {
                    Log.e("NOTECREATED0",result.toString());
                    taskResult.onSucces(result);
                }

                @Override
                public void onException(Exception exception) {
                    taskResult.onError(Utils.generateError(exception.toString()));
                }
            });
        } else {
            taskResult.onError(Utils.generateError(notLoggedError));
        }

    }

    private NoteFilter castFilterToEvernoteFilter(Filter filter) {
        NoteFilter noteFilter = new NoteFilter();
        if (filter.getFilterParams() == FilterParameters.CREATION) {
            noteFilter.setOrder(NoteSortOrder.CREATED.getValue());
        } else if (filter.getFilterParams() == FilterParameters.TITLE) {
            noteFilter.setOrder(NoteSortOrder.TITLE.getValue());
        }

        if (filter.getFilterOrder() == FilterOrder.ASCENDING) {
            noteFilter.setAscending(true);
        } else if (filter.getFilterOrder() == FilterOrder.DESCENDING) {
            noteFilter.setAscending(false);
        }

        return noteFilter;
    }
}
