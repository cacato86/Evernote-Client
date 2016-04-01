package com.cct.evernoteclient.View.NoteViewManager;

import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.Utils;
import com.evernote.client.android.EvernoteSession;
import com.evernote.client.android.asyncclient.EvernoteCallback;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public class NoteRepresentationHtml implements NoteRepresentationInterface {

    @Override
    public void getNoteDataForRepresentation(Note note, final TaskResultInterface callbackResult) {
        try {
            EvernoteSession.getInstance().getEvernoteClientFactory().getHtmlHelperDefault().downloadNoteAsync(note.getId(), new EvernoteCallback<Response>() {
                @Override
                public void onSuccess(final Response result) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                String data = "<html><head></head><body>" + result.body().string() + "</body></html>";
                                callbackResult.onSucces(data);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                @Override
                public void onException(Exception exception) {
                    callbackResult.onError(Utils.generateError(exception.toString()));
                }
            });
        } catch (IOException e) {
            callbackResult.onError(Utils.generateError(e.getMessage()));
        }
    }
}
