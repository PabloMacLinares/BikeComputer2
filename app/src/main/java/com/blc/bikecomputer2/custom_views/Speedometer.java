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

    private int arrowColor = Color.RED;
    private int circleColor = Color.BLACK;
    private int numbersColor = Color.WHITE;

    private int currSpeed = 30;
    private int maxSpeed = 100;

    public Speedometer(Context context, AttributeSet attrs) {
        super(context, attrs);
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

    public int getCurrSpeed() {
        return currSpeed;
    }

    public void setCurrSpeed(int currSpeed) {
        this.currSpeed = currSpeed;
        refresh();
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
        refresh();
    }

    private void refresh(){
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        drawCircle(width, height, canvas);
        drawNumbers(width, height, canvas);
        drawArrow(width, height, calcAngle(),canvas);
    }

    private void drawCircle(int width, int height, Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
        paint.setColor(circleColor);

        int radius = (width / 2) - 5;
        canvas.drawCircle(width / 2, height, radius, paint);
    }

    private void drawNumbers(int width, int height, Canvas canvas){
        List<Float> numbers = new ArrayList<>();
        float step = maxSpeed / 5;
        float n = 0;
        for (int i = 0; i <= 5; i++){
            numbers.add(n);
            n += step;
        }

        for (float nu : numbers) {
            System.out.println(nu);
        }
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
        return (currSpeed * 180) / maxSpeed;
    }
}
