package com.cct.evernoteclient.Models.Note;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public interface NoteConverterInterface<T> {
    Note convertEspecificNoteToMyNote(T note);

    T convertMyNoteToEspecificNote(Note note);
}
