package com.blc.bikecomputer2.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Pablo on 10/01/2017.
 */

public class InclinationLevel extends View {

    public static final int PITCH = 0;
    public static final int ROLL = 1;

    private int backgroundColor = Color.WHITE;
    private int foregroundColor = Color.DKGRAY;
    private int levelColor = Color.RED;
    private int circleColor = Color.BLACK;

    private float angle = 0;
    private int mode = PITCH;

    public InclinationLevel(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        refresh();
    }

    public int getLevelColor() {
        return levelColor;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setLevelColor(int levelColor) {
        this.levelColor = levelColor;
        refresh();
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        refresh();
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
        refresh();
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        if(mode != ROLL && mode != PITCH){
            mode = PITCH;
        }
        refresh();
    }

    private void refresh(){
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        drawLevel(width, height, canvas);
        drawLines(width, height, canvas);
        drawCircles(width, height, canvas);
        drawIndicator(width, height, canvas);
    }

    private void drawLevel(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);

        int radius = (width / 2) - 5;

        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.drawCircle(0, 0, radius, paint);
        paint.setColor(levelColor);
        canvas.rotate(angle);
        RectF r = new RectF(-radius, -radius, radius, radius);
        canvas.drawArc(r, 0, 180, true, paint);
        canvas.restore();
    }

    private void drawLines(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(circleColor);

        float DEG2RAD = 3.14159f/180f;
        int radius = (width / 2) - 10;
        canvas.save();
        canvas.translate(width / 2, height / 2);

        for (int i = -50; i <= 50; i += 10) {
            if(i == 0){
                paint.setStrokeWidth(10);
            }else{
                paint.setStrokeWidth(5);
            }
            float degInRad = i * DEG2RAD;
            float x = (float)Math.cos(degInRad) * radius;
            float y = (float)Math.sin(degInRad) * radius;
            canvas.drawLine(-x, -y, x, y, paint);
        }
        canvas.restore();
    }

    private void drawCircles(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(foregroundColor);

        int extRadius = (width / 2) - 10;
        int innerRadius = (width / 2) - 40;

        canvas.drawCircle(width / 2, height / 2, innerRadius, paint);
        paint.setStrokeWidth(10);
        paint.setColor(circleColor);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, height / 2, extRadius, paint);
        canvas.drawCircle(width / 2, height / 2, innerRadius, paint);
    }

    private void drawIndicator(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(levelColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);
        Path p = new Path();
        p.moveTo(0, 0);
        if(mode == PITCH){
            p.lineTo(- (width / 10), 0);
            p.lineTo(0, - (width / 5));
            p.lineTo(width / 10, 0);
            p.lineTo(0, width / 5);
            p.lineTo(- (width / 10), 0);
            p.close();
        }else{
            p.lineTo(- (width / 5), 0);
            p.lineTo(0, - (width / 10));
            p.lineTo(width / 5, 0);
            p.lineTo(0, width / 10);
            p.lineTo(- (width / 5), 0);
            p.close();
        }
        canvas.drawPath(p, paint);
        canvas.rotate(angle);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawLine((-width / 2) + 50, 0, - (width / 4), 0, paint);
        canvas.drawLine((width / 2) - 50, 0, (width / 4), 0, paint);
        canvas.restore();
    }
}
