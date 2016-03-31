package com.cct.evernoteclient.View.NoteViewManager;

import com.cct.evernoteclient.Domain.TaskResultInterface;
import com.evernote.edam.type.Note;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public interface NoteRepresentationInterface<T> {
    void getNoteDataForRepresentation(Note note, TaskResultInterface<T> callbackResult);
}
