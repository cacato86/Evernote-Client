package com.cct.evernoteclient.domain;

import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public interface TaskRepositoryFactoryInterface {
    void login(TaskResultInterface<Boolean> taskResult);

    void getNotes(TaskResultInterface<ArrayList<Note>> taskResult);

    void getNoteBooks(TaskResultInterface<ArrayList<Notebook>> taskResult);

    void getNoteDetail(TaskResultInterface<Note> taskResult);

    void createNote(TaskResultInterface<Note> taskResult);
}
