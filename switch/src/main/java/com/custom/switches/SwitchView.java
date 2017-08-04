package com.custom.switches;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 多个开关自定义view
 * Created by yang on 2016/12/18.
 */

public class SwitchView extends View {
    private int textColor = 0;
    private int btnColor = 0;
    private int borderColor = 0;
    private Context context;
    private float x = 0;
    private float height = 0;
    private String[] status = {"你好", "你也好", "呵呵"};
    private int state = 0;
    private String text = status[state];

    public SwitchView(Context context) {
        super(context);
    }

    public SwitchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, com.custom.switches.R.styleable.SwitchView, 0, 0);
        int btnColor = typedArray.getColor(R.styleable.SwitchView_btn_color, Color.parseColor("#999999"));
        int borderColor = typedArray.getColor(R.styleable.SwitchView_border_color, Color.parseColor("#cccccc"));
        int textColor = typedArray.getColor(R.styleable.SwitchView_text_color, Color.parseColor("#cccccc"));

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
        int height = measureDimension(50, heightMeasureSpec);
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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        setBackgroundColor(Color.WHITE);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, this.getHeight() / 2, height / 2, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(height / 8);
        for (int i = 0; i < status.length; i++) {
            canvas.drawText(status[i], height * (i + 1) - height / 2 - text.length() * (height / 16), height / 2 + (height / 16), paint);
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            new Thread() {
                @Override
                public void run() {
                    int onState = (int) (event.getX() / height);
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
                            Toast.makeText(getContext(), state + "", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }.start();
        }
        return super.onTouchEvent(event);
    }

}
