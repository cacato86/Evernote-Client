package com.cct.evernoteclient.View.CustomView;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.cct.evernoteclient.R;

/**
 * Created by Carlos Carrasco Torres on 02/04/2016.
 */
public class InputOCR extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    @Override
    public View onCreateInputView() {
        Log.e("onCreateInputView", "onCreateInputView");
        RelativeLayout kv = (RelativeLayout) getLayoutInflater().inflate(R.layout.keyboard_ocr, null);
        return kv;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {

    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
