package com.cct.evernoteclient.Domain;

import com.cct.evernoteclient.Models.Filter;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public interface TaskRepositoryFactoryInterface {
    void login(TaskResultInterface<Boolean> taskResult);

    void getNotes(Filter filter, TaskResultInterface<ArrayList<Note>> taskResult);

    void getNoteBooks(TaskResultInterface<ArrayList<Notebook>> taskResult);

    void getNoteDetail(TaskResultInterface<Note> taskResult);

    void createNote(Note note, TaskResultInterface<Note> taskResult);
}
