package com.cct.evernoteclient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Carlos Carrasco Torres on 01/04/2016.
 */
public class FingerPaintExample extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.finger_paint);

        final FingerPaintView dv = (FingerPaintView) findViewById(R.id.fingerView);
        Button btn = (Button) findViewById(R.id.btn);
        final EditText edit = (EditText) findViewById(R.id.edit);
        final ImageView img1 = (ImageView)findViewById(R.id.img1);
        final ImageView img = (ImageView)findViewById(R.id.img);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAM",dv.getHeight()+" /");
                Bitmap bitmap = Utils.loadBitmapFromView(dv);
                Bitmap bmp = Bitmap.createScaledBitmap(bitmap, 600, 600, true);
                img1.setImageBitmap(bmp);
                edit.setText(Utils.detectText(bmp));

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = false;
                options.inDither = false;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.c, options);
                Utils.detectText(icon);
                img.setImageBitmap(icon);
            }
        });




    }
}
