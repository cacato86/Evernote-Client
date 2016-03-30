package com.cct.evernoteclient.View.ViewModel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.evernote.edam.type.Note;

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

    public void onItemClick(View view) {
        /*Intent intent = new Intent(view.getContext(), Note.class);
        intent.putExtra("house", house);
        view.getContext().startActivity(intent);*/
    }

    public String getTitle() {
        return note.getTitle();
    }


    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        //ImageManager imageManager = new ImageManager(view.getContext());
        //imageManager.getDowloaderImageTask().setImageUrlIntoImageView(imageUrl, view);

    }
}
