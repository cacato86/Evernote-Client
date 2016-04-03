package com.cct.evernoteclient.View.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Toast;

import com.cct.evernoteclient.Domain.ErrorManager;
import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.View.NoteDetailHtml;
import com.cct.evernoteclient.View.NoteViewManager.NoteRepresentationFactory;

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
        Intent intent = new Intent(context, NoteDetailHtml.class);
        intent.putExtra("note", note);
        context.startActivity(intent);
    }

    public String getTitle() {
        return note.getTitle();
    }

    public String getAuthor() {
        String author = (note.getAuthor() != null) ? note.getAuthor() : "Unknown";
        return author;
    }

    public String getUpdate() {
        long timeStampServer = note.getUpdate();
        if (timeStampServer > 0) {
            long timeStampNow = new Date().getTime();

            CharSequence realTime = DateUtils.getRelativeTimeSpanString(
                    timeStampServer,
                    timeStampNow,
                    DateUtils.SECOND_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);

            String realTimeString = realTime.toString();

            return realTimeString.substring(0, 1).toUpperCase() + realTimeString.substring(1);
        }
        return "";
    }
}
