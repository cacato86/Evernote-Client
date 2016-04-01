package com.cct.evernoteclient.Models.Note;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public class NoteManager<T> {
    public Note convertEspecificNoteToMyNote(T note) {
        return new NoteEvernoteConverter().convertEspecificNoteToMyNote(note);
    }

    public T convertMyNoteToEspecificNote(Note note){
        return (T) new NoteEvernoteConverter().convertMyNoteToEspecificNote(note);
    }
}
