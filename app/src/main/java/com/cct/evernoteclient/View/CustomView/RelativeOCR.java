package com.cct.evernoteclient.View.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cct.evernoteclient.R;

/**
 * Created by Carlos Carrasco Torres on 02/04/2016.
 */
public class RelativeOCR extends RelativeLayout {

    private EditTextOCR editOCR;

    public RelativeOCR(Context context) {
        super(context);
        init();
    }

    public RelativeOCR(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RelativeOCR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.relative_ocr, this);
        editOCR = (EditTextOCR) findViewById(R.id.edit_ocr);
        ImageView delete = (ImageView) findViewById(R.id.delete);
        delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editOCR.getSelectionStart() > 0){
                    editOCR.getText().delete(editOCR.getSelectionStart() - 1, editOCR.getSelectionStart());
                }
            }
        });
        Button space = (Button) findViewById(R.id.space);
        space.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                editOCR.getText().insert(editOCR.getSelectionStart(), " ");
            }
        });
    }

    public EditTextOCR getEditOCR(){
        return editOCR;
    }
}
