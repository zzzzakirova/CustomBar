package com.example.alina.custombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class CustomBar extends View {
    private int progress = getHeight();
    private int slider = getHeight();

    private Paint myPaint;
    private int sliderHeight;
    private int sliderThickness;

    private int barThickness;
    private int barHeight;

    private int barColor;
    private int sliderColor;
    private int unfilledBarColor;

    private int y;

    private onSliderPositionListener onSliderPositionListener;

    //constructor for initialising attributes
    public CustomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    //find and define attributes from attrs.xml
    private void init(AttributeSet attrs) {
        myPaint = new Paint();
        myPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CustomBar, 0, 0);
        try {
            setSliderHeight(typedArray.getDimensionPixelSize(R.styleable.CustomBar_sliderHeight, 20));
            setSliderThickness(typedArray.getDimensionPixelSize(R.styleable.CustomBar_sliderThickness, 40));
            setBarThickness(typedArray.getDimensionPixelSize(R.styleable.CustomBar_barThickness, 30));
            setBarHeight(typedArray.getDimensionPixelSize(R.styleable.CustomBar_barHeight, 900));
            setBarColor(typedArray.getColor(R.styleable.CustomBar_barColor, getResources().getColor(R.color.colorViolet)));
            setSliderColor(typedArray.getColor(R.styleable.CustomBar_sliderColor, getResources().getColor(R.color.colorDRKViolet)));
            setUnfilledBarColor(typedArray.getColor(R.styleable.CustomBar_unfilledSecionColor, Color.DKGRAY));
            }
            finally {
            typedArray.recycle();
        }
    }
    //draw the bar with a circular slider
    @Override
    protected void onDraw(Canvas canvas) {
        myPaint.setStrokeWidth(barThickness);

        myPaint.setColor(unfilledBarColor);
        canvas.drawLine(getWidth()/2,  progress, getWidth()/2,0 , myPaint);

        myPaint.setColor(barColor);
        canvas.drawLine(getWidth()/2, barHeight,getWidth()/2, progress, myPaint);

        myPaint.setColor(sliderColor);
        canvas.drawCircle(getWidth()/2, getSlider(), sliderHeight/2, myPaint);
        
    }

    // setting view to occupy no more space in width than the thickness of a slider
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        int width = sliderThickness;
        int height;
        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                height = specHeight;
                break;
            case MeasureSpec.AT_MOST:
                height = barHeight;
                break;
            case MeasureSpec.UNSPECIFIED:
                default:
                height = barHeight;
                break;
        }
        setMeasuredDimension(width, height);
    }

    //TouchEvent for slider to move it throughout the bar
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(onSliderPositionListener!= null)
                    onSliderPositionListener.onStartTrackingTouch(this);
                break;
            case MotionEvent.ACTION_MOVE:
                updateOnTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                if(onSliderPositionListener != null)
                    onSliderPositionListener.onStopTrackingTouch(this);
                setPressed(false);
                this.getParent().requestDisallowInterceptTouchEvent(false);
            case MotionEvent.ACTION_CANCEL:
                if(onSliderPositionListener!=null)
                    onSliderPositionListener.onStopTrackingTouch(this);
                setPressed(false);
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    //set slider by the coordinates where it is touched
    public void updateOnTouchEvent(MotionEvent event){
        setPressed(true);
        y=(int) event.getY();
        if(y<getHeight() && y>0)
            setSlider(y);
    }

    //interface for tracking slider position
    public interface onSliderPositionListener{
        void onStartTrackingTouch(CustomBar sliderPosition);
        void onStopTrackingTouch(CustomBar sliderPosition);
    }

    //Setters and getters
    public void setSlider(int slider) {
        this.slider = slider;
        postInvalidate();
    }

    public void setSliderHeight(int sliderHeight) {
        this.sliderHeight = sliderHeight;
        postInvalidate();
    }

    public void setSliderThickness(int sliderThickness){
        this.sliderThickness = sliderThickness;
        postInvalidate();
    }

    public void setBarThickness(int barThickness) {
        this.barThickness = barThickness;
        postInvalidate();
    }

    public void setBarHeight(int barHeight) {
        this.barHeight = barHeight;
        postInvalidate();
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
        postInvalidate();
    }

    public void setSliderColor(int sliderColor) {
        this.sliderColor = sliderColor;
        postInvalidate();
    }

    public void setUnfilledBarColor(int unfilledBarColor) {
        this.unfilledBarColor = unfilledBarColor;
        postInvalidate();
    }

    public void setProgress(int progress){
        this.progress = progress;
        postInvalidate();
    }

    public int getProgress() {
        return progress;
    }
    public int getSlider(){
        return slider;
    }
}

