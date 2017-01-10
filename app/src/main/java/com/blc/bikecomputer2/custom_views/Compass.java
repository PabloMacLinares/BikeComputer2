package com.blc.bikecomputer2.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Pablo on 10/01/2017.
 */

public class Compass extends View {

    private int backgroundColor = Color.WHITE;
    private int arrowColor = Color.RED;
    private int circleColor = Color.BLACK;
    private int lettersColor = Color.BLACK;

    private int angle = 0;

    public Compass(Context context, AttributeSet attrs) {
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

    public int getArrowColor() {
        return arrowColor;
    }

    public void setArrowColor(int arrowColor) {
        this.arrowColor = arrowColor;
        refresh();
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        refresh();
    }

    public int getLettersColor() {
        return lettersColor;
    }

    public void setLettersColor(int lettersColor) {
        this.lettersColor = lettersColor;
        refresh();
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
        refresh();
    }

    private void refresh(){
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        drawCircle(width, height, canvas);
        drawSphere(width, height, canvas);
    }

    public void drawCircle(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(backgroundColor);

        int extRadius = (width / 2) - 10;
        int innerRadius = (width / 2) - 40;

        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.drawCircle(0, 0, extRadius, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(circleColor);
        canvas.drawCircle(0, 0, extRadius, paint);

        float DEG2RAD = 3.14159f/180f;
        for (int i = 0; i <= 180; i += 30) {
            if(i == 0 || i == 90){
                paint.setStrokeWidth(10);
            }else{
                paint.setStrokeWidth(5);
            }
            float degInRad = i * DEG2RAD;
            float x = (float)Math.cos(degInRad) * extRadius;
            float y = (float)Math.sin(degInRad) * extRadius;
            canvas.drawLine(-x, -y, x, y, paint);
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        canvas.drawCircle(0, 0, innerRadius, paint);

        canvas.restore();
    }

    public void drawSphere(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(circleColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.rotate(angle);
        canvas.drawCircle(0, 0, 15, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        canvas.drawLine(-(width / 8), 0, width / 8, 0, paint);
        canvas.drawLine(0, -(width / 8), 0, width / 8, paint);

        paint.setTextSize(50);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(arrowColor);
        Path p = new Path();
        p.moveTo(0, -(width / 2) + 20);
        p.lineTo(20, -(width / 2) + 40);
        p.lineTo(-20, -(width / 2) + 40);
        p.close();
        canvas.drawPath(p, paint);

        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("N", 0, -(width / 4) + 10, paint);
        paint.setColor(circleColor);
        canvas.drawText("E", width / 4, 25, paint);
        canvas.drawText("S", 0, width / 4 + 25, paint);
        canvas.drawText("W", -(width / 4), 25, paint);
        canvas.restore();
    }
}
