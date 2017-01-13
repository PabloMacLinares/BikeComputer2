package com.blc.bikecomputer2.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 13/01/2017.
 */

public class LightIndicator extends View {

    public static final int AUTO = 0;
    public static final int ON = 1;
    public static final int OFF = 2;

    private int mode = AUTO;
    private boolean lightOn = true;

    private int foregroundColor = Color.CYAN;
    private int backgroundColor = Color.BLACK;
    private Paint sPaint;

    public LightIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        sPaint = new Paint();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode == AUTO) {
                    mode = ON;
                    setLightOn(true);
                }else if(mode == ON){
                    mode = OFF;
                    setLightOn(false);
                }else if(mode == OFF){
                    mode = AUTO;
                    refresh();
                }
            }
        });
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        refresh();
    }

    public boolean isLightOn() {
        return lightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.lightOn = lightOn;
        refresh();
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
        refresh();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        refresh();
    }

    public void refresh(){
        invalidate();
        requestLayout();
    }

    private float getRadius(int width, int height){
        return Math.min(width, height) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getMeasuredWidth();
        int heigth = this.getMeasuredHeight();

        drawBackground(width, heigth, canvas);
        drawIndicator(width, heigth, canvas);
    }

    private void drawBackground(int width, int height, Canvas canvas){
        sPaint.setAntiAlias(true);
        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setColor(backgroundColor);

        canvas.save();

        float radius = getRadius(width, height);
        canvas.translate(width / 2, height / 2);
        canvas.drawCircle(0, 0, radius, sPaint);

        int strokeWidth = 10;
        sPaint.setColor(foregroundColor);
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setStrokeWidth(strokeWidth);
        for (PointF p : getSteppedPoints(12, radius, new PointF(0, 0), 0, 360)) {
            canvas.drawLine(p.x, p.y, 0, 0, sPaint);
        }

        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setColor(backgroundColor);
        canvas.drawCircle(0, 0, radius / 1.2f, sPaint);

        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setColor(foregroundColor);
        canvas.drawCircle(0, 0, radius / 1.6f, sPaint);

        canvas.restore();
    }

    private void drawIndicator(int width, int height, Canvas canvas){
        canvas.save();
        canvas.translate(width / 2, height / 2);
        if(lightOn){
            sPaint.setAntiAlias(true);
            sPaint.setStyle(Paint.Style.FILL);
            sPaint.setColor(foregroundColor);

            float radius = getRadius(width, height);
            canvas.drawCircle(0, 0, radius / 2.3f, sPaint);
        }
        if(mode == AUTO){
            sPaint.setStyle(Paint.Style.STROKE);
            sPaint.setTextSize(60);
            sPaint.setTextAlign(Paint.Align.CENTER);
            Typeface bold = Typeface.create(sPaint.getTypeface(), Typeface.BOLD);
            sPaint.setTypeface(bold);
            if(lightOn){
                sPaint.setColor(backgroundColor);
            }else{
                sPaint.setColor(foregroundColor);
            }
            canvas.drawText("A", 1, 20, sPaint);
        }
        canvas.restore();
    }

    private PointF getPointInCircle(float radius, float radAngle, PointF center){
        return new PointF(
                (float) (radius * Math.cos(radAngle)) + center.x,
                (float) (radius * Math.sin(radAngle)) + center.y
        );
    }

    private List<PointF> getSteppedPoints(int steps, float radius, PointF center, float start, float end){
        List<PointF> points = new ArrayList<>();
        float step = (end - start) / steps;
        for (int i = 0; i <= steps; i++) {
            points.add(getPointInCircle(radius, (float) Math.toRadians((step) * i + start), center));
        }

        return points;
    }
}
