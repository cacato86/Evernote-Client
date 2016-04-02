package com.cct.evernoteclient.Domain;

import android.app.Activity;

import com.cct.evernoteclient.Models.Filter;
import com.cct.evernoteclient.Models.Note.Note;
import com.cct.evernoteclient.Models.User.User;

import java.util.ArrayList;

/**
 * Created by carloscarrasco on 30/3/16.
 */
public interface TaskRepositoryFactoryInterface {
    void login(Activity activity);

    void logout();

    void getNotes(Filter filter, TaskResultInterface<ArrayList<Note>> taskResult);

    void getNoteDetail(Note note, TaskResultInterface<Note> taskResult);

    void createNote(Note note, TaskResultInterface<Note> taskResult);

    void getUser(TaskResultInterface<User> taskResult);
}
