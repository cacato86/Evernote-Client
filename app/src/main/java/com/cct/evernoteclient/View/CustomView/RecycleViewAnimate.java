package com.cct.evernoteclient.View.CustomView;

/**
 * Created by Carlos Carrasco Torres on 30/03/2016.
 */

import android.support.v7.widget.RecyclerView;

import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.View.Adapters.NoteAdapter;

import java.util.ArrayList;

public abstract class RecycleViewAnimate extends RecyclerView.Adapter<NoteAdapter.NoteBindingHolder> {
    public ArrayList<Note> notesArray = new ArrayList<>();

    public void setNoteArray(ArrayList<Note> notes) {
        notesArray = new ArrayList<>(notes);
        notifyDataSetChanged();
    }

    public void animateTo(ArrayList<Note> models) {
        applyAndAnimateRemovals(models);
        applyAndAnimateAdditions(models);
        applyAndAnimateMovedItems(models);
    }

    private void applyAndAnimateRemovals(ArrayList<Note> newModels) {
        for (int i = notesArray.size() - 1; i >= 0; i--) {
            final Note model = notesArray.get(i);
            if (!newModels.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(ArrayList<Note> newModels) {
        for (int i = 0, count = newModels.size(); i < count; i++) {
            final Note model = newModels.get(i);
            if (!notesArray.contains(model)) {
                addItem(i, model);
            }
        }
    }

    private void applyAndAnimateMovedItems(ArrayList<Note> newModels) {
        for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
            final Note model = newModels.get(toPosition);
            final int fromPosition = notesArray.indexOf(model);
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition);
            }
        }
    }

    public Note removeItem(int position) {
        final Note model = notesArray.remove(position);
        notifyItemRemoved(position);
        return model;
    }

    public void addItem(int position, Note model) {
        notesArray.add(position, model);
        notifyItemInserted(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        final Note model = notesArray.remove(fromPosition);
        notesArray.add(toPosition, model);
        notifyItemMoved(fromPosition, toPosition);
    }
}