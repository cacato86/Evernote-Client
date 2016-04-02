package com.cct.evernoteclient.View.CustomView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cct.evernoteclient.R;
import com.cct.evernoteclient.Utils;

/**
 * Created by Carlos Carrasco Torres on 02/04/2016.
 */
public class EditTextOCR extends EditText {

    private Context context;
    private FingerPaintView dv;

    public EditTextOCR(Context context) {
        super(context);
        init(context);
    }

    public EditTextOCR(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EditTextOCR(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(final Context context) {
        Log.e("init", "init");
        this.context = context;

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDialog();
                }
                return true;
            }
        });
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.keyboard_ocr, null);

        dv = (FingerPaintView) view.findViewById(R.id.fingerView);
        ImageView del = (ImageView) view.findViewById(R.id.del);
        del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dv.clear();
            }
        });

        builder.setView(view)
                .setCancelable(false)
                .setPositiveButton(R.string.send, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        final AlertDialog dialog =  builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textOCR = doOCR(dv);
                EditTextOCR.this.getText().insert(EditTextOCR.this.getSelectionStart(), textOCR);
                if (textOCR.equals("")) {
                    Toast.makeText(context, context.getResources().getString(R.string.recognition_fail), Toast.LENGTH_LONG).show();
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    private String doOCR(FingerPaintView dv) {
        Bitmap bitmap = Utils.loadBitmapFromView(dv);
        return Utils.detectText(bitmap);
    }


}
