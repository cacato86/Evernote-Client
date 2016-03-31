package com.cct.evernoteclient.View.NoteViewManager;

/**
 * Created by carloscarrasco on 31/3/16.
 */
public class NoteRepresentationFactory {
    public NoteRepresentationInterface getNoteRepresentation() {
        return new NoteRepresentationHtml();
    }
}
