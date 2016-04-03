package com.cct.evernoteclient.Creator;

import com.cct.evernoteclient.Domains.TaskResultInterface;
import com.cct.evernoteclient.Models.Note.Note;

/**
 * Created by Carlos Carrasco Torres on 31/03/2016.
 */
public interface NoteCreatorInterface {
    void createNote(TaskResultInterface<Note> callback);
}
