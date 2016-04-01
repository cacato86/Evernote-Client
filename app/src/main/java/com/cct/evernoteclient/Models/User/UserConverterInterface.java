package com.cct.evernoteclient.Models.User;

import com.cct.evernoteclient.Models.Note.Note;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public interface UserConverterInterface<T> {
    User convertEspecificUserToMyUser(T user);
}
