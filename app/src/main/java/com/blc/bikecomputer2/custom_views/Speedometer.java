package com.blc.bikecomputer2.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
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

    private int backgroundColor = Color.BLACK;
    private int foregroundColor = Color.WHITE;
    private int speedColor = Color.rgb(255, 128, 0);
    private int lowBatteryColor = Color.RED;
    private int medBatteryColor = Color.YELLOW;
    private int highBatteryColor = Color.GREEN;
    private int batteryColor = lowBatteryColor;

    private float currSpeed = 70;
    private float maxSpeed = 100;
    private float currBattery = 100;
    private final float MAX_BATTERY = 100;
    private int selectedInfoElement = 0;
    private List<InfoElement> infoElements;
    private int idSelectedInfoElement;
    private Bitmap bmpSelectedInfoElement;
    private int idNotSelectedInfoElement;
    private Bitmap bmpNotSelectedInfoElement;

    public Speedometer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
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

    public float getCurrBattery() {
        return currBattery;
    }

    public void setCurrBattery(float currBattery) {
        this.currBattery = currBattery;
        refresh();
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
        int width = this.getWidth();
        int height = this.getHeight();

        drawSphere(width, height, canvas);
        drawGuides(width, height, canvas);
        drawSpeedLevel(width, height, canvas);
        drawBatteryLevel(width, height, canvas);
        drawCircles(width, height, canvas);
        drawValues(width, height, canvas);
    }

    private void drawSphere(int width, int height, Canvas canvas){
        Paint spherePaint = new Paint();
        spherePaint.setStyle(Paint.Style.FILL);
        spherePaint.setColor(backgroundColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height);

        canvas.drawCircle(0, 0, radius, spherePaint);

        canvas.restore();
    }

    private void drawCircles(int width, int height, Canvas canvas){
        int strokeWidth = 20;
        Paint circlePaint = new Paint();
        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeWidth(strokeWidth);
        circlePaint.setColor(foregroundColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) - strokeWidth;

        canvas.drawCircle(0, 0, radius, circlePaint);
        canvas.drawCircle(0, 0, radius / 1.4f, circlePaint);
        circlePaint.setColor(backgroundColor);
        circlePaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(0, 0, radius / 1.4f, circlePaint);

        canvas.restore();
    }

    private void drawSpeedLevel(int width, int height, Canvas canvas){
        int strokeWidth = 10;
        Paint speedPaint = new Paint();
        speedPaint.setAntiAlias(true);
        speedPaint.setStyle(Paint.Style.STROKE);
        speedPaint.setStrokeWidth(strokeWidth);
        speedPaint.setColor(speedColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) - 30;

        PointF pSpeed = getPointInCircle(radius, (float) Math.toRadians(calcSpeedAngle(120) + 120), new PointF(0, 0));
        canvas.drawLine(0, 0, pSpeed.x, pSpeed.y, speedPaint);

        speedPaint.setStrokeWidth(strokeWidth * 3);
        RectF r = new RectF(-radius + 14, -radius + 14, radius - 14, radius - 14);
        canvas.drawArc(r, 120, calcSpeedAngle(120), false, speedPaint);
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
        Paint battPaint = new Paint();
        battPaint.setAntiAlias(true);
        battPaint.setStyle(Paint.Style.STROKE);
        battPaint.setStrokeWidth(strokeWidth);
        battPaint.setColor(getBatteryColor());

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) - 30;

        PointF pBattery = getPointInCircle(radius, (float) Math.toRadians(calcBatteryAngle(120) + 120), new PointF(0, 0));
        canvas.drawLine(0, 0, -pBattery.x, pBattery.y, battPaint);

        battPaint.setStrokeWidth(strokeWidth * 3);
        RectF r = new RectF(-radius + 14, -radius + 14, radius - 14, radius - 14);
        canvas.drawArc(r, 60, -calcBatteryAngle(120), false, battPaint);
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
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(foregroundColor);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.LEFT);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height);

        boolean large = true;
        for (PointF p : getSteppedPoints(10, radius - (radius / 8), new PointF(0, 0), 120, 240)) {
            if(large){
                paint.setStrokeWidth(strokeWidth);
                large = false;
            }else{
                paint.setStrokeWidth(strokeWidth / 2);
                large = true;
            }
            canvas.drawLine(p.x, p.y, 0, 0, paint);
        }

        large = true;
        for (PointF p : getSteppedPoints(5, radius - (radius / 8), new PointF(0, 0), 60, 0)) {
            if(large){
                paint.setStrokeWidth(strokeWidth);
                large = false;
            }else{
                paint.setStrokeWidth(strokeWidth / 2);
                large = true;
            }
            canvas.drawLine(p.x, p.y, 0, 0, paint);
        }

        large = false;
        for (PointF p : getSteppedPoints(5, radius - (radius / 8), new PointF(0, 0), 360, 300)) {
            if(large){
                paint.setStrokeWidth(strokeWidth);
                large = false;
            }else{
                paint.setStrokeWidth(strokeWidth / 2);
                large = true;
            }
            canvas.drawLine(p.x, p.y, 0, 0, paint);
        }

        Paint spherePaint = new Paint();
        spherePaint.setStyle(Paint.Style.FILL);
        spherePaint.setColor(backgroundColor);
        canvas.drawCircle(0, 0, radius - (radius / 6), spherePaint);

        canvas.restore();
    }

    private void drawValues(int width, int height, Canvas canvas){
        Paint speedPaint = new Paint();
        speedPaint.setAntiAlias(true);
        speedPaint.setTextAlign(Paint.Align.CENTER);
        speedPaint.setTextSize(50);
        speedPaint.setColor(speedColor);

        Paint battPaint = new Paint();
        battPaint.setAntiAlias(true);
        battPaint.setTextAlign(Paint.Align.CENTER);
        battPaint.setTextSize(50);
        battPaint.setColor(batteryColor);

        Paint textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(60);
        textPaint.setColor(foregroundColor);

        canvas.save();
        canvas.translate(width / 2, height / 2);

        float radius = getRadius(width, height) / 1.8f;

        PointF pSpeed = getPointInCircle(radius, (float) Math.toRadians(calcSpeedAngle(120) + 120), new PointF(0, 0));
        canvas.drawText(Math.round(currSpeed) + "", pSpeed.x, pSpeed.y, speedPaint);

        PointF pBattery = getPointInCircle(radius, (float) Math.toRadians(calcBatteryAngle(120) + 120), new PointF(0, 0));
        canvas.drawText(Math.round(currBattery) + "", -pBattery.x, pBattery.y, battPaint);

        if(infoElements.isEmpty()){
            canvas.drawText("---", 0, 0, textPaint);
        }else {
            String text = infoElements.get(selectedInfoElement).getText();
            canvas.drawText(text, 0, 0, textPaint);
            if(infoElements.get(selectedInfoElement).getBitmap() != null) {
                Bitmap bmp = infoElements.get(selectedInfoElement).getBitmap();
                canvas.drawBitmap(bmp, - bmp.getWidth() / 2, -60 - bmp.getHeight(), textPaint);
            }
            float tWidth = getBmpSelectedInfoElement().getWidth() * infoElements.size() / 2;
            for (int i = 0; i < infoElements.size(); i++) {
                Bitmap bmpIc = getBmpNotSelectedInfoElement();
                if(i == selectedInfoElement){
                    bmpIc = getBmpSelectedInfoElement();
                }
                canvas.drawBitmap(bmpIc, bmpIc.getWidth() * i - tWidth, 30, textPaint);
            }
        }


        canvas.restore();
    }

    private float getRadius(int width, int height){
        if(height < width){
            return height / 2;
        }else{
            return width / 2;
        }
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
}