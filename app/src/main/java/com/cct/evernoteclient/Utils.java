package com.cct.evernoteclient;

import com.cct.evernoteclient.Domain.ErrorManager;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public class Utils {

    public static ErrorManager generateError(String errorMessage) {
        ErrorManager error = new ErrorManager();
        errorMessage = (errorMessage != null) ? errorMessage : "";
        error.setReason(errorMessage);
        return error;
    }
}
