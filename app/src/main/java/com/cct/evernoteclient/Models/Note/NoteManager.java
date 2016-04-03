package com.cct.evernoteclient.Models.Note;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public class NoteManager<T> {
    public Note convertEspecificNoteToMyNote(T note) {
        return new NoteEvernoteConvert().convertEspecificNoteToMyNote(note);
    }

    public T convertMyNoteToEspecificNote(Note note){
        return (T) new NoteEvernoteConvert().convertMyNoteToEspecificNote(note);
    }
}
