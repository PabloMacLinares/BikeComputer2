package com.blc.bikecomputer2.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.blc.bikecomputer2.R;
import com.blc.bikecomputer2.pojo.InfoElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pablo on 12/01/2017.
 */

public class Speedometer extends View {

    private Context context;

    private final long ANIMATION_TIME = 300;
    private MyAnimator myAnimator;
    private int backgroundColor = Color.BLACK;
    private int foregroundColor = Color.WHITE;
    private int speedColor = Color.rgb(255, 128, 0);
    private int lowBatteryColor = Color.RED;
    private int medBatteryColor = Color.YELLOW;
    private int highBatteryColor = Color.GREEN;
    private int batteryColor = lowBatteryColor;

    private float currSpeed = 0;
    private float maxSpeed = 100;
    private int currBattery = 0;
    private final int MAX_BATTERY = 100;
    private int selectedInfoElement = 0;
    private List<InfoElement> infoElements;
    private int idSelectedInfoElement;
    private Bitmap bmpSelectedInfoElement;
    private int idNotSelectedInfoElement;
    private Bitmap bmpNotSelectedInfoElement;
    private Paint sPaint;

    public Speedometer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.myAnimator = new MyAnimator();
        infoElements = new ArrayList<>();
        if(!this.isInEditMode()) {
            setIdSelectedInfoElement(R.drawable.ic_radio_button_checked_24dp);
            setIdNotSelectedInfoElement(R.drawable.ic_radio_button_unchecked_24dp);
            this.setOnTouchListener(new OnSwipeTouchListener(context) {
                @Override
                public void onSwipeLeft() {
                    swipeLeft();
                }

                @Override
                public void onSwipeRight() {
                    swipeRight();
                }
            });
        }
        sPaint = new Paint();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        refresh();
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
        refresh();
    }

    public int getSpeedColor() {
        return speedColor;
    }

    public void setSpeedColor(int speedColor) {
        this.speedColor = speedColor;
        refresh();
    }

    public int getLowBatteryColor() {
        return lowBatteryColor;
    }

    public void setLowBatteryColor(int lowBatteryColor) {
        this.lowBatteryColor = lowBatteryColor;
        refresh();
    }

    public int getMedBatteryColor() {
        return medBatteryColor;
    }

    public void setMedBatteryColor(int medBatteryColor) {
        this.medBatteryColor = medBatteryColor;
        refresh();
    }

    public int getHighBatteryColor() {
        return highBatteryColor;
    }

    public void setHighBatteryColor(int highBatteryColor) {
        this.highBatteryColor = highBatteryColor;
        refresh();
    }

    public int getBatteryColor() {
        if(currBattery <= 33) {
            batteryColor = lowBatteryColor;
        }else if(currBattery <= 66){
            batteryColor = medBatteryColor;
        }else{
            batteryColor = highBatteryColor;
        }
        return batteryColor;
    }

    public float getCurrSpeed() {
        return currSpeed;
    }

    public void setCurrSpeed(float currSpeed) {
        if(this.currSpeed != currSpeed)
            myAnimator.animateSpeedLevel(this.currSpeed, currSpeed, ANIMATION_TIME, Math.round(Math.abs(this.currSpeed - currSpeed) / 40));
    }

    public float getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
        refresh();
    }

    public int getCurrBattery() {
        return currBattery;
    }

    public void setCurrBattery(int currBattery) {
        if(this.currBattery != currBattery)
            myAnimator.animateBatteryLevel(this.currBattery, currBattery, ANIMATION_TIME, Math.round(Math.abs(this.currSpeed - currSpeed) / 40));
    }

    public int getSelectedInfoElement() {
        return selectedInfoElement;
    }

    public void setSelectedInfoElement(int selectedInfoElement) {
        this.selectedInfoElement = selectedInfoElement;
        if(this.selectedInfoElement < 0){
            this.selectedInfoElement = 0;
        }else if(this.selectedInfoElement >= this.infoElements.size()){
            this.selectedInfoElement = this.infoElements.size() - 1;
        }
        refresh();
    }

    public void swipeLeft(){
        this.selectedInfoElement--;
        if(this.selectedInfoElement < 0){
            this.selectedInfoElement = infoElements.size() - 1;
        }
        refresh();
    }

    public void swipeRight(){
        this.selectedInfoElement++;
        if(this.selectedInfoElement >= infoElements.size()){
            this.selectedInfoElement = 0;
        }
        refresh();
    }

    public List<InfoElement> getInfoElements() {
        return infoElements;
    }

    public void setInfoElements(List<InfoElement> infoElements) {
        this.infoElements = infoElements;
        refresh();
    }

    public void addInfoElement(InfoElement infoElement){
        this.infoElements.add(infoElement);
        refresh();
    }

    public void removeInfoElement(InfoElement infoElement){
        this.infoElements.remove(infoElement);
        refresh();
    }

    public void removeInfoElement(int index){
        this.infoElements.remove(index);
        refresh();
    }

    public int getIdSelectedInfoElement() {
        return idSelectedInfoElement;
    }

    public void setIdSelectedInfoElement(int idSelectedInfoElement) {
        this.idSelectedInfoElement = idSelectedInfoElement;
        bmpSelectedInfoElement = null;
        getBmpSelectedInfoElement();
        refresh();
    }

    public void setBmpSelectedInfoElement(Bitmap bmpSelectedInfoElement) {
        this.bmpSelectedInfoElement = bmpSelectedInfoElement;
        getBmpSelectedInfoElement();
        refresh();
    }

    public int getIdNotSelectedInfoElement() {
        return idNotSelectedInfoElement;
    }

    public void setIdNotSelectedInfoElement(int idNotSelectedInfoElement) {
        this.idNotSelectedInfoElement = idNotSelectedInfoElement;
        bmpNotSelectedInfoElement = null;
        getBmpSelectedInfoElement();
        refresh();
    }

    public void setBmpNotSelectedInfoElement(Bitmap bmpNotSelectedInfoElement) {
        this.bmpNotSelectedInfoElement = bmpNotSelectedInfoElement;
        getBmpNotSelectedInfoElement();
        refresh();
    }

    public Bitmap getBmpSelectedInfoElement(){
        if(bmpSelectedInfoElement == null) {
            Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, idSelectedInfoElement);
            Canvas canvas = new Canvas();
            Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bmp);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            this.bmpSelectedInfoElement = bmp;
            return bmp;
        }
        else{
            return bmpSelectedInfoElement;
        }
    }

    public Bitmap getBmpNotSelectedInfoElement(){
        if(bmpNotSelectedInfoElement == null) {
            Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, idNotSelectedInfoElement);
            Canvas canvas = new Canvas();
            Bitmap bmp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            canvas.setBitmap(bmp);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            this.bmpNotSelectedInfoElement = bmp;
            return bmp;
        }
        else{
            return bmpNotSelectedInfoElement;
        }
    }

    public void refresh(){
        invalidate();
        requestLayout();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getMeasuredWidth();
        int height = this.getMeasuredHeight();

        drawSphere(width, height, canvas);
        drawGuides(width, height, canvas);
        drawSpeedLevel(width, height, canvas);
        drawBatteryLevel(width, height, canvas);
        drawCircles(width, height, canvas);
        drawValues(width, height, canvas);
    }

    private void drawSphere(int width, int height, Canvas canvas){
        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setColor(backgroundColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height);

        canvas.drawCircle(0, 0, radius, sPaint);

        canvas.restore();
    }

    private void drawCircles(int width, int height, Canvas canvas){
        int strokeWidth = 20;
        sPaint.setAntiAlias(true);
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setStrokeWidth(strokeWidth);
        sPaint.setColor(foregroundColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) - strokeWidth;

        canvas.drawCircle(0, 0, radius, sPaint);
        canvas.drawCircle(0, 0, radius / 1.4f, sPaint);
        sPaint.setColor(backgroundColor);
        sPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, radius / 1.4f, sPaint);

        canvas.restore();
    }

    private void drawSpeedLevel(int width, int height, Canvas canvas){
        int strokeWidth = 10;
        sPaint.setAntiAlias(true);
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setStrokeWidth(strokeWidth);
        sPaint.setColor(speedColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) - 30;

        PointF pSpeed = getPointInCircle(radius, (float) Math.toRadians(calcSpeedAngle(120) + 120), new PointF(0, 0));
        canvas.drawLine(0, 0, pSpeed.x, pSpeed.y, sPaint);

        sPaint.setStrokeWidth(strokeWidth * 3);
        RectF r = new RectF(-radius + 14, -radius + 14, radius - 14, radius - 14);
        canvas.drawArc(r, 120, calcSpeedAngle(120), false, sPaint);
        canvas.restore();
    }

    private float calcSpeedAngle(float arc){
        float angle = (currSpeed * arc) / maxSpeed;
        if(angle > arc){
            angle = arc;
        }
        return angle;
    }

    private void drawBatteryLevel(int width, int height, Canvas canvas){
        int strokeWidth = 10;
        sPaint.setAntiAlias(true);
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setStrokeWidth(strokeWidth);
        sPaint.setColor(getBatteryColor());

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) - 30;

        PointF pBattery = getPointInCircle(radius, (float) Math.toRadians(calcBatteryAngle(120) + 120), new PointF(0, 0));
        canvas.drawLine(0, 0, -pBattery.x, pBattery.y, sPaint);

        sPaint.setStrokeWidth(strokeWidth * 3);
        RectF r = new RectF(-radius + 14, -radius + 14, radius - 14, radius - 14);
        canvas.drawArc(r, 60, -calcBatteryAngle(120), false, sPaint);
        canvas.restore();
    }

    private float calcBatteryAngle(float arc){
        float angle = (currBattery * arc) / MAX_BATTERY;
        if(angle > arc){
            angle = arc;
        }
        return angle;
    }

    private void drawGuides(int width, int height, Canvas canvas){
        int strokeWidth = 10;
        sPaint.setAntiAlias(true);
        sPaint.setStyle(Paint.Style.STROKE);
        sPaint.setStrokeWidth(strokeWidth);
        sPaint.setColor(foregroundColor);
        sPaint.setTextSize(60);
        sPaint.setTextAlign(Paint.Align.LEFT);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height);

        boolean large = true;
        for (PointF p : getSteppedPoints(10, radius - (radius / 8), new PointF(0, 0), 120, 240)) {
            if(large){
                sPaint.setStrokeWidth(strokeWidth);
                large = false;
            }else{
                sPaint.setStrokeWidth(strokeWidth / 2);
                large = true;
            }
            canvas.drawLine(p.x, p.y, 0, 0, sPaint);
        }

        large = true;
        for (PointF p : getSteppedPoints(5, radius - (radius / 8), new PointF(0, 0), 60, 0)) {
            if(large){
                sPaint.setStrokeWidth(strokeWidth);
                large = false;
            }else{
                sPaint.setStrokeWidth(strokeWidth / 2);
                large = true;
            }
            canvas.drawLine(p.x, p.y, 0, 0, sPaint);
        }

        large = false;
        for (PointF p : getSteppedPoints(5, radius - (radius / 8), new PointF(0, 0), 360, 300)) {
            if(large){
                sPaint.setStrokeWidth(strokeWidth);
                large = false;
            }else{
                sPaint.setStrokeWidth(strokeWidth / 2);
                large = true;
            }
            canvas.drawLine(p.x, p.y, 0, 0, sPaint);
        }

        sPaint.setStyle(Paint.Style.FILL);
        sPaint.setColor(backgroundColor);
        canvas.drawCircle(0, 0, radius - (radius / 6), sPaint);

        canvas.restore();
    }

    private void drawValues(int width, int height, Canvas canvas){
        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) / 1.7f;

        sPaint.setAntiAlias(true);
        sPaint.setTextAlign(Paint.Align.CENTER);
        sPaint.setTextSize(50);
        sPaint.setColor(speedColor);
        PointF pSpeed = getPointInCircle(radius, (float) Math.toRadians(calcSpeedAngle(120) + 120), new PointF(0, 0));
        canvas.drawText(Math.round(currSpeed) + "", pSpeed.x, pSpeed.y, sPaint);

        sPaint.setColor(batteryColor);
        PointF pBattery = getPointInCircle(radius, (float) Math.toRadians(calcBatteryAngle(120) + 120), new PointF(0, 0));
        canvas.drawText(Math.round(currBattery) + "", -pBattery.x, pBattery.y, sPaint);

        canvas.restore();
        canvas.save();
        canvas.translate(width / 2, height / 1.7f);
        sPaint.setTextSize(70);
        sPaint.setColor(foregroundColor);
        if(infoElements.isEmpty()){
            canvas.drawText("---", 0, 0, sPaint);
        }else {
            String text = infoElements.get(selectedInfoElement).getText();
            canvas.drawText(text, 0, 10, sPaint);
            if(infoElements.get(selectedInfoElement).getBitmap() != null) {
                Bitmap bmp = infoElements.get(selectedInfoElement).getBitmap();
                canvas.drawBitmap(bmp, - bmp.getWidth() / 2, -60 - bmp.getHeight(), sPaint);
            }
            float tWidth = getBmpSelectedInfoElement().getWidth() * infoElements.size() / 2;
            for (int i = 0; i < infoElements.size(); i++) {
                Bitmap bmpIc = getBmpNotSelectedInfoElement();
                if(i == selectedInfoElement){
                    bmpIc = getBmpSelectedInfoElement();
                }
                canvas.drawBitmap(bmpIc, bmpIc.getWidth() * i - tWidth, 60, sPaint);
            }
        }

        canvas.restore();
    }

    private float getRadius(int width, int height){
        return Math.min(width, height) / 2;
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

    class OnSwipeTouchListener implements OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {
        }

        public void onSwipeRight() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                return false;
            }
        }
    }

    private class MyAnimator{
        AsyncTask<Long, Float, Float> atSpeed = null;
        AsyncTask<Long, Float, Float> atBattery = null;

        public void animateSpeedLevel(float startValue, float endValue, long durationMillis, int step){
            if(atSpeed != null){
                atSpeed.cancel(true);
            }
            atSpeed = new AsyncTask<Long, Float, Float>() {
                @Override
                protected Float doInBackground(Long... longs) {
                    long currValue = longs[0];
                    long endValue = longs[1];
                    int step = Math.round(longs[3]);
                    if(step == 0)
                        step = 1;
                    if(Math.abs((endValue - currValue) / step) < step){
                        step = 1;
                    }else if(currValue == endValue){
                        return Float.valueOf(endValue);
                    }
                    long timeStep = longs[2] / Math.abs((endValue - currValue) / step);
                    if(currValue < endValue) {
                        while (currValue < endValue && !isCancelled()) {
                            try {
                                currValue += step;
                                if(currValue >= endValue)
                                    break;
                                publishProgress(Float.valueOf(currValue));
                                Thread.sleep(timeStep);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }else{
                        while (currValue > endValue && !isCancelled()) {
                            try {
                                currValue -= step;
                                if(currValue <= endValue)
                                    break;
                                publishProgress(Float.valueOf(currValue));
                                Thread.sleep(timeStep);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                    return Float.valueOf(endValue);
                }

                @Override
                protected void onProgressUpdate(Float... values) {
                    Speedometer.this.currSpeed = Math.round(values[0]);
                    refresh();
                }

                @Override
                protected void onPostExecute(Float aFloat) {
                    Speedometer.this.currSpeed = Math.round(aFloat);
                    refresh();
                }
            };
            atSpeed.execute(new Long[]{Long.valueOf(Math.round(startValue)), Long.valueOf(Math.round(endValue)), durationMillis, Long.valueOf(step)});
        }

        public void animateBatteryLevel(int startValue, int endValue, long durationMillis, int step){
            if(atBattery != null){
                atBattery.cancel(true);
            }
            atBattery = new AsyncTask<Long, Float, Float>() {
                @Override
                protected Float doInBackground(Long... longs) {
                    long currValue = longs[0];
                    long endValue = longs[1];
                    int step = Math.round(longs[3]);
                    if(step == 0)
                        step = 1;
                    if(Math.abs((endValue - currValue) / step) < step){
                        step = 1;
                    }else if(currValue == endValue){
                        return Float.valueOf(endValue);
                    }
                    long timeStep = longs[2] / Math.abs((endValue - currValue) / step);
                    if(currValue < endValue) {
                        while (currValue < endValue && !isCancelled()) {
                            try {
                                currValue += step;
                                if(currValue >= endValue)
                                    break;
                                publishProgress(Float.valueOf(currValue));
                                Thread.sleep(timeStep);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }else{
                        while (currValue > endValue && !isCancelled()) {
                            try {
                                currValue -= step;
                                if(currValue <= endValue)
                                    break;
                                publishProgress(Float.valueOf(currValue));
                                Thread.sleep(timeStep);
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                    return Float.valueOf(endValue);
                }

                @Override
                protected void onProgressUpdate(Float... values) {
                    Speedometer.this.currBattery = Math.round(values[0]);
                    refresh();
                }

                @Override
                protected void onPostExecute(Float aFloat) {
                    Speedometer.this.currBattery = Math.round(aFloat);
                    refresh();
                }
            };
            atBattery.execute(Long.valueOf(startValue), Long.valueOf(endValue), durationMillis, Long.valueOf(step));
        }
    }
}