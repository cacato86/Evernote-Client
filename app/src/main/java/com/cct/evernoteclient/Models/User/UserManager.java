package com.cct.evernoteclient.Models.User;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public class UserManager<T> {
    public User getUser(T user) {
        return new UserEvernoteConverter().convertEspecificUserToMyUser(user);
    }
}
