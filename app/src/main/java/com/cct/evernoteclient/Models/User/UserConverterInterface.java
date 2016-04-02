package com.cct.evernoteclient.Models.User;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public interface UserConverterInterface<T> {
    User convertEspecificUserToMyUser(T user);
}
