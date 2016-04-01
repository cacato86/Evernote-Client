package com.cct.evernoteclient.Models.Note;

import com.evernote.edam.type.NoteAttributes;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public class NoteEvernoteConverter<T> implements NoteConverterInterface {

    @Override
    public Note convertEspecificNoteToMyNote(Object note) {
        Note myNote = new Note();
        com.evernote.edam.type.Note noteEvernote = (com.evernote.edam.type.Note) note;
        myNote.setTitle(noteEvernote.getTitle());
        myNote.setContent(noteEvernote.getContent());
        myNote.setId(noteEvernote.getGuid());
        myNote.setAuthor(noteEvernote.getAttributes().getAuthor());
        myNote.setCreated(noteEvernote.getCreated());
        myNote.setUpdate(noteEvernote.getUpdated());
        return myNote;
    }


    @Override
    public T convertMyNoteToEspecificNote(Note note) {
        com.evernote.edam.type.Note noteEvernote = new com.evernote.edam.type.Note();
        noteEvernote.setTitle(note.getTitle());
        noteEvernote.setContent(note.getContent());
        noteEvernote.setGuid(note.getId());
        NoteAttributes attributes = new NoteAttributes();
        attributes.setAuthor(note.getAuthor());
        noteEvernote.setAttributes(attributes);
        noteEvernote.setCreated(note.getCreated());
        noteEvernote.setUpdated(noteEvernote.getUpdated());
        return (T) noteEvernote;
    }
}
