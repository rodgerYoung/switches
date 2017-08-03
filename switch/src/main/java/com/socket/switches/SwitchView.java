package com.socket.switches;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by yang on 2016/12/18.
 */

public class SwitchView extends View {
    int state;
    Context context;
    float x=this.getHeight()/2;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public SwitchView(Context context) {
        super(context);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwitchView, defStyleAttr, 0);
        int state = typedArray.getInteger(R.styleable.SwitchView_switch_state, 1);
        this.state = state;
        this.context=context;
        switch (state) {
            case 0://启用
                break;
            case 1://备用
                break;
            case 2://禁用
                break;
        }
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //默认的宽度和高度
        int width = measureDimension(180, widthMeasureSpec);
        int height = measureDimension(50, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public int measureDimension(int defaultSize, int measureSpec) {
        int result;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        /**
         * UNSPECIFIED：父结点对子结点的大小没有任何要求。
         *EXACTLY:  父结点要求其子节点的大小指定为某个确切的值。其子节点以及其他子孙结点都需要适应该大小。
         *AT MOST：父结点要求其子节点的大小不能超过某个最大值，其子节点以及其他子孙结点的大小都需要小于这个值
         */
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = defaultSize;   //UNSPECIFIED
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
Canvas canvas;
    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas=canvas;
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(x, this.getHeight()/2, this.getHeight()/2, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText("备用", x, this.getHeight()/2, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked()==MotionEvent.ACTION_DOWN){
            Toast.makeText(getContext(),event.getX()+"",Toast.LENGTH_LONG).show();
            if (event.getX()>this.getHeight()){
                for (int i=0;i<this.getHeight();i++){
                    x++;
                   this.invalidate();
                }
            }else {
                for (int i=0;i<this.getHeight();i++){
                    x--;
                    this.invalidate();
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
