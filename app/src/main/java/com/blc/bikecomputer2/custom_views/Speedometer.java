package com.blc.bikecomputer2.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 10/01/2017.
 */

public class Speedometer extends View {

    private int backgroundColor = Color.WHITE;
    private int arrowColor = Color.RED;
    private int circleColor = Color.BLACK;
    private int numbersColor = Color.BLACK;

    private float currSpeed = 0;
    private float maxSpeed = 100;

    public Speedometer(Context context, AttributeSet attrs) {
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

    public int getNumbersColor() {
        return numbersColor;
    }

    public void setNumbersColor(int numbersColor) {
        this.numbersColor = numbersColor;
        refresh();
    }

    public float getCurrSpeed() {
        return currSpeed;
    }

    public void setCurrSpeed(float currSpeed) {
        this.currSpeed = currSpeed;
        refresh();
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
        refresh();
    }

    private void refresh(){
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight() - 50;

        drawCircle(width, height, canvas);
        drawNumbers(width, height, canvas);
        drawArrow(width, height, calcAngle(),canvas);
    }

    private void drawCircle(int width, int height, Canvas canvas){
        Paint paintFill = new Paint();
        paintFill.setStyle(Paint.Style.FILL);
        paintFill.setColor(backgroundColor);

        Paint paintCircle = new Paint();
        paintCircle.setStyle(Paint.Style.STROKE);
        paintCircle.setAntiAlias(true);
        paintCircle.setStrokeWidth(10);
        paintCircle.setColor(circleColor);

        int radius = (width / 2) - 10;
        canvas.drawCircle(width / 2, height, radius, paintFill);
        canvas.drawCircle(width / 2, height, radius, paintCircle);
    }

    private void drawNumbers(int width, int height, Canvas canvas){
        List<Float> numbers = new ArrayList<>();
        float step = maxSpeed / 5;
        float n = 0;
        for (int i = 0; i <= 5; i++){
            numbers.add(n);
            n += step;
        }

        float DEG2RAD = 3.14159f/180f;
        int radius = (width / 2) - 80;

        canvas.save();
        canvas.translate(width / 2, height);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(numbersColor);
        paint.setTextSize(70);
        paint.setTextAlign(Paint.Align.CENTER);
        int k = 0;
        boolean dot = false;
        for (int i = 180; i <= 360; i += (360 / 20)) {
            float degInRad = i * DEG2RAD;
            float x = (float)Math.cos(degInRad) * radius;
            float y = (float)Math.sin(degInRad) * radius;
            if(!dot) {
                canvas.drawText(Math.round(numbers.get(k++)) + "", x, y, paint);
                dot = true;
            }else{
                canvas.drawCircle(x, y, 10, paint);
                dot = false;
            }
        }
        canvas.restore();
    }

    private void drawArrow(int width, int height, float angle,  Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(arrowColor);

        int radius = (width / 10);
        canvas.drawCircle(width / 2, height, radius, paint);

        canvas.save();
        canvas.translate(width / 2, height);
        canvas.rotate(angle);
        Path arrow = new Path();
        arrow.moveTo(0, 0);
        arrow.lineTo(0, radius / 2);
        arrow.lineTo(- (width - 20) / 2, 0);
        arrow.lineTo(0, -radius / 2);
        arrow.close();
        canvas.drawPath(arrow, paint);
        canvas.restore();
    }

    private float calcAngle(){
        float angle = (currSpeed * 180) / maxSpeed;
        if(angle > 182){
            angle = 182;
        }
        return angle;
    }
}
