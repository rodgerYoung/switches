package com.custom.switches;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 多个开关自定义view
 * Created by yang on 2016/12/18.
 */

public class SwitchView extends View {
    private int textColor = 0;//文字颜色
    private int btnColor = 0;//按钮颜色
    private int borderColor = 0;//背景
    private Context context;
    private float x = 0;
    private float height = 0;
    private String[] status = {"开", "关"};
    private int state = 0;
    private String text = status[state];
    private OnClickStateChange onClickStateChange;
    public SwitchView(Context context) {
        super(context);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, com.custom.switches.R.styleable.SwitchView, 0, 0);
        int btnColor = typedArray.getColor(R.styleable.SwitchView_btn_color, Color.WHITE);
        int borderColor = typedArray.getColor(R.styleable.SwitchView_border_color, Color.parseColor("#4bd763"));
        int textColor = typedArray.getColor(R.styleable.SwitchView_text_color, Color.parseColor("#000000"));
        this.btnColor = btnColor;
        this.borderColor = borderColor;
        this.textColor = textColor;
        typedArray.recycle();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //默认的宽度和高度
        int height = measureDimension(100, heightMeasureSpec);
        this.height = height;
        x = height / 2;
        int width = height * status.length;
        setMeasuredDimension(width, height);
    }

    /**
     * 测量
     *
     * @param defaultSize 默认大小
     * @param measureSpec 测量大小
     * @return
     */
    public int measureDimension(int defaultSize, int measureSpec) {
        int result;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(borderColor);
        gd.setCornerRadius(height);
        setBackground(gd);

        paint.setColor(borderColor);
        canvas.drawCircle(x, this.getHeight() / 2, height / 2, paint);

        paint.setColor(btnColor);
        canvas.drawCircle(x, this.getHeight() / 2, height / 2-3, paint);

        paint.setColor(textColor);
        paint.setTextSize(height / 4);
        paint.setStrokeWidth(4);
        for (int i = 0; i < status.length; i++) {
            canvas.drawText(status[i], height * (i + 1) - height / 2 - status[i].length() * (height / 8), height / 2 + (height /8), paint);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            new Thread() {
                @Override
                public void run() {
                    post(new Runnable() {
                        @Override
                        public void run() {
//                            Toast.makeText(context,getLeft()+"",Toast.LENGTH_LONG).show();
                        }
                    });
                    int onState = (int) ((event.getX()-getLeft())/ height);
                    if (state != onState) {
                        if (state > onState) {
                            for (int i = 0; i < (state - onState) * height; i++) {
                                try {
                                    if (x % 9 == 0)
                                        Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                x--;
                                postInvalidate();
                            }
                        } else {
                            for (int i = 0; i < (onState - state) * height; i++) {
                                try {
                                    if (x % 9 == 0)
                                        Thread.sleep(1);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                x++;
                                postInvalidate();
                            }
                        }
                    }
                    state = onState;
                    text = status[state];
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (onClickStateChange!=null){
                                onClickStateChange.onStateChange(state,status[state]);
                            }
                        }
                    });
                }
            }.start();
        }
        return super.onTouchEvent(event);
    }

    public String[] getStatus() {
        return status;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getBtnColor() {
        return btnColor;
    }

    public void setBtnColor(int btnColor) {
        this.btnColor = btnColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public void setOnClickStateChange(OnClickStateChange onClickStateChange) {
        this.onClickStateChange = onClickStateChange;
    }

    public interface OnClickStateChange{
        void onStateChange(int state,String statusText);
    }
}
