package com.cct.evernoteclient.Models.User;

/**
 * Created by carloscarrasco on 1/4/16.
 */
public class UserEvernoteConverter implements UserConverterInterface {
    @Override
    public User convertEspecificUserToMyUser(Object user) {
        User myUser = new User();
        com.evernote.edam.type.User userEvernote = (com.evernote.edam.type.User) user;
        myUser.setId(userEvernote.getId());
        myUser.setName(userEvernote.getName());
        myUser.setUsername(userEvernote.getUsername());
        myUser.setEmail(userEvernote.getEmail());
        return myUser;
    }
}
