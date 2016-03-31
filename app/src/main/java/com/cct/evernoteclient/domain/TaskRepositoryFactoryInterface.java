package com.cct.evernoteclient.Domain;

import android.app.Activity;

import com.cct.evernoteclient.Models.Filter;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.Notebook;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public interface TaskRepositoryFactoryInterface {
    void login(Activity activity);

    void getNotes(Filter filter, TaskResultInterface<ArrayList<Note>> taskResult);

    void getNoteBooks(TaskResultInterface<ArrayList<Notebook>> taskResult);

    void getNoteDetail(Note note, TaskResultInterface<Note> taskResult);

    void createNote(Note note, TaskResultInterface<Note> taskResult);
}
