package com.cct.evernoteclient.View.Adapters;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.R;
import com.cct.evernoteclient.View.CustomView.RecycleViewAnimate;
import com.cct.evernoteclient.View.ViewModel.NoteViewModel;
import com.cct.evernoteclient.databinding.NoteBinding;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public class NoteAdapter extends RecycleViewAnimate {

    private final Activity activity;

    public NoteAdapter(Activity activity) {
        super();
        this.activity = activity;
    }

    @Override
    public NoteBindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NoteBinding noteBinding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.note,
                parent,
                false);
        return new NoteBindingHolder(noteBinding);
    }

    @Override
    public void onBindViewHolder(NoteBindingHolder holder, int position) {
        holder.bindCharacter(notesArray.get(position));
    }

    @Override
    public int getItemCount() {
        return notesArray.size();
    }

    public static class NoteBindingHolder extends RecyclerView.ViewHolder {
        final NoteBinding binding;

        public NoteBindingHolder(NoteBinding binding) {
            super(binding.cv);
            this.binding = binding;
        }

        void bindCharacter(Note note) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new NoteViewModel(note));
            } else {
                binding.getViewModel().setNote(note);
            }
        }
    }
}
