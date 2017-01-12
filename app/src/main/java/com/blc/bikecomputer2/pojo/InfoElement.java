package com.blc.bikecomputer2.pojo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatDrawableManager;

/**
 * Created by Pablo on 12/01/2017.
 */

public class InfoElement {
    private String text;
    private int bitmapId;
    private Bitmap bitmap;
    private Context context;

    public InfoElement(String text, int bitmapId, Context context) {
        this.text = text;
        this.bitmapId = bitmapId;
        this.context = context;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getBitmapId() {
        return bitmapId;
    }

    public void setBitmapId(int bitmapId) {
        this.bitmapId = bitmapId;
        bitmap = null;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        if(bitmap == null) {
            Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, bitmapId);
            Canvas canvas = new Canvas();
            Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bmp);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            this.bitmap = bmp;
            return bmp;
        }
        else{
            return bitmap;
        }
    }
}
