package com.cct.evernoteclient.Creator;

import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.evernote.edam.type.Note;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public interface NoteCreatorInterface {
    void createNote(TaskResultInterface<Note> callback);
}