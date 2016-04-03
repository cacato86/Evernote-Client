package com.cct.evernoteclient.Domains;

import android.app.Activity;
import android.app.Application;

import com.cct.evernoteclient.Models.Filter;
import com.cct.evernoteclient.Models.Filter.FilterBuilder.FilterOrder;
import com.cct.evernoteclient.Models.Filter.FilterBuilder.FilterParameters;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.Models.Note.NoteManager;
import com.cct.evernoteclient.Models.User.User;
import com.cct.evernoteclient.Models.User.UserManager;
import com.cct.evernoteclient.Utils;
import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.evernote.client.android.asyncclient.EvernoteNoteStoreClient;
import com.evernote.client.android.asyncclient.EvernoteUserStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteList;
import com.evernote.edam.type.NoteSortOrder;

import java.util.ArrayList;


/**
 * Created by carloscarrasco on 30/3/16.
 */
public class EvernoteRepository implements TaskRepositoryFactoryInterface {

    private static final String CONSUMER_KEY = "cacato86-4186";
    private static final String CONSUMER_SECRET = "efa4d57f3ba30bd0";
    private static final EvernoteSession.EvernoteService EVERNOTE_SERVICE = EvernoteSession.EvernoteService.SANDBOX;
    private static final boolean SUPPORT_APP_LINKED_NOTEBOOKS = true;

    private String notLoggedError = "You should register in Evernote for use this app";
    private int MAX_NOTES = 100;

    private boolean isLogged() {
        return EvernoteSession.getInstance().isLoggedIn();
    }

    @Override
    public void initializeSDK(Application app) {
        new EvernoteSession.Builder(app)
                .setEvernoteService(EVERNOTE_SERVICE)
                .setSupportAppLinkedNotebooks(SUPPORT_APP_LINKED_NOTEBOOKS)
                .build(CONSUMER_KEY, CONSUMER_SECRET)
                .asSingleton();
    }

    @Override
    public void login(Activity activity) {
        EvernoteSession.getInstance().authenticate(activity);
    }

    @Override
    public void logout() {
        EvernoteSession.getInstance().logOut();
    }

    @Override
    public void getNotes(Filter filter, final TaskResultInterface<ArrayList<Note>> taskResult) {
        if (isLogged()) {
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            NoteFilter noteFilter = castFilterToEvernoteFilter(filter);
            noteStoreClient.findNotesAsync(noteFilter, 0, MAX_NOTES, new EvernoteCallback<NoteList>() {
                @Override
                public void onSuccess(NoteList result) {
                    taskResult.onSucces(convertEvernoteArray(result));
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
            noteStoreClient.getNoteAsync(note.getId(), true, true, true, true, new EvernoteCallback<com.evernote.edam.type.Note>() {
                @Override
                public void onSuccess(com.evernote.edam.type.Note result) {
                    Note note = new NoteManager<com.evernote.edam.type.Note>().convertEspecificNoteToMyNote(result);
                    taskResult.onSucces(note);
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
            com.evernote.edam.type.Note noteEvernote = new NoteManager<com.evernote.edam.type.Note>().convertMyNoteToEspecificNote(note);
            EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
            noteStoreClient.createNoteAsync(noteEvernote, new EvernoteCallback<com.evernote.edam.type.Note>() {
                @Override
                public void onSuccess(com.evernote.edam.type.Note result) {
                    Note note = new NoteManager<com.evernote.edam.type.Note>().convertEspecificNoteToMyNote(result);
                    taskResult.onSucces(note);
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
    public void getUser(final TaskResultInterface<User> taskResult) {
        if (isLogged()) {
            EvernoteUserStoreClient userStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getUserStoreClient();
            userStoreClient.getUserAsync(new EvernoteCallback<com.evernote.edam.type.User>() {
                @Override
                public void onSuccess(com.evernote.edam.type.User result) {
                    User user = new UserManager<com.evernote.edam.type.User>().getUser(result);
                    taskResult.onSucces(user);
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

    private ArrayList<Note> convertEvernoteArray(NoteList list) {
        ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < list.getNotes().size(); i++) {
            com.evernote.edam.type.Note noteEvernote = list.getNotes().get(i);
            Note note = new NoteManager<com.evernote.edam.type.Note>().convertEspecificNoteToMyNote(noteEvernote);
            notes.add(note);
        }
        return notes;
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
