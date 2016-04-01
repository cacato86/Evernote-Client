package com.cct.evernoteclient.View.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cct.evernoteclient.Domain.ErrorManager;
import com.cct.evernoteclient.Domain.TaskRepositoryFactory;
import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.cct.evernoteclient.View.NoteDetailHtml;
import com.cct.evernoteclient.View.NoteViewManager.NoteRepresentationFactory;
import com.evernote.edam.type.Note;

import java.util.Date;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public class NoteViewModel extends BaseObservable {
    private Note note;

    public NoteViewModel(Note note) {
        this.note = note;
    }

    public void setNote(Note note) {
        this.note = note;
        notifyChange();
    }

    public void onItemClick(final View view) {
        final Context context = view.getContext();
        new NoteRepresentationFactory().getNoteRepresentation().getNoteDataForRepresentation(note, new TaskResultInterface<String>() {
            @Override
            public void onSucces(String result) {
                Intent intent = new Intent(context, NoteDetailHtml.class);
                intent.putExtra("note_html", result);
                intent.putExtra("title", note.getTitle());
                context.startActivity(intent);
            }

            @Override
            public void onError(ErrorManager error) {
                Toast.makeText(context, error.getReason(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getTitle() {
        return note.getTitle();
    }

    public String getAuthor() {
        String author = (note.getAttributes() != null && note.getAttributes().getAuthor() != null) ? note.getAttributes().getAuthor() : "Unknown";
        return author;
    }

    public String getUpdate() {
        long timeStampServer = note.getUpdated();
        if (timeStampServer > 0) {
            long timeStampNow = new Date().getTime();

            CharSequence realTime = DateUtils.getRelativeTimeSpanString(
                    timeStampServer,
                    timeStampNow,
                    DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);

            return realTime.toString();
        }
        return "";
    }

    public Note getNote() {
        return note;
    }

    @BindingAdapter({"loadBitmap"})
    public static void loadBitmap(final ImageView view, final Note noteMethod) {
        /*final EvernoteNoteStoreClient noteStoreClient = EvernoteSession.getInstance().getEvernoteClientFactory().getNoteStoreClient();
        noteStoreClient.getNoteAsync(noteMethod.getGuid(), true, true, true, true, new EvernoteCallback<Note>() {
            @Override
            public void onSuccess(Note result) {
                Iterator<Resource> iterator = result.getResourcesIterator();
                Resource resource = iterator.next();
                Log.e("LOAD1", resource.toString());

                Glide.with(view.getContext()).load(resource.getData().getBody()).into(view);

                //Bitmap bitmap = BitmapFactory.decodeByteArray(resource.getData().getBody(), 0, resource.getData().getSize());
                //view.setImageBitmap(bitmap);
            }

            @Override
            public void onException(Exception exception) {
                exception.printStackTrace();
            }
        });*/
    }
}
